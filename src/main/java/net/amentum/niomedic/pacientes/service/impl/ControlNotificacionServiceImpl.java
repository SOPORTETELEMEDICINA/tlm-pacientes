package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.callback.OneSignalCallback;
import net.amentum.niomedic.pacientes.configuration.ApiServCaller;
import net.amentum.niomedic.pacientes.configuration.OneSignalApi;
import net.amentum.niomedic.pacientes.converter.ControlNotificacionesConverter;
import net.amentum.niomedic.pacientes.exception.ControlNotificacionException;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.model.ControlNotificacion;
import net.amentum.niomedic.pacientes.model.ControlNotificacionLogs;
import net.amentum.niomedic.pacientes.persistence.ControlNotificacionLogsRepository;
import net.amentum.niomedic.pacientes.persistence.ControlNotificacionRepository;
import net.amentum.niomedic.pacientes.service.ControlNotificacionService;
import net.amentum.niomedic.pacientes.views.ControlNotificacionView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional(readOnly = true)
@Service
public class ControlNotificacionServiceImpl implements ControlNotificacionService {

    private final Logger logger = LoggerFactory.getLogger(ControlNotificacionServiceImpl.class);

    final String DEVICE_VALUE = "idDevice";
    final String RESPONSE_ID = "id";
    final String RESPONSE_ID_USER = "idUserApp";
    final String RESPONSE_ID_PACIENTE = "idPaciente";
    final String RECIPIENTS = "recipients";
    final Long TYPE_GLUCOSA = 1L;
    final Long TYPE_PRESION_ARTERIAL = 2L;
    final Long TYPE_NUTRICION = 3L;
    final Long TYPE_COVID = 4L;

    @Autowired
    ControlNotificacionRepository repository;

    @Autowired
    ControlNotificacionLogsRepository logsRepository;

    @Autowired
    ControlNotificacionesConverter converter;

    @Autowired
    ApiServCaller servCaller;

    @Autowired
    @Lazy
    OneSignalApi oneSignalApi;

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void createControlNotificacionService(ControlNotificacionView view) throws ControlNotificacionException {
        try {
            repository.saveAndFlush(converter.toEntity(view));
        } catch (Exception ex) {
            ControlNotificacionException exception = new ControlNotificacionException("Error al insertar en control de notificaciones", ControlNotificacionException.LAYER_DAO, ControlNotificacionException.ACTION_INSERT);
            exception.addError(ex.getLocalizedMessage());
            logger.error("Error: ", ex.getCause());
            throw exception;
        }
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public List<ControlNotificacionView> getByUserAndDeviceAndTipoNotificacion(Long idUserApp, String idDevice, Long tipoNotificacion) throws ControlNotificacionException {
        try {
            List<ControlNotificacion> list = repository.findAllByIdUserAndIdDeviceAndTipoNotificacion(idUserApp, idDevice, tipoNotificacion);
            if (list == null || list.isEmpty()) {
                ControlNotificacionException exception = new ControlNotificacionException("Error al buscar en control de notificaciones", ControlNotificacionException.LAYER_DAO, ControlNotificacionException.ACTION_SELECT);
                exception.addError("No hay datos para el usuario: " + idUserApp + ", device: " + idDevice + ", tipo: " + tipoNotificacion);
                logger.error("No hay datos para el usuario: " + idUserApp + ", device: " + idDevice + ", tipo: " + tipoNotificacion);
                throw exception;
            }
            List<ControlNotificacionView> viewList = new ArrayList<>();
            list.forEach(entity -> {
                ControlNotificacionView view = new ControlNotificacionView();
                view.setId(entity.getIdControl());
                view.setIdUser(entity.getIdUser());
                view.setIdDevice(entity.getIdDevice());
                view.setIdNotification(entity.getIdNotification());
                view.setTipoNotificacion(entity.getTipoNotificacion());
                viewList.add(view);
            });
            return viewList;
        } catch (ControlNotificacionException ex) {
            throw ex;
        } catch (Exception ex) {
            ControlNotificacionException exception = new ControlNotificacionException("Error al insertar en control de notificaciones", ControlNotificacionException.LAYER_DAO, ControlNotificacionException.ACTION_INSERT);
            exception.addError(ex.getLocalizedMessage());
            logger.error("Error: ", ex.getCause());
            throw exception;
        }
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void deleteByUserApp(Long idUserApp, String idDevice, Long tipoNotificacion) throws ControlNotificacionException {
        try {
            List<ControlNotificacion> list = repository.findAllByIdUserAndIdDeviceAndTipoNotificacion(idUserApp, idDevice, tipoNotificacion);
            if (list == null || list.isEmpty()) {
                ControlNotificacionException exception = new ControlNotificacionException("Error al buscar en control de notificaciones", ControlNotificacionException.LAYER_DAO, ControlNotificacionException.ACTION_SELECT);
                exception.addError("No hay datos para el usuario: " + idUserApp + ", device: " + idDevice);
                logger.error("No hay datos para el usuario: " + idUserApp + ", device: " + idDevice);
                throw exception;
            }
            repository.delete(list);
            logger.info("Notificaciones borradas: " + list.size());
        } catch (ControlNotificacionException ex) {
            throw ex;
        } catch (Exception ex) {
            ControlNotificacionException exception = new ControlNotificacionException("Error al buscar en control de notificaciones", ControlNotificacionException.LAYER_DAO, ControlNotificacionException.ACTION_DELETE);
            exception.addError(ex.getLocalizedMessage());
            logger.error("Error: ", ex);
            throw exception;
        }
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void deleteAll() throws ControlNotificacionException {
        try {
            repository.deleteAll();
        } catch (Exception ex) {
            ControlNotificacionException exception = new ControlNotificacionException("Error al buscar en control de notificaciones", ControlNotificacionException.LAYER_DAO, ControlNotificacionException.ACTION_INSERT);
            exception.addError(ex.getLocalizedMessage());
            logger.error("Error: ", ex);
            throw exception;
        }
    }

    @Transactional()
    @Override
    public void createErrorControlNotificacionService(ControlNotificacionLogs error) {
        try {
            logsRepository.saveAndFlush(error);
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    @Transactional()
    @Override
    public void deleteAllErrorControlNotificacionService() {
        try {
            logsRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

//    @Scheduled(cron = "0 0/5 * * * *")
//    @Scheduled(cron = "0 10 0 * * *")
    /*@Scheduled(cron = "0/30 * * * * *")*/
    @Scheduled(cron = "0 55 12 * * *")
    @Transactional()
    public void syncNotifications() {
        try {
            logger.info("CRON iniciando... " + new Date());
            logger.info("Borrando notificaciones");
            deleteAll();
            deleteAllErrorControlNotificacionService();
            JSONObject notificationList = servCaller.getListaNotificacionesPorAgendar();
            JSONArray jsonArray = notificationList.getJSONArray("presionArt");
            logger.info("Agendando notificaciones de presión arterial: " + jsonArray.length() + " pendientes");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = jsonArray.getJSONObject(i);
                scheduleOneSignal(element, TYPE_PRESION_ARTERIAL);
            }
            jsonArray = notificationList.getJSONArray("covid");
            logger.info("Agendando notificaciones de covid: " + jsonArray.length() + " pendientes");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = jsonArray.getJSONObject(i);
                scheduleOneSignal(element, TYPE_COVID);
            }
            jsonArray = notificationList.getJSONArray("glucosa");
            logger.info("Agendando notificaciones de glucosa: " + jsonArray.length() + " pendientes");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = jsonArray.getJSONObject(i);
                scheduleOneSignal(element, TYPE_GLUCOSA);
            }
            jsonArray = notificationList.getJSONArray("nutricion");
            logger.info("Agendando notificaciones de nutrición: " + jsonArray.length() + " pendientes");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = jsonArray.getJSONObject(i);
                scheduleOneSignal(element, TYPE_NUTRICION);
            }
            logger.info("CRON terminado...");
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    @Transactional
    public void scheduleOneSignal(JSONObject element, Long notificationType) {
        try {
            oneSignalApi.scheduleOneSignal(element, notificationType, new OneSignalCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        if((int)response.get(RECIPIENTS) > 0) {
                            ControlNotificacionView view = new ControlNotificacionView();
                            view.setTipoNotificacion(notificationType);
                            view.setIdNotification(response.getString(RESPONSE_ID));
                            view.setIdDevice(response.getString(DEVICE_VALUE));
                            view.setIdUser(response.getLong(RESPONSE_ID_USER));
                            createControlNotificacionService(view);
                            logger.info(Thread.currentThread().getName() + " - Registro de notificación guardado - {}", response);
                        } else {
                            logger.info(Thread.currentThread().getName() + " onSuccess() Errors - {}", response);
                            ControlNotificacionLogs error = new ControlNotificacionLogs();
                            error.setIdUser(response.getLong(RESPONSE_ID_USER));
                            error.setIdPaciente(response.getString(RESPONSE_ID_PACIENTE));
                            error.setTipoNotif(notificationType);
                            error.setDescripcion(response.toString());
                            logsRepository.saveAndFlush(error);
                        }
                    } catch (JSONException | ControlNotificacionException e) {
                        logger.error(Thread.currentThread().getName() + " onSuccess() - {}", e.getLocalizedMessage());
                    }
                }

                @Override
                public void onError(String error, JSONObject element) {
                    try {
                        ControlNotificacionLogs errorEnt = new ControlNotificacionLogs();
                        errorEnt.setIdUser((element.isNull(RESPONSE_ID_USER)?0: element.getLong(RESPONSE_ID_USER)));
                        errorEnt.setIdPaciente((element.isNull(RESPONSE_ID_PACIENTE)?"-": element.getString("idPaciente")));
                        errorEnt.setTipoNotif(notificationType);
                        errorEnt.setDescripcion(error);
                        logsRepository.saveAndFlush(errorEnt);
                        logger.error(Thread.currentThread().getName() + " onError() - Element: {} - error al agendar notificación en OneSignal: {}",element,error);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            PacienteException consE = new PacienteException("Ocurrió un error inesperado en CRON", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
            consE.addError("No fue posible obtener la ´lista de notificaciones de pacientes");
            logger.error("Ocurrió un error al agendar la notificación- error: {}", e.toString());
        }
    }
}
