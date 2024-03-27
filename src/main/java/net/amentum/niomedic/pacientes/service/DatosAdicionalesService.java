package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.DatosAdicionalesException;
import net.amentum.niomedic.pacientes.views.DatosAdicionalesView;

public interface DatosAdicionalesService {
    void createDatosAdicionales(DatosAdicionalesView datosAdicionalesView, String idPaciente) throws DatosAdicionalesException;
//
    void updateDatosAdicionales(DatosAdicionalesView datosAdicionalesView, String idPaciente) throws DatosAdicionalesException;
}
