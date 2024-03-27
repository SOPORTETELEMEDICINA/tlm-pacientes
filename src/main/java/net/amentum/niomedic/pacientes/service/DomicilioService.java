package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.DomicilioException;
import net.amentum.niomedic.pacientes.views.DomicilioView;

import java.util.ArrayList;

public interface DomicilioService {
    void createDomicilio(ArrayList<DomicilioView> domicilioViewArrayList, String idPaciente) throws DomicilioException;

    void updateDomicilio(ArrayList<DomicilioView> domicilioViewArrayList, String idPaciente) throws DomicilioException;

    void deleteDomicilio(ArrayList<DomicilioView> domicilioViewArrayList, String idPaciente) throws DomicilioException;
}
