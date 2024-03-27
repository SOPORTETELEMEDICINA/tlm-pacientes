package net.amentum.niomedic.pacientes.rest;

import net.amentum.niomedic.pacientes.exception.DatosContactoException;
import net.amentum.niomedic.pacientes.service.DatosContactoService;
import net.amentum.niomedic.pacientes.views.DatosContactoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("pacientes")
public class DatosContactoRest {
   private final Logger logger = LoggerFactory.getLogger(DatosContactoRest.class);

   private DatosContactoService datosContactoService;


    @Autowired
    public void setDatosContactoService(DatosContactoService datosContactoService) { this.datosContactoService= datosContactoService;}

    @RequestMapping(value = "{idPaciente}/datos-contacto",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createDatosContacto(@PathVariable()String idPaciente, @RequestBody @Valid ArrayList<DatosContactoView> datosContactoViewArrayList) throws DatosContactoException {
        try{
            logger.info("===>>>Guardar nuevo datos contacto: {}", datosContactoViewArrayList);
            datosContactoService.createDatosContacto(datosContactoViewArrayList,idPaciente);
        }catch (DatosContactoException dce){
            throw dce;
        }catch(Exception ex){
            DatosContactoException dce = new DatosContactoException("No fue posible insertar datos contacto", DatosContactoException.LAYER_REST, DatosContactoException.ACTION_INSERT);
            logger.error("===>>>Error al insertar el datos contacto- CODE: {} - ",dce.getExceptionCode(),ex);
            throw dce;
        }
    }

    @RequestMapping(value = "{idPaciente}/datos-contacto",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateDatosContacto(@PathVariable()String idPaciente, @RequestBody @Valid ArrayList<DatosContactoView> datosContactoViewArrayList) throws DatosContactoException {
        try{
            logger.info("===>>>Modificar datos contacto: {}", datosContactoViewArrayList);
            datosContactoService.updateDatosContacto(datosContactoViewArrayList,idPaciente);
        }catch (DatosContactoException dce){
            throw dce;
        }catch(Exception ex){
            DatosContactoException dce = new DatosContactoException("No fue posible modificar datos contacto", DatosContactoException.LAYER_REST, DatosContactoException.ACTION_UPDATE);
            logger.error("===>>>Error al modificar datos contacto- CODE: {} - ",dce.getExceptionCode(),ex);
            throw dce;
        }
    }

    @RequestMapping(value = "{idPaciente}/datos-contacto",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDatosContacto(@PathVariable()String idPaciente, @RequestBody @Valid ArrayList<DatosContactoView> datosContactoViewArrayList) throws DatosContactoException {
        try{
            logger.info("===>>>Borrar datos contacto: {}", datosContactoViewArrayList);
            datosContactoService.deleteDatosContacto(datosContactoViewArrayList,idPaciente);
        }catch (DatosContactoException dce){
            throw dce;
        }catch(Exception ex){
            DatosContactoException dce = new DatosContactoException("No fue posible borrar datos contacto", DatosContactoException.LAYER_REST, DatosContactoException.ACTION_DELETE);
            logger.error("===>>>Error al borrar datos contacto- CODE: {} - ",dce.getExceptionCode(),ex);
            throw dce;
        }
    }

}
