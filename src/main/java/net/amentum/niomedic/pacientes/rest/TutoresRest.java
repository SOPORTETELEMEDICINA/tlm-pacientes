package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.service.TutoresService;
import net.amentum.niomedic.pacientes.views.TutoresView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("Tutores")
public class TutoresRest extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(TutoresRest.class);

    private TutoresService service;

    @Autowired
    public void setService(TutoresService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutores(@RequestBody @Valid TutoresView tutoresView) throws TutoresException {
        try {
            logger.info("====>Guardar nuevo tutor - {}", tutoresView);
            if(tutoresView.getIdPaciente() != null && tutoresView.getIdPaciente().isEmpty())
                throw new TutoresException("Campo de paciente vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            service.createTutores(tutoresView);
        } catch (Exception ex) {
            throw new TutoresException("Error al insertar - " + ex.getMessage(), PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TutoresView> findAll() throws TutoresException {
        return service.findAll();
    }
}
