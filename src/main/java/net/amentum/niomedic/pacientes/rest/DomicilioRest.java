package net.amentum.niomedic.pacientes.rest;

import net.amentum.niomedic.pacientes.exception.DomicilioException;
import net.amentum.niomedic.pacientes.service.DomicilioService;
import net.amentum.niomedic.pacientes.views.DomicilioView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("pacientes")
public class DomicilioRest {
    private final Logger logger = LoggerFactory.getLogger(DomicilioRest.class);

    private DomicilioService domicilioService;

    @Autowired
    public void setDomicilioService(DomicilioService domicilioService) { this.domicilioService= domicilioService;}


    @RequestMapping(value = "{idPaciente}/domicilio",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createDomicilio(@PathVariable()String idPaciente, @RequestBody @Valid ArrayList<DomicilioView> domicilioViewArrayList) throws DomicilioException {
        try{
            logger.info("===>>>Guardar nuevo domicilio: {}", domicilioViewArrayList);
            domicilioService.createDomicilio(domicilioViewArrayList,idPaciente);
        }catch (DomicilioException dome){
            throw dome;
        }catch(Exception ex){
            DomicilioException dome = new DomicilioException("No fue posible insertar domicilio", DomicilioException.LAYER_REST, DomicilioException.ACTION_INSERT);
            logger.error("===>>>Error al insertar domicilio- CODE: {} - ",dome.getExceptionCode(),ex);
            throw dome;
        }
    }

    @RequestMapping(value = "{idPaciente}/domicilio",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateDomicilio(@PathVariable()String idPaciente, @RequestBody @Valid ArrayList<DomicilioView> domicilioViewArrayList) throws DomicilioException {
        try{
            logger.info("===>>>Modificar domicilio: {}", domicilioViewArrayList);
            domicilioService.updateDomicilio(domicilioViewArrayList,idPaciente);
        }catch (DomicilioException dome){
            throw dome;
        }catch(Exception ex){
            DomicilioException dome = new DomicilioException("No fue posible modificar domicilio", DomicilioException.LAYER_REST, DomicilioException.ACTION_UPDATE);
            logger.error("===>>>Error al modificar personas vivivenda- CODE: {} - ",dome.getExceptionCode(),ex);
            throw dome;
        }
    }

    @RequestMapping(value = "{idPaciente}/domicilio",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDomicilio(@PathVariable()String idPaciente, @RequestBody @Valid ArrayList<DomicilioView> domicilioViewArrayList) throws DomicilioException {
        try{
            logger.info("===>>>Borrar domicilio: {}", domicilioViewArrayList);
            domicilioService.deleteDomicilio(domicilioViewArrayList,idPaciente);
        }catch (DomicilioException dome){
            throw dome;
        }catch(Exception ex){
            DomicilioException dome = new DomicilioException("No fue posible borrar domicilio", DomicilioException.LAYER_REST, DomicilioException.ACTION_DELETE);
            logger.error("===>>>Error al borrar domicilio- CODE: {} - ",dome.getExceptionCode(),ex);
            throw dome;
        }
    }

}
