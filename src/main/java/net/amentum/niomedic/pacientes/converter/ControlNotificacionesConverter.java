package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.ControlNotificacion;
import net.amentum.niomedic.pacientes.views.ControlNotificacionView;
import org.springframework.stereotype.Component;

@Component
public class ControlNotificacionesConverter {

    public ControlNotificacion toEntity(ControlNotificacionView view) {
        ControlNotificacion entity = new ControlNotificacion();
        entity.setIdControl(view.getId());
        entity.setIdUser(view.getIdUser());
        entity.setIdDevice(view.getIdDevice());
        entity.setIdNotification(view.getIdNotification());
        entity.setTipoNotificacion(view.getTipoNotificacion());
        return entity;
    }

    public ControlNotificacionView toEntity(ControlNotificacion entity) {
        ControlNotificacionView view = new ControlNotificacionView();
        view.setId(entity.getIdControl());
        view.setIdUser(entity.getIdUser());
        view.setIdDevice(entity.getIdDevice());
        view.setIdNotification(entity.getIdNotification());
        view.setTipoNotificacion(entity.getTipoNotificacion());
        return view;
    }

}
