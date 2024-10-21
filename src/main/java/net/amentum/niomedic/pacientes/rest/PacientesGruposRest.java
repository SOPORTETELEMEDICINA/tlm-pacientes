package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.PacientesGruposException;
import net.amentum.niomedic.pacientes.service.PacientesGruposService;
import net.amentum.niomedic.pacientes.views.PacientesGruposView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes_grupos")
public class PacientesGruposRest extends BaseController {
    final Logger logger = LoggerFactory.getLogger(PacientesGruposRest.class);

    @Autowired
    PacientesGruposService service;

    @RequestMapping(value = "{idPaciente}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PacientesGruposView findFirstByIdPaciente(@PathVariable() String idPaciente) throws PacientesGruposException {
        try {
            logger.info("Buscando grupo del paciente: {}", idPaciente);
            if(idPaciente == null) {
                logger.error("Id Cuestionario vacío/nulo");
                PacientesGruposException catEx = new PacientesGruposException("Ocurrió un error al buscar el cuestionario", PacientesGruposException.LAYER_REST, PacientesGruposException.ACTION_VALIDATE);
                catEx.addError("Id cuestionario vacío/nulo");
                throw catEx;
            }
            return service.findFirstByIdPaciente(idPaciente);
        } catch (PacientesGruposException ex) {
            throw ex;
        } catch(Exception ex) {
            logger.error("Error al buscar un cuestionario : ", ex);
            PacientesGruposException catEx = new PacientesGruposException("Ocurrió un error al buscar el cuestionario", PacientesGruposException.LAYER_REST, PacientesGruposException.ACTION_INSERT);
            catEx.addError("Error al buscar un cuestionario: " + ex);
            throw catEx;
        }
    }
}
