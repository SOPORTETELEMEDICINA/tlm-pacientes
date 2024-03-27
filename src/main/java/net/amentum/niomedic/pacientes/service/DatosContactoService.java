package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.DatosContactoException;
import net.amentum.niomedic.pacientes.views.DatosContactoView;

import java.util.ArrayList;

public interface DatosContactoService {
    void createDatosContacto(ArrayList<DatosContactoView> datosContactoViewArrayList, String idPaciente) throws DatosContactoException;

    void updateDatosContacto(ArrayList<DatosContactoView> datosContactoViewArrayList, String idPaciente) throws DatosContactoException;

    void deleteDatosContacto(ArrayList<DatosContactoView> datosContactoViewArrayList, String idPaciente) throws DatosContactoException;
}
