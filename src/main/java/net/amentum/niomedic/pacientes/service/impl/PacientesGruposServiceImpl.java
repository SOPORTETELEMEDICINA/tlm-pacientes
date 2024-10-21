package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.PacientesGruposConverter;
import net.amentum.niomedic.pacientes.exception.PacientesGruposException;
import net.amentum.niomedic.pacientes.model.PacientesGrupos;
import net.amentum.niomedic.pacientes.persistence.PacientesGruposRepository;
import net.amentum.niomedic.pacientes.service.PacientesGruposService;
import net.amentum.niomedic.pacientes.views.PacientesGruposView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PacientesGruposServiceImpl implements PacientesGruposService {
    final Logger logger = LoggerFactory.getLogger(PacientesGruposServiceImpl.class);

    @Autowired
    PacientesGruposRepository repository;

    @Autowired
    PacientesGruposConverter converter;

    @Override
    public PacientesGruposView findFirstByIdPaciente(String idPaciente) throws PacientesGruposException {
        try {
            PacientesGrupos pacienteGrupo = repository.findFirstByIdPaciente(idPaciente);
            if(pacienteGrupo == null) {
                PacientesGruposException exception = new PacientesGruposException("Error inesperado al buscar un cuestionario", PacientesGruposException.LAYER_DAO, PacientesGruposException.ACTION_SELECT );
                exception.addError("Error al buscar un cuestionario, no se encuentra registrado");
                logger.error("Error al buscar un grupo ID del paciente: - {} - CODE {}", idPaciente, exception.getExceptionCode());
                throw exception;
            }
            return converter.toView(pacienteGrupo);
        } catch(Exception ex) {
            PacientesGruposException exception = new PacientesGruposException("Error inesperado al buscar un cuestionario", PacientesGruposException.LAYER_DAO, PacientesGruposException.ACTION_SELECT );
            exception.addError("Error al buscar un cuestionario - " + ex);
            logger.error("Error al buscar ungrupo ID del paciente- {} - CODE {} - {}", idPaciente, exception.getExceptionCode(), ex.getMessage());
            throw exception;
        }
    }

}
