package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.ControlNotificacionException;
import net.amentum.niomedic.pacientes.service.ControlNotificacionService;
import net.amentum.niomedic.pacientes.views.ControlNotificacionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("notificaciones-paciente")
public class ControlNotificacionRest extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ControlNotificacionRest.class);

    @Autowired
    ControlNotificacionService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createControlNotificacion(@RequestBody @Valid ControlNotificacionView view) throws ControlNotificacionException {
        try {
            logger.info("====>Guardar nuevo control de notificacion - {}", view);
            if(view.getIdNotification() == null || view.getIdNotification().isEmpty())
                throw new ControlNotificacionException("Campo id notificación vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(view.getIdDevice() == null || view.getIdDevice().isEmpty())
                throw new ControlNotificacionException("Campo id device vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(view.getIdUser() == null || view.getIdUser() <= 0)
                throw new ControlNotificacionException("Campo id user vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(view.getTipoNotificacion() == null || view.getTipoNotificacion() <= 0)
                throw new ControlNotificacionException("Campo tipo notificación vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            service.createControlNotificacionService(view);
        } catch (ControlNotificacionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ControlNotificacionException("Error al insertar en control notificaciones - " + ex.getMessage(), ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_INSERT);
        }
    }

    @RequestMapping(value = "one", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ControlNotificacionView> getByUserAndDeviceAndTipoNotificacion(@RequestParam() Long idUserApp, @RequestParam() String idDevice, @RequestParam() Long tipoNotificacion) throws ControlNotificacionException {
        try {
            if(idUserApp <= 0)
                throw new ControlNotificacionException("Campo user vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(idDevice.isEmpty())
                throw new ControlNotificacionException("Campo device vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(tipoNotificacion <= 0)
                throw new ControlNotificacionException("Campo tipo notificación vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            return service.getByUserAndDeviceAndTipoNotificacion(idUserApp, idDevice, tipoNotificacion);
        } catch (ControlNotificacionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ControlNotificacionException("Error al borrar en control notificaciones - " + ex.getMessage(), ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_INSERT);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteByUserAndDevice(@RequestParam() Long idUserApp, @RequestParam() String idDevice, @RequestParam() Long tipoNotificacion) throws ControlNotificacionException {
        try {
            if(idUserApp <= 0)
                throw new ControlNotificacionException("Campo user vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(idDevice.isEmpty())
                throw new ControlNotificacionException("Campo device vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            if(tipoNotificacion <= 0)
                throw new ControlNotificacionException("Campo tipo notificación vacío", ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_VALIDATE);
            service.deleteByUserApp(idUserApp, idDevice, tipoNotificacion);
        } catch (ControlNotificacionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ControlNotificacionException("Error al borrar en control notificaciones - " + ex.getMessage(), ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_INSERT);
        }
    }

    @RequestMapping(value = "deleteAll", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() throws ControlNotificacionException {
        try {
            service.deleteAll();
        } catch (ControlNotificacionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ControlNotificacionException("Error al borrar en control notificaciones - " + ex.getMessage(), ControlNotificacionException.LAYER_REST, ControlNotificacionException.ACTION_INSERT);
        }
    }
}
