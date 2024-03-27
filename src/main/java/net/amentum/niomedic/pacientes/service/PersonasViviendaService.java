package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.PersonasViviendaException;
import net.amentum.niomedic.pacientes.views.PersonasViviendaView;

import java.util.ArrayList;

public interface PersonasViviendaService {
    void createPersonasVivienda(ArrayList<PersonasViviendaView> personasViviendaViewArrayList, String idPaciente) throws PersonasViviendaException;

    void updatePersonasVivienda(ArrayList<PersonasViviendaView> personasViviendaViewArrayList, String idPaciente) throws PersonasViviendaException;

    void deletePersonasVivienda(ArrayList<PersonasViviendaView> personasViviendaViewArrayList, String idPaciente) throws PersonasViviendaException;
}
