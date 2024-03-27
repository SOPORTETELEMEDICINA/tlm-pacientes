package net.amentum.niomedic.pacientes.rest;

import net.amentum.niomedic.pacientes.exception.PersonasViviendaException;
import net.amentum.niomedic.pacientes.service.PersonasViviendaService;
import net.amentum.niomedic.pacientes.views.PersonasViviendaView;
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
public class PersonasViviendaRest {
   private final Logger logger = LoggerFactory.getLogger(PersonasViviendaRest.class);

   private PersonasViviendaService personasViviendaService;

   @Autowired
   public void setPersonasViviendaService(PersonasViviendaService personasViviendaService) {
      this.personasViviendaService = personasViviendaService;
   }


   @RequestMapping(value = "{idPaciente}/personas-vivienda", method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public void createPersonasVivienda(@PathVariable() String idPaciente, @RequestBody @Valid ArrayList<PersonasViviendaView> personasViviendaViewArrayList) throws PersonasViviendaException {
      try {
         logger.info("===>>>Guardar nuevo personas vivienda: {}", personasViviendaViewArrayList);
         personasViviendaService.createPersonasVivienda(personasViviendaViewArrayList, idPaciente);
      } catch (PersonasViviendaException pve) {
         throw pve;
      } catch (Exception ex) {
         PersonasViviendaException pve = new PersonasViviendaException("No fue posible insertar personas vivienda", PersonasViviendaException.LAYER_REST, PersonasViviendaException.ACTION_INSERT);
         logger.error("===>>>Error al insertar personas vivienda- CODE: {} - ", pve.getExceptionCode(), ex);
         throw pve;
      }
   }

   @RequestMapping(value = "{idPaciente}/personas-vivienda", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public void updatePersonasVivienda(@PathVariable() String idPaciente, @RequestBody @Valid ArrayList<PersonasViviendaView> personasViviendaViewArrayList) throws PersonasViviendaException {
      try {
         logger.info("===>>>Modificar personas vivienda: {}", personasViviendaViewArrayList);
         personasViviendaService.updatePersonasVivienda(personasViviendaViewArrayList, idPaciente);
      } catch (PersonasViviendaException pve) {
         throw pve;
      } catch (Exception ex) {
         PersonasViviendaException pve = new PersonasViviendaException("No fue posible modificar personas vivienda", PersonasViviendaException.LAYER_REST, PersonasViviendaException.ACTION_UPDATE);
         logger.error("===>>>Error al modificar personas vivivenda- CODE: {} - ", pve.getExceptionCode(), ex);
         throw pve;
      }
   }

   @RequestMapping(value = "{idPaciente}/personas-vivienda", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.OK)
   public void deletePersonasVivienda(@PathVariable() String idPaciente, @RequestBody @Valid ArrayList<PersonasViviendaView> personasViviendaViewArrayList) throws PersonasViviendaException {
      try {
         logger.info("===>>>Borrar personas vivienda: {}", personasViviendaViewArrayList);
         personasViviendaService.deletePersonasVivienda(personasViviendaViewArrayList, idPaciente);
      } catch (PersonasViviendaException pve) {
         throw pve;
      } catch (Exception ex) {
         PersonasViviendaException pve = new PersonasViviendaException("No fue posible borrar personas vivienda", PersonasViviendaException.LAYER_REST, PersonasViviendaException.ACTION_DELETE);
         logger.error("===>>>Error al borrar personas vivienda- CODE: {} - ", pve.getExceptionCode(), ex);
         throw pve;
      }
   }

}
