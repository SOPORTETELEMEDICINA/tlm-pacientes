package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.ServiciosException;
import net.amentum.niomedic.pacientes.views.CatServicio;
import net.amentum.niomedic.pacientes.views.ServiciosView;


public interface ServiciosP {

    void createServicio(ServiciosView serviciosView) throws ServiciosException;

    void deleteServiceById(String idPaciente) throws ServiciosException;

    CatServicio findByIdPaciente(String idPaciente) throws Exception;

    void addService(String idPaciente) throws ServiciosException;
}
