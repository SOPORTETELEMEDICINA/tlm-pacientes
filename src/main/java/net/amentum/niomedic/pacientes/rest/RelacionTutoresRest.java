package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.RelacionTutores;
import net.amentum.niomedic.pacientes.service.RelacionTutoresService;
import net.amentum.niomedic.pacientes.views.RelacionTutoresView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("relacion-tutores")
public class RelacionTutoresRest extends BaseController  {

    private final Logger logger = LoggerFactory.getLogger(RelacionTutoresRest.class);

    private RelacionTutoresService service;

    @Autowired
    public void setService(RelacionTutoresService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRelacionTutores(@RequestBody @Valid RelacionTutoresView tutoresView) throws TutoresException {
        try {
            logger.info("====>Guardar relacion tutor - {}", tutoresView);
            //if(tutoresView.getIdTutor() == null || tutoresView.getIdTutor() <= 0) {
            if(tutoresView.getIdTutor() == null ) {
                logger.error("Campo id tutor vacío");
                throw new TutoresException("Campo id tutor vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            }
            if(tutoresView.getIdPacTutor() == null || tutoresView.getIdPacTutor().isEmpty()) {
                logger.error("Campo id de paciente vacío");
                throw new TutoresException("Campo de paciente vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            }
            if(tutoresView.getParentesco() == null || tutoresView.getParentesco().isEmpty()) {
                logger.error("Campo parentesco vacío");
                throw new TutoresException("Campo de parentesco vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            }
            service.createRelacionTutores(tutoresView);
        } catch (Exception ex) {
            throw new TutoresException("Error al insertar relación de tutor - " + ex.getMessage(), PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RelacionTutoresView> findAll() throws TutoresException {
        return service.findAll();
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RelacionTutoresView getRelacionTutor(@RequestParam() String idPaciente) throws TutoresException {
        try {
            logger.info("getRelacionTutor() - idPaciente - {}", idPaciente);

            if(idPaciente == null || idPaciente.isEmpty()) {
                logger.error("idPaciente vacio/null");
                throw new TutoresException("id para buscar vacio", PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
            }

            return service.findByIdPaciente(idPaciente);
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            throw new TutoresException("Error al buscar una relacion de tutor - " + ex.getMessage(), PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
        }
    }

    @RequestMapping(value = "find-by-tutor/{idTutor}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RelacionTutoresView getRelacionTutorByTutor(@PathVariable("idTutor") String idTutor) throws TutoresException {
        try {
            logger.info("getRelacionTutor() - idPaciente - {}", idTutor);
            if(idTutor == null || idTutor.isEmpty()) {
                logger.error("idTutor vacio/null");
                throw new TutoresException("id para buscar vacio", PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
            }
            return service.findByIdTutor(idTutor);
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            throw new TutoresException("Error al buscar una relacion de tutor by tutor - " + ex.getMessage(), PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
        }
    }

}
