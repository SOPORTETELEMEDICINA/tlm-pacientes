package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.RelacionTutores;
import net.amentum.niomedic.pacientes.views.RelacionTutoresView;

import java.util.List;

public interface RelacionTutoresService {
    void createRelacionTutores(RelacionTutoresView tutoresView) throws TutoresException;
    List<RelacionTutoresView> findAll() throws TutoresException;
    RelacionTutoresView findByIdPaciente(String idPaciente) throws TutoresException;

    RelacionTutoresView findByIdTutor(String idTutor) throws TutoresException;
}
