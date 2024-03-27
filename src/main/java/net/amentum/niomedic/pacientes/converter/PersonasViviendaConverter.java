package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.PersonasVivienda;
import net.amentum.niomedic.pacientes.views.PacienteView;
import net.amentum.niomedic.pacientes.views.PersonasViviendaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class PersonasViviendaConverter {
   private Logger logger = LoggerFactory.getLogger(PersonasViviendaConverter.class);

   public Collection<PersonasVivienda> toEntity(Collection<PersonasViviendaView> personasViviendaViewArrayList, Collection<PersonasVivienda> personasViviendaArrayList, Paciente paciente, Boolean update) {
      for (PersonasViviendaView pvV : personasViviendaViewArrayList) {
         PersonasVivienda pv = new PersonasVivienda();
         if (update) {
            pv.setIdPersonasVivienda(pvV.getIdPersonasVivienda());
         } else {
            logger.debug("----->NO es un update");
         }
         pv.setParentesco(pvV.getParentesco());
         pv.setEdad(pvV.getEdad());
         pv.setConvivencia(pvV.getConvivencia());
         pv.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
         pv.setPaciente(paciente);
         personasViviendaArrayList.add(pv);
      }
      logger.debug("converter PersonasViviendaView to Entity: {}", personasViviendaArrayList);
      return personasViviendaArrayList;
   }

   public Collection<PersonasViviendaView> toView(Collection<PersonasVivienda> personasVivienda, Boolean completeConversion) {
      Collection<PersonasViviendaView> personasViviendaViews = new ArrayList<>();
      for (PersonasVivienda pv : personasVivienda) {
         PersonasViviendaView pvV = new PersonasViviendaView();
         pvV.setIdPersonasVivienda(pv.getIdPersonasVivienda());
         pvV.setParentesco(pv.getParentesco());
         pvV.setEdad(pv.getEdad());
         pvV.setConvivencia(pv.getConvivencia());
         pvV.setFechaCreacion(pv.getFechaCreacion());
         personasViviendaViews.add(pvV);
      }

      logger.debug("converter PersonasVivienda to View: {}", personasViviendaViews);
      return personasViviendaViews;
   }

   public Collection<String> obtenerIDNoExistentesPersonasVivienda(Paciente paciente, PacienteView pacienteView) {
//      IDs de DB
      Collection<String> ids = new ArrayList<>();
      ids.addAll(
         paciente.getPersonasViviendaList().stream()
            .map(pv -> {
               String id = pv.getIdPersonasVivienda();
               return id;
            }).collect(Collectors.toList())
      );

//      IDs de View
      Collection<String> idsView = new ArrayList<>();
      idsView.addAll(
         pacienteView.getPersonasViviendaViewList().stream()
            .map(pvV -> {
               String idV = pvV.getIdPersonasVivienda();
               return idV;
            }).collect(Collectors.toList())
      );

//      Obtener los no existentes
      Collection<String> noExisten = new ArrayList<>(ids);
      noExisten.removeAll(idsView);

//      logger.info("--->ids--->{}", ids);
//      logger.info("--->idsView--->{}", idsView);
//      logger.info("--->noExisten--->{}", noExisten);

      return noExisten;
   }
}
