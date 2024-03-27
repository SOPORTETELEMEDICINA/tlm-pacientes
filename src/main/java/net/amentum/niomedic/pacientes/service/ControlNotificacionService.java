package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.ControlNotificacionException;
import net.amentum.niomedic.pacientes.model.ControlNotificacionLogs;
import net.amentum.niomedic.pacientes.views.ControlNotificacionView;

import java.util.List;

public interface ControlNotificacionService {

    void createControlNotificacionService(ControlNotificacionView view) throws ControlNotificacionException;

    List<ControlNotificacionView> getByUserAndDeviceAndTipoNotificacion(Long idUserApp, String idDevice, Long tipoNotificacion) throws ControlNotificacionException;

    void deleteByUserApp(Long idUserApp, String idDevice, Long tipoNotificacion) throws ControlNotificacionException;

    void deleteAll() throws ControlNotificacionException;

    void createErrorControlNotificacionService(ControlNotificacionLogs error) throws ControlNotificacionException;

    void deleteAllErrorControlNotificacionService();
}
