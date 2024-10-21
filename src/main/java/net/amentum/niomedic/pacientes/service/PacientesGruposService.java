package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.PacientesGruposException;
import net.amentum.niomedic.pacientes.views.PacientesGruposView;

public interface PacientesGruposService {
    PacientesGruposView findFirstByIdPaciente(String idPaciente) throws PacientesGruposException;
}
