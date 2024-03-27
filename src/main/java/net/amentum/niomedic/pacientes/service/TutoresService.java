package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.views.TutoresView;

import java.util.List;

public interface TutoresService {

    void createTutores(TutoresView tutoresView) throws TutoresException;
    List<TutoresView> findAll() throws TutoresException;

}
