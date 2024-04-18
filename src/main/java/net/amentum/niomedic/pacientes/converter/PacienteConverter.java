package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.*;
import net.amentum.niomedic.pacientes.views.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PacienteConverter {
   private Logger logger = LoggerFactory.getLogger(PacienteConverter.class);

   private DatosContactoConverter datosContactoConverter;
   private ServicioAdicionalesConverter servicioAdicionalesConverter;
   private PersonasViviendaConverter personasViviendaConverter;
   private DatosAdicionalesConverter datosAdicionalesConverter;
   private DomicilioConverter domicilioConverter;

   @Autowired
   public void setDatosContactoConverter(DatosContactoConverter datosContactoConverter) {
      this.datosContactoConverter = datosContactoConverter;
   }

   @Autowired
   public void setServicioAdicionalesConverter(ServicioAdicionalesConverter servicioAdicionalesConverter) {
      this.servicioAdicionalesConverter = servicioAdicionalesConverter;
   }

   @Autowired
   public void setPersonasViviendaConverter(PersonasViviendaConverter personasViviendaConverter) {
      this.personasViviendaConverter = personasViviendaConverter;
   }

   @Autowired
   public void setDatosAdicionalesConverter(DatosAdicionalesConverter datosAdicionalesConverter) {
      this.datosAdicionalesConverter = datosAdicionalesConverter;
   }

   @Autowired
   public void setDomicilioConverter(DomicilioConverter domicilioConverter) {
      this.domicilioConverter = domicilioConverter;
   }

   public Paciente toEntity(PacienteView pacienteView, Paciente paciente, Boolean update) {
      paciente.setNombre(pacienteView.getNombre());
      paciente.setApellidoPaterno(pacienteView.getApellidoPaterno());
      paciente.setApellidoMaterno(pacienteView.getApellidoMaterno());
      paciente.setFechaNacimiento(pacienteView.getFechaNacimiento());
      paciente.setLugarNacimiento(pacienteView.getLugarNacimiento());
      paciente.setCurp(pacienteView.getCurp());
      paciente.setSexo(pacienteView.getSexo());
      paciente.setReligion(pacienteView.getReligion());
      paciente.setEstadoCivil(pacienteView.getEstadoCivil());
      paciente.setTelefonoLocal(pacienteView.getTelefonoLocal());
      paciente.setTelefonoCelular(pacienteView.getTelefonoCelular());
      paciente.setEmail(pacienteView.getEmail());
      paciente.setRfc(pacienteView.getRfc());
      paciente.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
      paciente.setIdUsuario(pacienteView.getIdUsuario());
      paciente.setActivo(pacienteView.getActivo());
      paciente.setEsTutor(pacienteView.getEsTutor());
      paciente.setIdDevice(pacienteView.getIdDevice());
      paciente.setClaveElector(pacienteView.getClaveElector());
      paciente.setPacienteAtendido(pacienteView.getPacienteAtendido());

      // nuevos campos
      paciente.setPadecimientoCronico(pacienteView.getPadecimientoCronico());
      paciente.setAlergias(pacienteView.getAlergias());
      paciente.setTipoSangre(pacienteView.getTipoSangre());
      paciente.setAfiliacion(pacienteView.getAfiliacion());
      paciente.setNumeroAfiliacion(pacienteView.getNumeroAfiliacion());
      paciente.setNumeroExpediente(pacienteView.getNumeroExpediente());
      paciente.setUserName(pacienteView.getUserName());
      paciente.setTransfusiones(pacienteView.getTransfusiones());
      paciente.setIdUnidadMedica(pacienteView.getIdUnidadMedica());
//      el campo de busqueda
      String datosBusqueda = pacienteView.getNombre()+" "+pacienteView.getApellidoPaterno();
      if(pacienteView.getApellidoMaterno()!=null && !pacienteView.getApellidoMaterno().isEmpty()) {
    	  datosBusqueda = datosBusqueda + " " +pacienteView.getApellidoMaterno();
      }
      if(pacienteView.getCurp()!=null){
    	  datosBusqueda = datosBusqueda + " " + pacienteView.getCurp();
      }
      paciente.setDatosBusqueda(datosBusqueda);

      if (!update) {
//         logger.info("--->es un create PacienteConverter");
         if (pacienteView.getDatosContactoViewList() != null && !pacienteView.getDatosContactoViewList().isEmpty()) {
            paciente.setDatosContactoList(datosContactoConverter.toEntity(
               pacienteView.getDatosContactoViewList(),
               paciente.getDatosContactoList(),
               paciente,
               Boolean.FALSE));

//                paciente.getDatosContactoList().addAll(
//                        pacienteView.getDatosContactoViewList().stream()
//                                .map(dcView -> {
//                                    DatosContacto dc = new DatosContacto();
//                                    if (dcView.getIdDatosContacto() != null && !dcView.getIdDatosContacto().equals(""))
//                                        dc.setIdDatosContacto(dcView.getIdDatosContacto());
//                                    dc.setNombre(dcView.getNombre());
//                                    dc.setParentesco(dcView.getParentesco());
//                                    dc.setEdad(dcView.getEdad());
//                                    dc.setConvivencia(dcView.getConvivencia());
//                                    dc.setLlamarCasoEmergencia(dcView.getLlamarCasoEmergencia());
//                                    dc.setCuentaConLlaves(dcView.getCuentaConLlaves());
//                                    dc.setTelefonoLocal(dcView.getTelefonoLocal());
//                                    dc.setTelefonoCelular(dcView.getTelefonoCelular());
//                                    dc.setTelefonoOficina(dcView.getTelefonoOficina());
//                                    dc.setTipoApoyoMaterial(dcView.getTipoApoyoMaterial());
//                                    dc.setTipoApoyoEmocional(dcView.getTipoApoyoEmocional());
//                                    dc.setTipoApoyoSocial(dcView.getTipoApoyoSocial());
//                                    dc.setTipoApoyoInstrumental(dcView.getTipoApoyoInstrumental());
//                                    dc.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
//                                    dc.setPaciente(paciente);
//                                    return dc;
//                                }).collect(Collectors.toList())
//                );
         }

         if (pacienteView.getServicioAdicionalesViewList() != null && !pacienteView.getServicioAdicionalesViewList().isEmpty()) {
            paciente.setServicioAdicionalesList(servicioAdicionalesConverter.toEntity(
               pacienteView.getServicioAdicionalesViewList(),
               paciente.getServicioAdicionalesList(),
               paciente,
               Boolean.FALSE
            ));

//            paciente.getServicioAdicionalesList().addAll(
//               pacienteView.getServicioAdicionalesViewList().stream()
//                  .map(saView -> {
//                     ServicioAdicionales sa = new ServicioAdicionales();
//                     if (saView.getIdServicioAdicionales() != null && !saView.getIdServicioAdicionales().equals(""))
//                        sa.setIdServicioAdicionales(saView.getIdServicioAdicionales());
//                     sa.setTipoServicio(saView.getTipoServicio());
//                     sa.setNombre(saView.getNombre());
//                     sa.setDomicilio(saView.getDomicilio());
//                     sa.setComentarios(saView.getComentarios());
//                     sa.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
//                     sa.setPaciente(paciente);
//                     return sa;
//                  }).collect(Collectors.toList())
//            );
         }

         if (pacienteView.getPersonasViviendaViewList() != null && !pacienteView.getPersonasViviendaViewList().isEmpty()) {
            paciente.setPersonasViviendaList(personasViviendaConverter.toEntity(
               pacienteView.getPersonasViviendaViewList(),
               paciente.getPersonasViviendaList(),
               paciente,
               Boolean.FALSE
            ));

//            paciente.getPersonasViviendaList().addAll(
//               pacienteView.getPersonasViviendaViewList().stream()
//                  .map(pvView -> {
//                     PersonasVivienda pv = new PersonasVivienda();
//                     if (pvView.getIdPersonasVivienda() != null && !pvView.getIdPersonasVivienda().equals(""))
//                        pv.setIdPersonasVivienda(pvView.getIdPersonasVivienda());
//                     pv.setParentesco(pvView.getParentesco());
//                     pv.setEdad(pvView.getEdad());
//                     pv.setConvivencia(pvView.getConvivencia());
//                     pv.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
//                     pv.setPaciente(paciente);
//                     return pv;
//                  }).collect(Collectors.toList())
//            );
         }

         if (pacienteView.getDatosAdicionalesView() != null) {
            paciente.setDatosAdicionales(datosAdicionalesConverter.toEntity(
               pacienteView.getDatosAdicionalesView(),
               paciente.getDatosAdicionales(),
               paciente,
               Boolean.FALSE
            ));

//            DatosAdicionalesView daView = pacienteView.getDatosAdicionalesView();
//            DatosAdicionales da = new DatosAdicionales();
//            if (daView.getIdDatosAdicionales() != null && !daView.getIdDatosAdicionales().equals(""))
//               da.setIdDatosAdicionales(daView.getIdDatosAdicionales());
//            da.setEscolaridad(daView.getEscolaridad());
//            da.setOcupacion(daView.getOcupacion());
//            da.setOcupacionAnterior(daView.getOcupacionAnterior());
//            da.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
////            da.setFechaCreacion((!update) ? new Date() : daView.getFechaCreacion());
//            da.setOrigenEtnico(daView.getOrigenEtnico());
//            da.setViveSolo(daView.getViveSolo());
//            da.setPaciente(paciente);
//            paciente.setDatosAdicionales(da);
         }

         if(pacienteView.getDomicilioViewList()!=null && !pacienteView.getDomicilioViewList().isEmpty()){
            paciente.setDomicilioList(domicilioConverter.toEntity(
               pacienteView.getDomicilioViewList(),
               paciente.getDomicilioList(),
               paciente,
               Boolean.FALSE
            ));
         }

//         si es relacion OneToOne
//         if (pacienteView.getDomicilioView() != null) {
//            paciente.setDomicilio(domicilioConverter.toEntity(
//               pacienteView.getDomicilioView(),
//               paciente.getDomicilio(),
//               paciente,
//               Boolean.FALSE
//            ));

//
//            DomicilioView domView = pacienteView.getDomicilioView();
//            Domicilio dom;
//            if (!update) {
//               dom = new Domicilio();
//            } else {
//               dom = paciente.getDomicilio();
//            }
//            dom.setDomicilio(domView.getDomicilio());
//            dom.setColonia(domView.getColonia());
//            dom.setMunicipio(domView.getMunicipio());
//            dom.setEstado(domView.getEstado());
//            dom.setPais(domView.getPais());
//            dom.setCp(domView.getCp());
//            dom.setFechaCreacion((!update) ? new Date() : domView.getFechaCreacion());
//            dom.setPaciente(paciente);
//            paciente.setDomicilio(dom);
//         }

      } else {
//         logger.info("--->es un update PacienteConverter");
         if (pacienteView.getDatosContactoViewList() != null && !pacienteView.getDatosContactoViewList().isEmpty()) {
            paciente.setDatosContactoList(datosContactoConverter.toEntity(
               pacienteView.getDatosContactoViewList(),
               paciente.getDatosContactoList(),
               paciente,
               Boolean.TRUE));
         }
         if (pacienteView.getServicioAdicionalesViewList() != null && !pacienteView.getServicioAdicionalesViewList().isEmpty()) {
            paciente.setServicioAdicionalesList(servicioAdicionalesConverter.toEntity(
               pacienteView.getServicioAdicionalesViewList(),
               paciente.getServicioAdicionalesList(),
               paciente,
               Boolean.TRUE
            ));
         }
         if (pacienteView.getPersonasViviendaViewList() != null && !pacienteView.getPersonasViviendaViewList().isEmpty()) {
            paciente.setPersonasViviendaList(personasViviendaConverter.toEntity(
               pacienteView.getPersonasViviendaViewList(),
               paciente.getPersonasViviendaList(),
               paciente,
               Boolean.TRUE
            ));
         }

         if (pacienteView.getDatosAdicionalesView() != null) {
            paciente.setDatosAdicionales(datosAdicionalesConverter.toEntity(
               pacienteView.getDatosAdicionalesView(),
               paciente.getDatosAdicionales(),
               paciente,
               Boolean.TRUE
            ));
         }

         if(pacienteView.getDomicilioViewList()!=null && !pacienteView.getDomicilioViewList().isEmpty()){
            paciente.setDomicilioList(domicilioConverter.toEntity(
               pacienteView.getDomicilioViewList(),
               paciente.getDomicilioList(),
               paciente,
               Boolean.TRUE
            ));
         }
      }

      logger.debug("convertir PacienteView to Entity: {}", paciente);
      return paciente;
   }

   public PacienteView toView(Paciente paciente, Boolean completeConversion) {
      PacienteView pacienteView = new PacienteView();
      pacienteView.setIdPaciente(paciente.getIdPaciente());
      pacienteView.setNombre(paciente.getNombre());
      pacienteView.setApellidoPaterno(paciente.getApellidoPaterno());
      pacienteView.setApellidoMaterno(paciente.getApellidoMaterno());
      pacienteView.setFechaNacimiento(paciente.getFechaNacimiento());
      pacienteView.setLugarNacimiento(paciente.getLugarNacimiento());
      pacienteView.setCurp(paciente.getCurp());
      pacienteView.setSexo(paciente.getSexo());
      pacienteView.setReligion(paciente.getReligion());
      pacienteView.setEstadoCivil(paciente.getEstadoCivil());
      pacienteView.setTelefonoLocal(paciente.getTelefonoLocal());
      pacienteView.setTelefonoCelular(paciente.getTelefonoCelular());
      pacienteView.setEmail(paciente.getEmail());
      pacienteView.setRfc(paciente.getRfc());
      pacienteView.setFechaCreacion(paciente.getFechaCreacion());
      pacienteView.setIdUsuario(paciente.getIdUsuario());
      pacienteView.setActivo(paciente.getActivo());
      pacienteView.setEsTutor(paciente.getEsTutor());
      pacienteView.setIdDevice(paciente.getIdDevice());
      pacienteView.setClaveElector(paciente.getClaveElector());
      // nuevos campos
      pacienteView.setPadecimientoCronico(paciente.getPadecimientoCronico());
      pacienteView.setAlergias(paciente.getAlergias());
      pacienteView.setTipoSangre(paciente.getTipoSangre());
      pacienteView.setAfiliacion(paciente.getAfiliacion());
      pacienteView.setNumeroAfiliacion(paciente.getNumeroAfiliacion());
      pacienteView.setNumeroExpediente(paciente.getNumeroExpediente());
      pacienteView.setTransfusiones(paciente.getTransfusiones());
      pacienteView.setIdUnidadMedica(paciente.getIdUnidadMedica());
      pacienteView.setUserName(paciente.getUserName());
      pacienteView.setPacienteAtendido(paciente.getPacienteAtendido());


      if (completeConversion) {
         if (paciente.getDatosContactoList() != null && !paciente.getDatosContactoList().isEmpty()) {
            pacienteView.setDatosContactoViewList(datosContactoConverter.toView(paciente.getDatosContactoList(), true));
         }

//         pacienteView.getDatosContactoViewList().addAll(
//            paciente.getDatosContactoList().stream()
//               .map(dc -> {
//                  DatosContactoView dcView = new DatosContactoView();
//                  dcView.setNombre(dc.getNombre());
//                  dcView.setParentesco(dc.getParentesco());
//                  dcView.setEdad(dc.getEdad());
//                  dcView.setConvivencia(dc.getConvivencia());
//                  dcView.setLlamarCasoEmergencia(dc.getLlamarCasoEmergencia());
//                  dcView.setCuentaConLlaves(dc.getCuentaConLlaves());
//                  dcView.setTelefonoLocal(dc.getTelefonoLocal());
//                  dcView.setTelefonoCelular(dc.getTelefonoCelular());
//                  dcView.setTelefonoOficina(dc.getTelefonoOficina());
//                  dcView.setTipoApoyoMaterial(dc.getTipoApoyoMaterial());
//                  dcView.setTipoApoyoEmocional(dc.getTipoApoyoEmocional());
//                  dcView.setTipoApoyoSocial(dc.getTipoApoyoSocial());
//                  dcView.setTipoApoyoInstrumental(dc.getTipoApoyoInstrumental());
//                  dcView.setFechaCreacion(dc.getFechaCreacion());
//                  return dcView;
//               }).collect(Collectors.toList())
//         );

         if (paciente.getServicioAdicionalesList() != null && !paciente.getServicioAdicionalesList().isEmpty()) {
            pacienteView.setServicioAdicionalesViewList(servicioAdicionalesConverter.toView(paciente.getServicioAdicionalesList(), true));
         }

//         pacienteView.getServicioAdicionalesViewList().addAll(
//            paciente.getServicioAdicionalesList().stream()
//               .map(sa -> {
//                  ServicioAdicionalesView saView = new ServicioAdicionalesView();
//                  saView.setTipoServicio(sa.getTipoServicio());
//                  saView.setNombre(sa.getNombre());
//                  saView.setDomicilio(sa.getDomicilio());
//                  saView.setComentarios(sa.getComentarios());
//                  saView.setFechaCreacion(sa.getFechaCreacion());
//                  return saView;
//               }).collect(Collectors.toList())
//         );

         if (paciente.getPersonasViviendaList() != null && !paciente.getPersonasViviendaList().isEmpty()) {
            pacienteView.setPersonasViviendaViewList(personasViviendaConverter.toView(paciente.getPersonasViviendaList(), true));
         }

//         pacienteView.getPersonasViviendaViewList().addAll(
//            paciente.getPersonasViviendaList().stream()
//               .map(pv -> {
//                  PersonasViviendaView pvView = new PersonasViviendaView();
//                  pvView.setParentesco(pv.getParentesco());
//                  pvView.setEdad(pv.getEdad());
//                  pvView.setConvivencia(pv.getConvivencia());
//                  pvView.setFechaCreacion(pv.getFechaCreacion());
//                  return pvView;
//               }).collect(Collectors.toList())
//         );

         if (paciente.getDatosAdicionales() != null) {
            pacienteView.setDatosAdicionalesView(datosAdicionalesConverter.toView(paciente.getDatosAdicionales(), true));
         }

//         DatosAdicionales da = paciente.getDatosAdicionales();
//         DatosAdicionalesView daView = new DatosAdicionalesView();
//         daView.setEscolaridad(da.getEscolaridad());
//         daView.setOcupacion(da.getOcupacion());
//         daView.setOcupacionAnterior(da.getOcupacionAnterior());
//         daView.setFechaCreacion(da.getFechaCreacion());
//         daView.setOrigenEtnico(da.getOrigenEtnico());
//         daView.setViveSolo(da.getViveSolo());
//
//         pacienteView.setDatosAdicionalesView(daView);

         if(paciente.getDomicilioList()!=null && !paciente.getDomicilioList().isEmpty()){
            pacienteView.setDomicilioViewList(domicilioConverter.toView(paciente.getDomicilioList(),true));
         }

//       si es relacion OneToOne
//         if (paciente.getDomicilio() != null) {
//            pacienteView.setDomicilioView(domicilioConverter.toView(paciente.getDomicilio(), true));
//         }

//         Domicilio dom = paciente.getDomicilio();
//         DomicilioView domView = new DomicilioView();
//         domView.setDomicilio(dom.getDomicilio());
//         domView.setColonia(dom.getColonia());
//         domView.setMunicipio(dom.getMunicipio());
//         domView.setEstado(dom.getEstado());
//         domView.setPais(dom.getPais());
//         domView.setCp(dom.getCp());
//         domView.setFechaCreacion(dom.getFechaCreacion());
//
//         pacienteView.setDomicilioView(domView);

      }
      logger.debug("convertir Paciente to View: {}", pacienteView);
      return pacienteView;
   }

//   public PacienteView toViewPage(Paciente paciente) {
//      PacienteView pacienteView = new PacienteView();
//      pacienteView.setNombre(paciente.getNombre() + " " + paciente.getApellidoPaterno() + " " + paciente.getApellidoMaterno());
//      pacienteView.setFechaCreacion(paciente.getFechaCreacion());
//      pacienteView.setIdUsuario(paciente.getIdUsuario());
//      logger.debug("converter paciente to View-Page: {}", pacienteView);
//      return pacienteView;
//   }

   public PacientePageView toViewPage(Paciente paciente) {
      PacientePageView pacientePageView = new PacientePageView();
      String nombrePaciente=paciente.getNombre();
      if(paciente.getApellidoPaterno()!= null) {
    	  nombrePaciente  = nombrePaciente + " " +paciente.getApellidoPaterno(); 
      }
      
      if(paciente.getApellidoPaterno()!= null && paciente.getApellidoMaterno()!=null) {
    	nombrePaciente = nombrePaciente + " " +  paciente.getApellidoMaterno();
      }
      pacientePageView.setNombre(nombrePaciente);
      pacientePageView.setFechaCreacion(paciente.getFechaCreacion());
      pacientePageView.setIdUsuario(paciente.getIdUsuario());
      pacientePageView.setUserName(paciente.getUserName());
      pacientePageView.setIdPaciente(paciente.getIdPaciente());
      pacientePageView.setCurp(paciente.getCurp());
      pacientePageView.setPacienteAtendido(paciente.getPacienteAtendido());
      logger.debug("converter paciente to View-Page: {}", pacientePageView);
      return pacientePageView;
   }
}
