package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Domicilio;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.views.DomicilioView;
import net.amentum.niomedic.pacientes.views.PacienteView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class DomicilioConverter {
   private Logger logger = LoggerFactory.getLogger(DomicilioConverter.class);

//   public Domicilio toEntity(DomicilioView domicilioView, Domicilio domicilio, Paciente paciente, Boolean update) {
//      Domicilio dom = new Domicilio();
//      if(update)
//         dom.setIdDomicilio(domicilioView.getIdDomicilio());
//      dom.setDomicilio(domicilioView.getDomicilio());
//      dom.setColonia(domicilioView.getColonia());
//      dom.setMunicipio(domicilioView.getMunicipio());
//      dom.setEstado(domicilioView.getEstado());
//      dom.setPais(domicilioView.getPais());
//      dom.setCp(domicilioView.getCp());
//      dom.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
//      dom.setPaciente(paciente);
//      domicilio = dom;
//      logger.debug("converter domicilioView to Entity: {}", domicilio);
//      return domicilio;
//   }

   public Collection<Domicilio> toEntity(Collection<DomicilioView> domicilioViewArrayList, Collection<Domicilio> domicilioArrayList, Paciente paciente, Boolean update) {
      for (DomicilioView domV : domicilioViewArrayList) {
         Domicilio dom = new Domicilio();
         if (update) {
            dom.setIdDomicilio(domV.getIdDomicilio());
         } else {
//            logger.debug("----->NO es un update");
         }
         dom.setDomicilio(domV.getDomicilio());
         dom.setColonia(domV.getColonia());
         dom.setMunicipio(domV.getMunicipio());
         dom.setEstado(domV.getEstado());
         dom.setPais(domV.getPais());
         dom.setCp(domV.getCp());
         dom.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
         dom.setActivo(domV.getActivo());
         dom.setPaciente(paciente);
         domicilioArrayList.add(dom);
      }
      logger.debug("converter domicilioView to Entity: {}", domicilioArrayList);
      return domicilioArrayList;
   }


//   public DomicilioView toView(Domicilio domicilio, Boolean completeConversion) {
//      DomicilioView domicilioView = new DomicilioView();
//      domicilioView.setIdDomicilio(domicilio.getIdDomicilio());
//      domicilioView.setDomicilio(domicilio.getDomicilio());
//      domicilioView.setColonia(domicilio.getColonia());
//      domicilioView.setMunicipio(domicilio.getMunicipio());
//      domicilioView.setEstado(domicilio.getEstado());
//      domicilioView.setPais(domicilio.getPais());
//      domicilioView.setCp(domicilio.getCp());
//      domicilioView.setFechaCreacion(domicilio.getFechaCreacion());
//      logger.debug("converter domicilio to View: {}", domicilioView);
//      return domicilioView;
//   }

   public Collection<DomicilioView> toView(Collection<Domicilio> domicilioArrayList, Boolean completeConversion) {
      Collection<DomicilioView> domicilioViews = new ArrayList<>();
      for (Domicilio dom : domicilioArrayList) {
         DomicilioView domV = new DomicilioView();
         domV.setIdDomicilio(dom.getIdDomicilio());
         domV.setDomicilio(dom.getDomicilio());
         domV.setColonia(dom.getColonia());
         domV.setMunicipio(dom.getMunicipio());
         domV.setEstado(dom.getEstado());
         domV.setPais(dom.getPais());
         domV.setCp(dom.getCp());
         domV.setFechaCreacion(dom.getFechaCreacion());
         domV.setActivo(dom.getActivo());
         domicilioViews.add(domV);
      }
      logger.debug("converter Domicilio to View: {}", domicilioViews);
      return domicilioViews;
   }

   public Collection<String> obtenerIDNoExistentesDomicilio(Paciente paciente, PacienteView pacienteView) {
//      IDs de DB
      Collection<String> ids = new ArrayList<>();
      ids.addAll(
         paciente.getDomicilioList().stream()
            .map(pv -> {
               String id = pv.getIdDomicilio();
               return id;
            }).collect(Collectors.toList())
      );

//      IDs de View
      Collection<String> idsView = new ArrayList<>();
      idsView.addAll(
         pacienteView.getDomicilioViewList().stream()
            .map(pvV -> {
               String idV = pvV.getIdDomicilio();
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























