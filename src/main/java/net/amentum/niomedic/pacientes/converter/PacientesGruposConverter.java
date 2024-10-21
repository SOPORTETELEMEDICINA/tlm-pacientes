package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.PacientesGrupos;
import net.amentum.niomedic.pacientes.views.PacientesGruposView;
import org.springframework.stereotype.Component;

@Component
public class PacientesGruposConverter {

    public PacientesGrupos toEntity(PacientesGruposView view) {
        PacientesGrupos entity = new PacientesGrupos();
        entity.setIdPaciente(view.getIdPaciente());
        entity.setIdGroup(view.getIdGroup());
        entity.setIdPacientesGrupos(view.getIdPacientesGrupos());
        return entity;
    }

    public PacientesGruposView toView(PacientesGrupos entity) {
        PacientesGruposView view = new PacientesGruposView();
        view.setIdGroup(entity.getIdGroup());
        view.setIdPaciente(entity.getIdPaciente());
        view.setIdPacientesGrupos(entity.getIdPacientesGrupos());
        return view;
    }
}
