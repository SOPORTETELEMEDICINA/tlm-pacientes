package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.DatosAdicionalesException;
import net.amentum.niomedic.pacientes.service.DatosAdicionalesService;
import net.amentum.niomedic.pacientes.views.DatosAdicionalesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pacientes")
public class DatosAdicionalesRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(DatosAdicionalesRest.class);

    private DatosAdicionalesService datosAdicionalesService;

    @Autowired
    public void setDatosAdicionalesService(DatosAdicionalesService datosAdicionalesService) { this.datosAdicionalesService= datosAdicionalesService;}


    @RequestMapping(value = "{idPaciente}/datos-adicionales",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createdDatosAdicionales(@PathVariable()String idPaciente,@RequestBody @Valid DatosAdicionalesView datosAdicionalesView) throws DatosAdicionalesException {
        try{
            logger.info("===>>>Guardar nuevo datos adicionales: {}", datosAdicionalesView);
            datosAdicionalesService.createDatosAdicionales(datosAdicionalesView,idPaciente);
        }catch (DatosAdicionalesException dae){
            throw dae;
        }catch(Exception ex){
            DatosAdicionalesException dae = new DatosAdicionalesException("No fue posible insertar datos adicionales", DatosAdicionalesException.LAYER_REST, DatosAdicionalesException.ACTION_INSERT);
            logger.error("===>>>Error al insertar  datos adicionales- CODE: {} - ",dae.getExceptionCode(),ex);
            throw dae;
        }
    }

    @RequestMapping(value = "{idPaciente}/datos-adicionales", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateDatosAdicionales(@PathVariable()String idPaciente, @RequestBody @Valid DatosAdicionalesView datosAdicionalesView) throws  DatosAdicionalesException{
        try {
            //datosAdicionalesView.setPacienteId(idPaciente);
            logger.info("===>>>Editar datos adicionales: {}", datosAdicionalesView);
            datosAdicionalesService.updateDatosAdicionales(datosAdicionalesView,idPaciente);
        }catch (DatosAdicionalesException dae){
            throw dae;
        }catch(Exception ex){
            DatosAdicionalesException dae = new DatosAdicionalesException("No fue posible modificar los datos adioionales", DatosAdicionalesException.LAYER_REST, DatosAdicionalesException.ACTION_UPDATE);
            logger.error("===>>>Error al editar los datos adicionales- CODE: {} - ",dae.getExceptionCode(),ex);
            throw dae;
        }
    }

}
