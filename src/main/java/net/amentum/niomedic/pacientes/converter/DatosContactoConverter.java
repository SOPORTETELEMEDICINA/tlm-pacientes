package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.DatosContacto;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.views.DatosContactoView;
import net.amentum.niomedic.pacientes.views.PacienteView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class DatosContactoConverter {
   private Logger logger = LoggerFactory.getLogger(DatosContactoConverter.class);

   public Collection<DatosContacto> toEntity(Collection<DatosContactoView> datosContactoViewArrayList, Collection<DatosContacto> datosContactoArrayList, Paciente paciente, Boolean update) {
      for (DatosContactoView dcV : datosContactoViewArrayList) {
         DatosContacto dc = new DatosContacto();
         if (update) {
//            logger.info("--->es un update DatosContactoConverter");
            dc.setIdDatosContacto(dcV.getIdDatosContacto());
         } else {
//            logger.debug("----->NO es un update");
         }
         dc.setNombre(dcV.getNombre());
         dc.setParentesco(dcV.getParentesco());
         dc.setEdad(dcV.getEdad());
         dc.setConvivencia(dcV.getConvivencia());
         dc.setLlamarCasoEmergencia(dcV.getLlamarCasoEmergencia());
         dc.setCuentaConLlaves(dcV.getCuentaConLlaves());
         dc.setTelefonoLocal(dcV.getTelefonoLocal());
         dc.setTelefonoCelular(dcV.getTelefonoCelular());
         dc.setTelefonoOficina(dcV.getTelefonoOficina());
         dc.setTipoApoyoMaterial(dcV.getTipoApoyoMaterial());
         dc.setTipoApoyoEmocional(dcV.getTipoApoyoEmocional());
         dc.setTipoApoyoSocial(dcV.getTipoApoyoSocial());
         dc.setTipoApoyoInstrumental(dcV.getTipoApoyoInstrumental());
         dc.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
         dc.setPrioridad(dcV.getPrioridad());
         dc.setPaciente(paciente);
         datosContactoArrayList.add(dc);
      }
      logger.debug("converter DatosContactoView to Entity: {}", datosContactoArrayList);
      return datosContactoArrayList;
   }

   public Collection<DatosContactoView> toView(Collection<DatosContacto> datosContacto, Boolean completeConversion) {
      Collection<DatosContactoView> datosContactoViews = new ArrayList<>();
      for(DatosContacto dc: datosContacto){
         DatosContactoView dcV = new DatosContactoView();
         dcV.setIdDatosContacto(dc.getIdDatosContacto());
         dcV.setNombre(dc.getNombre());
         dcV.setParentesco(dc.getParentesco());
         dcV.setEdad(dc.getEdad());
         dcV.setConvivencia(dc.getConvivencia());
         dcV.setLlamarCasoEmergencia(dc.getLlamarCasoEmergencia());
         dcV.setCuentaConLlaves(dc.getCuentaConLlaves());
         dcV.setTelefonoLocal(dc.getTelefonoLocal());
         dcV.setTelefonoCelular(dc.getTelefonoCelular());
         dcV.setTelefonoOficina(dc.getTelefonoOficina());
         dcV.setTipoApoyoMaterial(dc.getTipoApoyoMaterial());
         dcV.setTipoApoyoEmocional(dc.getTipoApoyoEmocional());
         dcV.setTipoApoyoSocial(dc.getTipoApoyoSocial());
         dcV.setTipoApoyoInstrumental(dc.getTipoApoyoInstrumental());
         dcV.setFechaCreacion(dc.getFechaCreacion());
         dcV.setPrioridad(dc.getPrioridad());
         datosContactoViews.add(dcV);
      }

      logger.debug("converter DatosContacto to View: {}", datosContactoViews);
      return datosContactoViews;
   }

   public Collection<String> obtenerIDNoExistentesDatosContacto(Paciente paciente, PacienteView pacienteView) {
//      IDs de DB
      Collection<String> ids = new ArrayList<>(paciente.getDatosContactoList().stream()
         .map(dc -> {
            String id = dc.getIdDatosContacto();
            return id;
         }).collect(Collectors.toList()));

//      IDs de View
      Collection<String> idsView = new ArrayList<>();
      idsView.addAll(
         pacienteView.getDatosContactoViewList().stream()
            .map(dcV -> {
               String idV = dcV.getIdDatosContacto();
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
