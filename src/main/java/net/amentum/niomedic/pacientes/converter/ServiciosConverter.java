package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Servicios;
import net.amentum.niomedic.pacientes.views.CatServicioView;
import net.amentum.niomedic.pacientes.views.ServiciosView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ServiciosConverter {

    public Servicios toEntity(ServiciosView view) {
        Servicios entity = new Servicios();
        entity.setIdPaciente(view.getIdPaciente());
        entity.setIdServicio(view.getIdServicio());
        return entity;
    }

    public ServiciosView toView(Servicios entity) {
        ServiciosView view = new ServiciosView();
        view.setIdPaciente(entity.getIdPaciente());
        view.setIdServicio(entity.getIdServicio());
        return view;
    }
}
