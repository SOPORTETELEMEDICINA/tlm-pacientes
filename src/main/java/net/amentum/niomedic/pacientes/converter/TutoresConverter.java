package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Tutores;
import net.amentum.niomedic.pacientes.views.TutoresView;
import org.springframework.stereotype.Component;

@Component
public class TutoresConverter {

    public Tutores toEntity(TutoresView view) {
        Tutores entity = new Tutores();
        entity.setIdPaciente(view.getIdPaciente());
        entity.setIdTutor(view.getIdTutor()); // Integer ahora
        return entity;
    }

    public TutoresView toView(Tutores entity) {
        TutoresView view = new TutoresView();
        view.setIdTutor(entity.getIdTutor()); // Integer ahora
        view.setIdPaciente(entity.getIdPaciente());
        return view;
    }

}
