package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.RelacionTutoresConverter;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.RelacionTutores;
import net.amentum.niomedic.pacientes.model.Tutores;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.persistence.RelacionTutoresRespository;
import net.amentum.niomedic.pacientes.persistence.TutoresRepository;
import net.amentum.niomedic.pacientes.service.RelacionTutoresService;
import net.amentum.niomedic.pacientes.views.RelacionTutoresView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelacionTutoresServiceImpl implements RelacionTutoresService {

    private final Logger logger = LoggerFactory.getLogger(RelacionTutoresServiceImpl.class);

    private RelacionTutoresRespository repository;
    private PacienteRepository pacienteRepository;
    private TutoresRepository tutoresRepository;
    private RelacionTutoresConverter converter;

    @Autowired
    public void setRepository(RelacionTutoresRespository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setConverter(RelacionTutoresConverter converter) {
        this.converter = converter;
    }

    @Autowired
    public void setPacienteRepository(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Autowired
    public void setTutoresRepository(TutoresRepository tutoresRepository) {
        this.tutoresRepository = tutoresRepository;
    }

    @Transactional(rollbackFor = {TutoresException.class})
    @Override
    public void createRelacionTutores(RelacionTutoresView tutoresView) throws TutoresException {
        try {
            logger.info("Agregando nueva relacion - {}", tutoresView);
            if(repository.existTutor(tutoresView.getIdPacTutor())) {
                throw new TutoresException("Tutor ya registrado", PacienteException.LAYER_SERVICE, PacienteException.ACTION_INSERT);
            }
            repository.save(converter.toEntity(tutoresView));
        } catch (Exception ex) {
            logger.error("Error al insertar relacion nueva - {}", ex.getMessage());
            throw new TutoresException(ex.getMessage(), PacienteException.LAYER_SERVICE, PacienteException.ACTION_INSERT);
        }
    }

    @Override
    public List<RelacionTutoresView> findAll() throws TutoresException {
        List<RelacionTutoresView> viewList = new ArrayList<>();
        try {
            List<RelacionTutores> entityList = repository.findAll();
            for(RelacionTutores entity : entityList) {
                Tutores tutores = tutoresRepository.findByIdTutor(entity.getIdTutor());
                Paciente paciente = pacienteRepository.getOne(tutores.getIdPaciente());
                RelacionTutoresView view = converter.toView(entity);
                view.setEmail(paciente.getEmail());
                view.setNombreTutor(String.format("%s %s %s", paciente.getNombre(), paciente.getApellidoMaterno(), paciente.getApellidoPaterno()));
                view.setTelefono(paciente.getTelefonoCelular());
                viewList.add(view);
            }
        } catch (Exception ex) {
            logger.error("Error al obtener relaciones - {}", ex.getMessage());
            throw new TutoresException(ex.getMessage(), PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
        }
        return viewList;
    }

    @Override
    public RelacionTutoresView findByIdPaciente(String idPaciente) throws TutoresException {
        RelacionTutoresView view = null;
        try {
            RelacionTutores entity = repository.findByIdPacTutor(idPaciente);
            Tutores tutores = tutoresRepository.findByIdTutor(entity.getIdTutor());
            Paciente paciente = pacienteRepository.getOne(tutores.getIdPaciente());
            view = converter.toView(entity);
            view.setEmail(paciente.getEmail());
            view.setNombreTutor(String.format("%s %s %s", paciente.getNombre(), paciente.getApellidoMaterno(), paciente.getApellidoPaterno()));
            view.setTelefono(paciente.getTelefonoCelular());
        } catch (Exception ex) {
            logger.error("Error al obtener relación con tutor - {}", ex.getMessage());
            throw new TutoresException(ex.getMessage(), PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
        }
        return view;
    }

    @Override
    public RelacionTutoresView findByIdTutor(Integer idTutor) throws TutoresException {
        RelacionTutoresView view = null;
        try {
            // Ahora sí coinciden los tipos con la interfaz y el repositorio
            RelacionTutores entity = repository.findByIdTutor(idTutor);
            Paciente paciente = pacienteRepository.getOne(entity.getIdPacTutor());
            view = converter.toView(entity);
            view.setEmail(paciente.getEmail());
            view.setNombreTutor(String.format("%s %s %s", paciente.getNombre(), paciente.getApellidoMaterno(), paciente.getApellidoPaterno()));
            view.setTelefono(paciente.getTelefonoCelular());
        } catch (Exception ex) {
            logger.error("Error al obtener relación con tutor - {}", ex.getMessage());
            throw new TutoresException(ex.getMessage(), PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
        }
        return view;
    }

}
