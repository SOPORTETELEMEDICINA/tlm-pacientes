package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.ServicioAdicionalesException;
import net.amentum.niomedic.pacientes.views.ServicioAdicionalesView;

import java.util.ArrayList;

public interface ServicioAdicionalesService {
   void createServicioAdicionales(ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList, String idPaciente) throws ServicioAdicionalesException;

    void updateServicioAdicionales(ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList, String idPaciente) throws ServicioAdicionalesException;

    void deleteServicioAdicionales(ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList, String idPaciente) throws ServicioAdicionalesException;
}
