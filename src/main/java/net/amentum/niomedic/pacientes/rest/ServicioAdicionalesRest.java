package net.amentum.niomedic.pacientes.rest;

import net.amentum.niomedic.pacientes.exception.ServicioAdicionalesException;
import net.amentum.niomedic.pacientes.service.ServicioAdicionalesService;
import net.amentum.niomedic.pacientes.views.ServicioAdicionalesView;
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
public class ServicioAdicionalesRest {
   private final Logger logger = LoggerFactory.getLogger(ServicioAdicionalesRest.class);

   private ServicioAdicionalesService servicioAdicionalesService;

   @Autowired
   public void setServicioAdicionalesService(ServicioAdicionalesService servicioAdicionalesService) {
      this.servicioAdicionalesService = servicioAdicionalesService;
   }


   @RequestMapping(value = "{idPaciente}/servicio-adicionales", method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public void createServicioAdicionales(@PathVariable() String idPaciente, @RequestBody @Valid ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList) throws ServicioAdicionalesException {
      try {
         logger.info("===>>>Guardar nuevo servicio adicionales: {}", servicioAdicionalesViewArrayList);
         servicioAdicionalesService.createServicioAdicionales(servicioAdicionalesViewArrayList, idPaciente);
      } catch (ServicioAdicionalesException sae) {
         throw sae;
      } catch (Exception ex) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("No fue posible insertar servicio adicionales", ServicioAdicionalesException.LAYER_REST, ServicioAdicionalesException.ACTION_INSERT);
         logger.error("===>>>Error al insertar el servicio adicionales- CODE: {} - ", sae.getExceptionCode(), ex);
         throw sae;
      }
   }


   @RequestMapping(value = "{idPaciente}/servicio-adicionales", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public void updateServicioAdicionales(@PathVariable() String idPaciente, @RequestBody @Valid ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList) throws ServicioAdicionalesException {
      try {
         logger.info("===>>>Modificar servicio adicionales: {}", servicioAdicionalesViewArrayList);
         servicioAdicionalesService.updateServicioAdicionales(servicioAdicionalesViewArrayList, idPaciente);
      } catch (ServicioAdicionalesException sae) {
         throw sae;
      } catch (Exception ex) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("No fue posible modificar servicio adicionales", ServicioAdicionalesException.LAYER_REST, ServicioAdicionalesException.ACTION_UPDATE);
         logger.error("===>>>Error al modificar servicio adicionales- CODE: {} - ", sae.getExceptionCode(), ex);
         throw sae;
      }
   }


   @RequestMapping(value = "{idPaciente}/servicio-adicionales", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.OK)
   public void deleteServicioAdicionales(@PathVariable() String idPaciente, @RequestBody @Valid ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList) throws ServicioAdicionalesException {
      try {
         logger.info("===>>>Borrar servicio adicionales: {}", servicioAdicionalesViewArrayList);
         servicioAdicionalesService.deleteServicioAdicionales(servicioAdicionalesViewArrayList, idPaciente);
      } catch (ServicioAdicionalesException sae) {
         throw sae;
      } catch (Exception ex) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("No fue posible borrar servicio adicionales", ServicioAdicionalesException.LAYER_REST, ServicioAdicionalesException.ACTION_DELETE);
         logger.error("===>>>Error al borrar servicio adicionales- CODE: {} - ", sae.getExceptionCode(), ex);
         throw sae;
      }
   }

}
