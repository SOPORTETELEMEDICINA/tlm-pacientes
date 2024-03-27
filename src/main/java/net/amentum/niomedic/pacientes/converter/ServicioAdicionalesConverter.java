package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.ServicioAdicionales;
import net.amentum.niomedic.pacientes.views.PacienteView;
import net.amentum.niomedic.pacientes.views.ServicioAdicionalesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class ServicioAdicionalesConverter {
   private Logger logger = LoggerFactory.getLogger(ServicioAdicionalesConverter.class);

   public Collection<ServicioAdicionales> toEntity(Collection<ServicioAdicionalesView> servicioAdicionalesViewArrayList, Collection<ServicioAdicionales> servicioAdicionalesArrayList, Paciente paciente, Boolean update) {
      for (ServicioAdicionalesView saV : servicioAdicionalesViewArrayList) {
         ServicioAdicionales sa = new ServicioAdicionales();
         if (update) {
            sa.setIdServicioAdicionales(saV.getIdServicioAdicionales());
         } else {
            logger.debug("----->NO es un update");
         }
         sa.setTipoServicio(saV.getTipoServicio());
         sa.setNombre(saV.getNombre());
         sa.setDomicilio(saV.getDomicilio());
         sa.setComentarios(saV.getComentarios());
         sa.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
         sa.setPaciente(paciente);
         servicioAdicionalesArrayList.add(sa);
      }
      logger.debug("converter ServicioAdicionalesView to Entity: {}", servicioAdicionalesArrayList);
      return servicioAdicionalesArrayList;
   }

   public Collection<ServicioAdicionalesView> toView(Collection<ServicioAdicionales> servicioAdicionales, Boolean completeConversion) {
      Collection<ServicioAdicionalesView> servicioAdicionalesViews = new ArrayList<>();
      for (ServicioAdicionales sa : servicioAdicionales) {
         ServicioAdicionalesView saV = new ServicioAdicionalesView();
         saV.setIdServicioAdicionales(sa.getIdServicioAdicionales());
         saV.setTipoServicio(sa.getTipoServicio());
         saV.setNombre(sa.getNombre());
         saV.setDomicilio(sa.getDomicilio());
         saV.setComentarios(sa.getComentarios());
         saV.setFechaCreacion(sa.getFechaCreacion());
         servicioAdicionalesViews.add(saV);
      }

      logger.debug("converter ServicioAdicionales to View: {}", servicioAdicionalesViews);
      return servicioAdicionalesViews;
   }
   public Collection<String> obtenerIDNoExistentesServicioAdicionales(Paciente paciente, PacienteView pacienteView) {
//      IDs de DB
      Collection<String> ids = new ArrayList<>();
      ids.addAll(
         paciente.getServicioAdicionalesList().stream()
            .map(sa -> {
               String id = sa.getIdServicioAdicionales();
               return id;
            }).collect(Collectors.toList())
      );

//      IDs de View
      Collection<String> idsView = new ArrayList<>();
      idsView.addAll(
         pacienteView.getServicioAdicionalesViewList().stream()
            .map(saV -> {
               String idV = saV.getIdServicioAdicionales();
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

