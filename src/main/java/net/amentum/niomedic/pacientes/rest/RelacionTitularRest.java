package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.service.RelacionTitularService;
import net.amentum.niomedic.pacientes.views.RelacionTitularView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("relacion-titular")
public class RelacionTitularRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(RelacionTutoresRest.class);

    private RelacionTitularService service;

    @Autowired
    public void setService(RelacionTitularService service) { this.service = service; }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRelacionTitular(@RequestBody @Valid RelacionTitularView titularView) throws TutoresException {
        try {
            logger.info("====>Guardar relacion titular - {}", titularView);

            if(titularView.getIdPacienteTitular() == null || titularView.getIdPacienteTitular().isEmpty()) {
                logger.error("Campo id de titular vacío");
                throw new TutoresException("Campo de titular vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            }

            if(titularView.getIdPacienteBeneficiario() == null || titularView.getIdPacienteBeneficiario().isEmpty()) {
                logger.error("Campo beneficiario vacío");
                throw new TutoresException("Campo de beneficiario vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            }

            if(titularView.getParentesco() == null || titularView.getParentesco().isEmpty()) {
                logger.error("Campo parentesco vacío");
                throw new TutoresException("Campo de parentesco vacío", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
            }

            service.createRelacionTitular(titularView);
        } catch (Exception ex) {
            throw new TutoresException("Error al insertar relación titular - " + ex.getMessage(), PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
        }
    }

    @RequestMapping(value = "beneficiarios/{idPacienteTitular}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PacienteBeneficiarioDTO>> getBeneficiariosTitular(@PathVariable("idPacienteTitular") String idPacienteTitular) throws Exception {
        List<PacienteBeneficiarioDTO> pacientes = service.getBeneficiariosTitular(idPacienteTitular);

        if (pacientes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pacientes);
    }
}
