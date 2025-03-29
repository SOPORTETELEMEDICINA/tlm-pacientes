package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.RelacionTutores;
import net.amentum.niomedic.pacientes.views.RelacionTutoresView;
import org.springframework.stereotype.Component;

@Component
public class RelacionTutoresConverter {

    public RelacionTutores toEntity(RelacionTutoresView view) {
        RelacionTutores entity = new RelacionTutores();
        entity.setIdRelacion(view.getIdRelacion());
        entity.setIdTutor(view.getIdTutor());  // Integer ahora
        entity.setIdPacTutor(view.getIdPacTutor());
        entity.setParentesco(view.getParentesco());
        return entity;
    }

    public RelacionTutoresView toView(RelacionTutores entity) {
        RelacionTutoresView view = new RelacionTutoresView();
        view.setIdRelacion(entity.getIdRelacion());
        view.setIdTutor(entity.getIdTutor());  // Integer ahora
        view.setIdPacTutor(entity.getIdPacTutor());
        view.setParentesco(entity.getParentesco());
        return view;
    }

}
