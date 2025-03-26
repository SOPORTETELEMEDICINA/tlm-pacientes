package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.common.GenericException;
import net.amentum.niomedic.pacientes.converter.RelacionTitularConverter;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.model.RelacionTitular;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.persistence.RelacionTitularRepository;
import net.amentum.niomedic.pacientes.service.RelacionTitularService;
import net.amentum.niomedic.pacientes.views.RelacionTitularView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelacionTitularServiceImpl implements RelacionTitularService {

    private final Logger logger = LoggerFactory.getLogger(RelacionTitularServiceImpl.class);

    private RelacionTitularRepository repository;

    private PacienteRepository repositoryPaciente;

    private RelacionTitularConverter converter;

    @Autowired
    public void setRepository(RelacionTitularRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setRepository(PacienteRepository repositoryPaciente) {
        this.repositoryPaciente = repositoryPaciente;
    }

    @Autowired
    public void setConverter(RelacionTitularConverter converter) { this.converter = converter; }

    @Transactional(rollbackFor = {TutoresException.class})
    @Override
    public void createRelacionTitular(RelacionTitularView relacionTitularView) throws TutoresException {
        try {
            if (repository.existsByIdPacienteTitularAndIdPacienteBeneficiario(
                    relacionTitularView.getIdPacienteTitular(),
                    relacionTitularView.getIdPacienteBeneficiario())) {
                throw new TutoresException("La relación entre el titular y el beneficiario ya existe",
                        PacienteException.LAYER_SERVICE,
                        PacienteException.ACTION_INSERT);
            }
            logger.info("Agregando nueva relacion titular - {}", relacionTitularView);

            repository.save(converter.toEntity(relacionTitularView));
        } catch (Exception ex) {
            logger.error("Error al insertar relacion titular nueva - {}", ex.getMessage());
            throw new TutoresException(ex.getMessage(), PacienteException.LAYER_SERVICE, PacienteException.ACTION_INSERT);
        }
    }

    @Override
    public List<PacienteBeneficiarioDTO> getBeneficiariosTitular(String idPacienteTitular) throws Exception {
        try {
            List<PacienteBeneficiarioDTO> pacientesDTO = new ArrayList<>();
            List<RelacionTitular> pacientes = repository.findAllByIdPacienteTitular(idPacienteTitular);

            if (pacientes == null) {
                return null;
            }

            for (int i = 0; i < pacientes.size(); i++) {
                Paciente paciente = repositoryPaciente.getOne(pacientes.get(i).getIdPacienteBeneficiario());
                PacienteBeneficiarioDTO pacienteDTO = converter.convertToPacienteBeneficiarioDTO(pacientes.get(i), paciente);

                pacientesDTO.add(pacienteDTO);
            }

            return pacientesDTO;
        }
        catch(Exception ex) {
            logger.error("Error: {}", ex.getLocalizedMessage());

            PacienteException exception = new PacienteException("Ocurrió un error al consultar los beneficiarios del paciente", PacienteException.LAYER_DAO, GenericException.ACTION_UPDATE);
            exception.addError(ex.getLocalizedMessage());

            throw exception;
        }
    }
}
