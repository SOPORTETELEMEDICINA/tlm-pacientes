package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.ControlPago;
import net.amentum.niomedic.pacientes.views.ControlPagosView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ControlPagosConverter {

    public ControlPago toEntity(ControlPagosView view) {
        ControlPago entity = new ControlPago();
        BeanUtils.copyProperties(view, entity);
        return entity;
    }

    public ControlPagosView toView(ControlPago entity) {
        ControlPagosView view = new ControlPagosView();
        BeanUtils.copyProperties(entity, view);
        return view;
    }

}
