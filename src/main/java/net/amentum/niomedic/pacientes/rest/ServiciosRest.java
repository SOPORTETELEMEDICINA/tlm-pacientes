package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.ServiciosException;
import net.amentum.niomedic.pacientes.service.ServiciosP;
import net.amentum.niomedic.pacientes.views.CatServicio;
import net.amentum.niomedic.pacientes.views.ServiciosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("paciente-servicios")
public class ServiciosRest extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ServiciosRest.class);

    @Autowired
    private ServiciosP service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createService(@RequestBody @Valid ServiciosView view) throws ServiciosException {
        try {
            logger.info("====>Guardar servicio - {}", view);
            if(view.getIdServicio() == null || view.getIdServicio() <= 0) {
                logger.error("Campo de servicio vacío");
                throw new ServiciosException("Campo de servicio vacío", ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
            }
            if(view.getIdPaciente() == null || view.getIdPaciente().isEmpty()) {
                logger.error("Campo de idPaciente vacío");
                throw new ServiciosException("Campo de nombre vacío", ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
            }
            service.createServicio(view);
        } catch (Exception ex) {
            throw new ServiciosException("Error al insertar servicio para paciente - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteServiceById(@RequestParam() String idPaciente) throws ServiciosException {
        try {
            logger.info("deleteServiceById() - idPaciente - {} ", idPaciente);
            if(idPaciente == null || idPaciente.isEmpty()) {
                logger.error("idPaciente vacio/null");
                throw new ServiciosException("id para buscar vacio", ServiciosException.LAYER_REST, ServiciosException.ACTION_SELECT);
            }
            service.deleteServiceById(idPaciente);
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            throw new ServiciosException("Error al eliminar un servicio - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_SELECT);
        }
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatServicio getServiciosByUsuario(@RequestParam() String idPaciente) throws ServiciosException {
        try {
            logger.info("getServiciosByUsuario() - idPaciente - {}", idPaciente);
            if(idPaciente == null || idPaciente.isEmpty()) {
                logger.error("idPaciente vacio/null");
                throw new ServiciosException("id para buscar vacio", ServiciosException.LAYER_REST, ServiciosException.ACTION_SELECT);
            }
            return service.findByIdPaciente(idPaciente);
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            throw new ServiciosException("Error al buscar un servicio por paciente - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_SELECT);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public void addServiciosByUsuario(@RequestParam() String idPaciente) throws ServiciosException {
        try {
            logger.info("getServiciosByUsuario() - idPaciente - {}", idPaciente);
            if(idPaciente == null || idPaciente.isEmpty()) {
                logger.error("idPaciente vacio/null");
                throw new ServiciosException("id para buscar vacio", ServiciosException.LAYER_REST, ServiciosException.ACTION_SELECT);
            }
            service.addService(idPaciente);
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            throw new ServiciosException("Error al agregar servicios al paciente - " + idPaciente + " error - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_SELECT);
        }
    }

}
