package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.TutoresConverter;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.Tutores;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.persistence.TutoresRepository;
import net.amentum.niomedic.pacientes.service.TutoresService;
import net.amentum.niomedic.pacientes.views.TutoresView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TutoresServiceImpl implements TutoresService {

    private final Logger logger = LoggerFactory.getLogger(TutoresServiceImpl.class);

    private TutoresRepository repository;
    private PacienteRepository pacienteRepository;
    private TutoresConverter converter;

    @Autowired
    public void setRepository(TutoresRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPacienteRepository(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Autowired
    public void setConverter(TutoresConverter converter) {
        this.converter = converter;
    }

    @Transactional(rollbackFor = {TutoresException.class})
    @Override
    public void createTutores(TutoresView tutoresView) throws TutoresException {
        try {
            if(repository.existTutor(tutoresView.getIdPaciente()))
                throw new TutoresException("Tutor repetido", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            repository.save(converter.toEntity(tutoresView));
        } catch (Exception ex) {
            logger.error("Error al insertar el tutor - {}", ex.getMessage());
            throw new TutoresException("Error al insertar nuevo tutor - " + ex.getMessage(), PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
        }
    }

    @Override
    public List<TutoresView> findAll() throws TutoresException {
        List<Tutores> entityList = repository.findAll();
        List<TutoresView> viewList = new ArrayList<>();
        for(Tutores entity : entityList) {
            Paciente paciente = pacienteRepository.getOne(entity.getIdPaciente());
            TutoresView view = converter.toView(entity);
            view.setNombreTutor(String.format("%s %s %s", paciente.getNombre(), paciente.getApellidoMaterno(), paciente.getApellidoPaterno()));
            viewList.add(view);
        }
        return viewList;
    }
}
