package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.common.GenericException;
import net.amentum.niomedic.pacientes.configuration.ApiServCaller;
import net.amentum.niomedic.pacientes.converter.*;
import net.amentum.niomedic.pacientes.exception.ExceptionServiceCode;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.exception.PacientesGruposException;
import net.amentum.niomedic.pacientes.model.*;
import net.amentum.niomedic.pacientes.persistence.*;
import net.amentum.niomedic.pacientes.rest.ServiciosRest;
import net.amentum.niomedic.pacientes.service.PacienteService;
import net.amentum.niomedic.pacientes.views.*;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value; // Sre22052020 Nuevo
// Sre19062020 Inicia nuevos imports
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// Sre19062020 Termina

//import net.amentum.niomedic.pacientes.model.*;

@Service
@Transactional(readOnly = true)
public class PacienteServiceImpl implements PacienteService {
   private final Logger logger = LoggerFactory.getLogger(PacienteServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();
   private PacienteRepository pacienteRepository;
   private RelacionTitularRepository relacionTitularRepository;
   private DatosContactoRepository datosContactoRepository;
   private ServicioAdicionalesRepository servicioAdicionalesRepository;
   private PersonasViviendaRepository personasViviendaRepository;
   private DomicilioRepository domicilioRepository;
   private DatosAdicionalesRepository datosAdicionalesRepository;
   private PacientesGruposRepository pacientesGruposRepository;

   private PacienteConverter pacienteConverter;
   private RelacionTitularConverter relacionTitularConverter;
   private ServicioAdicionalesConverter servicioAdicionalesConverter;
   private PersonasViviendaConverter personasViviendaConverter;
   private DatosContactoConverter datosContactoConverter;
   private DomicilioConverter domicilioConverter;
   private ApiServCaller apiServCaller;


   {
      colOrderNames.put("nombre", "datosBusqueda");
      colOrderNames.put("fechaCreacion", "fechaCreacion");
      colOrderNames.put("idUsuario", "idUsuario");
      colOrderNames.put("userName", "userName");
      colOrderNames.put("idPaciente", "idPaciente");
      colOrderNames.put("curp", "curp");
      
   }

    // Sre19062020 Inicia inyecto entityManager
    protected EntityManager entityManager;
     
    public EntityManager getEntityManager() {
        return entityManager;
    }
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    // Sre19062020 Termina
    
   // Sre22052020 Agrego nueva variable de ambiente para Puebla con default
   @Value("${novalcurp:no}")
   private String novalcurp;

   @Autowired
   public void setPacienteRepository(PacienteRepository pacienteRepository) {
      this.pacienteRepository = pacienteRepository;
   }

   @Autowired
   public void setRelacionTitularRepository(RelacionTitularRepository relacionTitularRepository) {
      this.relacionTitularRepository = relacionTitularRepository;
   }

   @Autowired
   public void setDatosContactoRepository(DatosContactoRepository datosContactoRepository) {
      this.datosContactoRepository = datosContactoRepository;
   }

   @Autowired
   public void setServicioAdicionalesRepository(ServicioAdicionalesRepository servicioAdicionalesRepository) {
      this.servicioAdicionalesRepository = servicioAdicionalesRepository;
   }

   @Autowired
   public void setPersonasViviendaRepository(PersonasViviendaRepository personasViviendaRepository) {
      this.personasViviendaRepository = personasViviendaRepository;
   }

   @Autowired
   public void setDatosAdicionalesRepository(DatosAdicionalesRepository datosAdicionalesRepository) {
      this.datosAdicionalesRepository = datosAdicionalesRepository;
   }

   @Autowired
   public void setPacienteConverter(PacienteConverter pacienteConverter) {
      this.pacienteConverter = pacienteConverter;
   }

   @Autowired
   public void setRelacionTitularConverter(RelacionTitularConverter relacionTitularConverter) {
      this.relacionTitularConverter = relacionTitularConverter;
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
   public void setDatosContactoConverter(DatosContactoConverter datosContactoConverter) {
      this.datosContactoConverter = datosContactoConverter;
   }

   @Autowired
   public void setDomicilioRepository(DomicilioRepository domicilioRepository) {
      this.domicilioRepository = domicilioRepository;
   }

   @Autowired
   public void setDomicilioConverter(DomicilioConverter domicilioConverter) {
      this.domicilioConverter = domicilioConverter;
   }

   @Autowired
   public void setPacientesGruposRepository(PacientesGruposRepository pacientesGruposRepository){
      this.pacientesGruposRepository = pacientesGruposRepository;
   }

   @Autowired
   private ServiciosRest serviciosRest;

   @Autowired
   public void setApiServCaller(ApiServCaller apiServCaller) {
      this.apiServCaller = apiServCaller;
   }

   @Transactional(readOnly = false, rollbackFor = {PacienteException.class})
   @Override
   public PacienteView createPaciente(PacienteView pacienteView) throws PacienteException {
      try {
         if (pacienteView.getCurp() != null && !pacienteView.getCurp().trim().isEmpty()) {
            //se verifica por curp
             // Sre22052020 Inicia si novalcurp==si no los validamos
            if ("no".equalsIgnoreCase(novalcurp)) {
                logger.error("VALIDO CURP DE PACIENTE!!!");
                if (pacienteRepository.findByCurp(pacienteView.getCurp()) != null) {
                   logger.error("===>>>CURP de Paciente DUPLICADO: {}", pacienteView.getCurp());
                   PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
                   pe.addError("CURP de Paciente DUPLICADO: " + pacienteView.getCurp());
                   throw pe;
                }
            }
         } else {
            logger.info("===>>>El CURP viene nulo");
//            SI PUEDE SER VACIO
//            logger.error("===>>>CURP de Paciente NULO/VACIO: {}", pacienteView.getCurp());
//            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
//            pe.addError("CURP de Paciente NULO/VACIO: " + pacienteView.getCurp());
//            throw pe;
         }
         if (pacienteView.getRfc() != null && !pacienteView.getRfc().trim().isEmpty()) {
            //se verifica por rfc
            if (pacienteRepository.findByRfc(pacienteView.getRfc()) != null) {
               logger.error("===>>>RFC de Paciente DUPLICADO: {}", pacienteView.getRfc());
               PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
               pe.addError("RFC de Paciente DUPLICADO: " + pacienteView.getRfc());
               throw pe;
            }
         } else {
            logger.info("===>>>El RFC viene nulo");
//            SI PUEDE SER VACIO
//            logger.error("===>>>RFC de Paciente NULO/VACIO: {}", pacienteView.getRfc());
//            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
//            pe.addError("RFC de Paciente NULO/VACIO: " + pacienteView.getRfc());
//            throw pe;
         }

         if(pacienteView.getIdUsuario() == null){
            logger.error("===>>>idUsuario del Paciente NULO/VACIO: {}", pacienteView.getIdUsuario());
            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("idUsuario del Paciente NULO/VACIO:" + pacienteView.getIdUsuario());
            throw pe;
         }

         if (pacienteRepository.findByIdUsuario(pacienteView.getIdUsuario()) != null) {
            logger.error("===>>>idUsuario de Paciente DUPLICADO: {}", pacienteView.getIdUsuario());
            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("idUsuario de Paciente DUPLICADO: " + pacienteView.getIdUsuario());
            throw pe;
         }
         if(pacienteView.getFechaCreacion()==null) {
        	 pacienteView.setFechaCreacion(new Date());
         }

         Paciente paciente = pacienteConverter.toEntity(pacienteView, new Paciente(), Boolean.FALSE);
         logger.debug("===>>>Insertar nuevo Paciente: {}", paciente);
         pacienteRepository.save(paciente);
         logger.info("Servicios list - " + pacienteView.getCatServiciosList());
         logger.info("Paciente id - " + paciente.getIdPaciente());
         if(pacienteView.getCatServiciosList() == null || pacienteView.getCatServiciosList().isEmpty()) {
            serviciosRest.addServiciosByUsuario(paciente.getIdPaciente());
            logger.info("Se agregan servicios activos por default");
         } else {
            Collection<CatServicioView> views = pacienteView.getCatServiciosList();
            logger.info("Se agregan servicios recibidos - " + views);
            for(CatServicioView catView : views) {
               ServiciosView view = new ServiciosView();
               view.setIdServicio(catView.getIdCatServicio());
               view.setIdPaciente(paciente.getIdPaciente());
               serviciosRest.createService(view);
            }
         }
         return pacienteConverter.toView(paciente, Boolean.FALSE);
      } catch (PacienteException pe) {
         throw pe;
      } catch (DataIntegrityViolationException dte) {
         PacienteException pe = new PacienteException("No fue posible agregar  Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_INSERT);
         pe.addError("Ocurrio un error al agregar Paciente");
         logger.error("===>>>Error al insertar nuevo Paciente - CODE: {} - {}", pe.getExceptionCode(), pacienteView, dte);
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("Error inesperado al agregar  Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_INSERT);
         pe.addError("Ocurrio un error al agregar Paciente");
         logger.error("===>>>Error al insertar nuevo Paciente - CODE: {} - {}", pe.getExceptionCode(), pacienteView, ex);
         throw pe;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {PacienteException.class})
   @Override
   public PacienteView updatePaciente(PacienteView pacienteView) throws PacienteException {
      try {
         if (!pacienteRepository.exists(pacienteView.getIdPaciente())) {
            logger.error("===>>>idPaciente no encotrado: {}", pacienteView.getIdPaciente());
            PacienteException pe = new PacienteException("No se encuentra en  sistema  Paciente.", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("idPaciente no encontrado:" + pacienteView.getIdPaciente());
            throw pe;
         }
//         SI PUEDE SER VACIO
//         if (pacienteView.getCurp() == null || pacienteView.getCurp().trim().isEmpty()) {
//            logger.error("===>>>CURP de Paciente NULO/VACIO: {}", pacienteView.getCurp());
//            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
//            pe.addError("CURP de Paciente NULO/VACIO: " + pacienteView.getCurp());
//            throw pe;
//         }
//         SI PUEDE SER VACIO
//         if (pacienteView.getRfc() == null || pacienteView.getRfc().trim().isEmpty()) {
//            logger.error("===>>>RFC de Paciente NULO/VACIO: {}", pacienteView.getRfc());
//            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
//            pe.addError("RFC de Paciente NULO/VACIO: " + pacienteView.getRfc());
//            throw pe;
//         }

         if (pacienteView.getNumeroExpediente() == null) {
//            if(pacienteView.getNumeroExpediente() instanceof Long){
            logger.error("===>>>NumeroExpediente NULO/VACIO: {}", pacienteView.getNumeroExpediente());
            PacienteException pe = new PacienteException("Existe un error", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("NumeroExpediente NULO/VACIO: " + pacienteView.getNumeroExpediente());
            throw pe;
         }


         Paciente paciente = pacienteRepository.findOne(pacienteView.getIdPaciente());

//         SE CORRIGE QUE PONGAN OTRO NUMERO DE EXPEDIENTE DIFERENTE AL GENERADO POR EL SISTEMA
         pacienteView.setNumeroExpediente(paciente.getNumeroExpediente());

//            los elimino de la DB
         Collection<String> noExistenDatosContactoView = datosContactoConverter.obtenerIDNoExistentesDatosContacto(paciente, pacienteView);
         DatosContacto dc;
         for (String IDdc : noExistenDatosContactoView) {
            dc = datosContactoRepository.findOne(IDdc);
            dc.setPaciente(null);
            datosContactoRepository.delete(IDdc);
         }

//         los elimino de la DB
         Collection<String> noExistenServicioAdicionalesView = servicioAdicionalesConverter.obtenerIDNoExistentesServicioAdicionales(paciente, pacienteView);
         ServicioAdicionales sa;
         for (String IDsa : noExistenServicioAdicionalesView) {
            sa = servicioAdicionalesRepository.findOne(IDsa);
            sa.setPaciente(null);
            servicioAdicionalesRepository.delete(IDsa);
         }

//         los elimino de la DB
         Collection<String> noExistenPersonasViviendaView = personasViviendaConverter.obtenerIDNoExistentesPersonasVivienda(paciente, pacienteView);
         PersonasVivienda pv;
         for (String IDpv : noExistenPersonasViviendaView) {
            pv = personasViviendaRepository.findOne(IDpv);
            pv.setPaciente(null);
            personasViviendaRepository.delete(IDpv);
         }

//         los elimino de la DB
         Collection<String> noExistenDomicilioView = domicilioConverter.obtenerIDNoExistentesDomicilio(paciente, pacienteView);
         Domicilio dom;
         for (String IDdom : noExistenDomicilioView) {
            dom = domicilioRepository.findOne(IDdom);
            dom.setPaciente(null);
            domicilioRepository.delete(IDdom);
         }

//         quito el existente para poner el nuevo
         if (paciente.getDatosAdicionales() != null) {
            String DAtemporal = paciente.getDatosAdicionales().getIdDatosAdicionales();
            DatosAdicionales da = datosAdicionalesRepository.findOne(DAtemporal);
            da.setPaciente(null);
            datosAdicionalesRepository.delete(DAtemporal);
         }


//            limpiar las listas
         paciente.setDatosContactoList(new ArrayList<>());
         paciente.setServicioAdicionalesList(new ArrayList<>());
         paciente.setPersonasViviendaList(new ArrayList<>());
         paciente.setDomicilioList(new ArrayList<>());

         logger.info("Servicios list - " + pacienteView.getCatServiciosList());
         logger.info("Paciente id - " + paciente.getIdPaciente());
         Collection<CatServicioView> views = pacienteView.getCatServiciosList();
         logger.info("Se agregan servicios recibidos - " + views);
         if(views == null || views.isEmpty())
            logger.error("Lista de servicios vacía, no se eliminan servicios anteriores");
         else {
            serviciosRest.deleteServiceById(paciente.getIdPaciente());
            for(CatServicioView catView : views) {
               ServiciosView view = new ServiciosView();
               view.setIdServicio(catView.getIdCatServicio());
               view.setIdPaciente(paciente.getIdPaciente());
               serviciosRest.createService(view);
            }
         }
         paciente = pacienteConverter.toEntity(pacienteView, paciente, Boolean.TRUE);
         logger.debug("===>>>Editar Paciente: {}", paciente);
         pacienteRepository.save(paciente);
         return pacienteConverter.toView(paciente, Boolean.TRUE);
      } catch (PacienteException pe) {
         throw pe;
      } catch (DataIntegrityViolationException dive) {
         PacienteException pe = new PacienteException("No fue posible editar  Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_UPDATE);
         pe.addError("Ocurrio un error al editar Paciente");
         logger.error("===>>>Error al editar Paciente - CODE: {} - {}", pe.getExceptionCode(), pacienteView, dive);
         throw pe;
      } catch (InvalidDataAccessApiUsageException idaaue) {
         logger.error("===>>>Existen Id's repetidos");
         PacienteException pe = new PacienteException("No fue posible editar Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_INSERT);
         pe.addError("Existen Id's repetidos");
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("Ocurrio un error al editar Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_UPDATE);
         pe.addError("Ocurrio un error al editar Paciente");
         logger.error("===>>>Error al editar Paciente - CODE: {} - {}", pe.getExceptionCode(), pacienteView, ex);
         throw pe;
      }
   }

   @Override
   public PacienteView getDetailsPacienteById(@NotNull String pacienteId) throws PacienteException {
      try {
         if (!pacienteRepository.exists(pacienteId)) {
            logger.error("===>>>idPaciente no encotrado: {}", pacienteId);
            PacienteException pe = new PacienteException("No se encuentra en  sistema  Paciente.", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("idPaciente no encontrado:" + pacienteId);
            throw pe;
         }
         Paciente paciente = pacienteRepository.findOne(pacienteId);
         PacienteView view = pacienteConverter.toView(paciente, Boolean.TRUE);
         CatServicio servicio = serviciosRest.getServiciosByUsuario(view.getIdPaciente());

         logger.error("===>>>Paciente paciente: {}", paciente);
         logger.error("===>>>Paciente view: {}", view);
         logger.error("===>>>Paciente servicios: {}", servicio);


         if(servicio != null)
            view.setCatServiciosList(servicio.getServicios());
         return view;
      } catch (PacienteException pe) {
         throw pe;
      } catch (DataIntegrityViolationException dte) {
         PacienteException pe = new PacienteException("No fue posible obtener detalles  Paciente" + pacienteId, PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         pe.addError("Ocurrio un error al obtener detalles  Paciente");
         logger.error(ExceptionServiceCode.PACIENTE + "Error al obtener detalles  Paciente - CODE: {} - {}", pe.getExceptionCode(), pacienteId, dte);
         throw pe;
      } catch (ConstraintViolationException dte) {
         PacienteException pe = new PacienteException("Error al obtener detalles  Paciente: " + pacienteId, PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         pe.addError("Ocurrio un error al obtener detalles  Paciente.");
         logger.error("===>>>Error al obtener detalles  Paciente. - CODE: {} - {}", pe.getExceptionCode(), pacienteId, dte);
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible obtener detalles  Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al obtener detalles  Paciente - CODE: {} - {}", pe.getExceptionCode(), pacienteId, ex);
         throw pe;
      }
   }

   @Override
   public PacienteView getDetailsPacienteByUsuario(@NotNull Integer idUsuario) throws PacienteException {
      try {
         Paciente paciente = pacienteRepository.findByIdUsuario(idUsuario);
         if (paciente == null) {
            logger.error("===>>>idUsuario no encotrado: {}", idUsuario);
            PacienteException pe = new PacienteException("No se encuentra en  sistema  Paciente.", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("idUsuario no encontrado:" + idUsuario);
            throw pe;
         }
         PacienteView view = pacienteConverter.toView(paciente, Boolean.TRUE);
         CatServicio servicio = serviciosRest.getServiciosByUsuario(view.getIdPaciente());
         if(servicio != null)
            view.setCatServiciosList(servicio.getServicios());
         return view;
      } catch (PacienteException pe) {
         throw pe;
      } catch (DataIntegrityViolationException dte) {
         PacienteException pe = new PacienteException("No fue posible obtener detalles  Paciente" + idUsuario, PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         pe.addError("Ocurrio un error al obtener detalles  Paciente");
         logger.error(ExceptionServiceCode.PACIENTE + "===>>>Error al obtener detalles  Paciente - CODE: {} - {}", pe.getExceptionCode(), idUsuario, dte);
         throw pe;
      } catch (ConstraintViolationException dte) {
         PacienteException pe = new PacienteException("Error al obtener detalles  Paciente: " + idUsuario, PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         pe.addError("Ocurrio un error al obtener detalles  Paciente.");
         logger.error("===>>>Error al obtener detalles  Paciente. - CODE: {} - {}", pe.getExceptionCode(), idUsuario, dte);
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible obtener detalles  Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al obtener detalles  Paciente - CODE: {} - {}", pe.getExceptionCode(), idUsuario, ex);
         throw pe;
      }
   }


   @Override
   public PacienteView getDetailsPacienteByCurp(String curp) throws PacienteException {
      try {
         Paciente paciente = pacienteRepository.findByCurp(curp);
         if (paciente == null) {
            logger.error("===>>>CURP no encotrado: {}", curp);
            PacienteException pe = new PacienteException("No se encuentra en  sistema  Paciente.", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
            pe.addError("CURP no encontrado:" + curp);
            throw pe;
         }
         PacienteView view = pacienteConverter.toView(paciente, Boolean.TRUE);
         CatServicio servicio = serviciosRest.getServiciosByUsuario(view.getIdPaciente());
         if(servicio != null)
            view.setCatServiciosList(servicio.getServicios());
         return view;
      } catch (PacienteException pe) {
         throw pe;
      } catch (DataIntegrityViolationException dte) {
         PacienteException pe = new PacienteException("No fue posible obtener detalles  Paciente" + curp, PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         pe.addError("Ocurrio un error al obtener detalles  Paciente");
         logger.error(ExceptionServiceCode.PACIENTE + "===>>>Error al obtener detalles  Paciente - CODE: {} - {}", pe.getExceptionCode(), curp, dte);
         throw pe;
      } catch (ConstraintViolationException dte) {
         PacienteException pe = new PacienteException("Error al obtener detalles  Paciente: " + curp, PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         pe.addError("Ocurrio un error al obtener detalles  Paciente.");
         logger.error("===>>>Error al obtener detalles  Paciente. - CODE: {} - {}", pe.getExceptionCode(), curp, dte);
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible obtener detalles  Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al obtener detalles  Paciente - CODE: {} - {}", pe.getExceptionCode(), curp, ex);
         throw pe;
      }
   }

   @Override
//   public Page<PacientePageView> getPacientePage(Boolean active, String name, Integer page, Integer size, String orderColumn, String orderType) throws PacienteException {
   // GGR20200618 Agrego grupo seleccionado
   public Page<PacientePageView> getPacientePage(String datosBusqueda, Boolean active, Integer page, Integer size, String orderColumn, String orderType, Long selectGroup) throws PacienteException {
      try {
         logger.info("===>>>getPacientePage(): - datosBusqueda {} - active {} - page {} - size: {} - orderColumn: {} - orderType: {} - selectGroup: {}",
            datosBusqueda, active, page, size, orderColumn, orderType, selectGroup);
         List<PacientePageView> pacienteViewList = new ArrayList<>();
         Page<Paciente> pacientePage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("nombre"));
         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         List<Long> losPacientesLong = new ArrayList<>();
         Boolean hayGrupo = false;
         if (selectGroup != null) {
            List<Integer> losPacientes = pacienteRepository.findIdByGroup(selectGroup);
            losPacientes.forEach(unId -> losPacientesLong.add(unId.longValue()));
            hayGrupo = true;
         }
         if (losPacientesLong.isEmpty())
            losPacientesLong.add(0L);
         logger.info("Estos son los pacientes {} que están en el grupo {}", losPacientesLong, selectGroup);
         if (hayGrupo)
            pacientePage = pacienteRepository.findAllByGroup(losPacientesLong, patternSearch, request);
         else
            pacientePage = pacienteRepository.findAllByGroup(patternSearch, request);
         pacientePage.getContent().forEach(paciente -> {
            PacientePageView view = pacienteConverter.toViewPage(paciente);
            try {
               logger.info("Viendo si es usuario canalizado {}", view.getIdPaciente());
               view.setEsCanalizado(apiServCaller.checkPacientecanalizado(view.getIdUsuario()));
               logger.info("Viendo de que grupo es el usuario {}", view.getIdPaciente());
               view.setIdGroup(pacientesGruposRepository.findFirstByIdPaciente(view.getIdPaciente()).getIdGroup());
               view.setGroupName(apiServCaller.getGroupName(view.getIdGroup()));
            } catch (PacienteException e) {
               logger.error(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
             pacienteViewList.add(view);
         });
         return new PageImpl<>(pacienteViewList, request, pacientePage.getTotalElements());
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algún parámetro no es correcto");
         PacienteException pe = new PacienteException("Algún parámetro no es correcto:", PacienteException.LAYER_SERVICE, PacienteException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacío o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         PacienteException pacienteException = new PacienteException("Ocurrio un error al seleccionar lista Paciente paginable", PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista Paciente paginable - CODE: {}", pacienteException.getExceptionCode(), ex);
         throw pacienteException;
      }
   }
   @Override
   public Page<PacientePageView> getPacientePageAtendidos(String datosBusqueda, Boolean active, Integer page, Integer size, String orderColumn, String orderType, Long idUser) throws PacienteException {
         try {
            logger.info("===>>>getPacientePageAtendidos(): - datosBusqueda {} - active {} - page {} - size: {} - orderColumn: {} - orderType: {} - idUser: {}",
                    datosBusqueda, active, page, size, orderColumn, orderType, idUser);
            List<PacientePageView> pacienteViewList = new ArrayList<>();
            Page<Paciente> pacientePage = null;
            Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("nombre"));

            Boolean acts = false;

            if (orderColumn != null && orderType != null) {
               if (orderType.equalsIgnoreCase("asc")) {
                  sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
               } else {
                  sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
               }

            }
            PageRequest request = new PageRequest(page, size, sort);
            final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
            Specifications<Paciente> spec = Specifications.where(
                    (root, query, cb) -> {
                       Predicate tc = null;

                       tc =(tc != null ? cb.and(tc, cb.equal(root.get("pacienteAtendido"), acts)) : cb.equal(root.get("pacienteAtendido"), acts));
                       if (datosBusqueda != null && !datosBusqueda.isEmpty()) {
                          tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch)));
                       }
                       if (active != null) {
                          tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), active)) : cb.equal(root.get("activo"), active));
                       }
                       return tc;
                    }
            );
            List<Long> losPacientesAtendidos = new ArrayList<>();
            /*
            try {
               losPacientesCanalizados = apiServCaller.getListaCanalizados(idUser);
               logger.info ("===>>>getPacientePageCanalizados(): Los usuarios canalizados para el idUser {} son: {}", idUser, losPacientesCanalizados);
            } catch (Exception e) {
               logger.info("===>>>Algo falló al pedir la lista de usuarios canalizados");
            }
            if (losPacientesCanalizados.isEmpty()) {
               losPacientesCanalizados.add(new Long(0));
            }

           // if (spec == null) {
           //    pacientePage = pacienteRepository.findAllByGroup(losPacientesCanalizados, patternSearch, request);
            } else {
               pacientePage = pacienteRepository.findAllByGroup(losPacientesCanalizados, patternSearch, request);
            } */

            pacientePage = pacienteRepository.findAll(spec, request);
           // pacientePage = pacienteRepository.findAllByGroup(spec, patternSearch, request);
            pacientePage.getContent().forEach(paciente -> {
               PacientePageView view = pacienteConverter.toViewPage(paciente);
                try {
                   logger.info("Viendo de que grupo es el usuario {}", view.getIdPaciente());
                   view.setIdGroup(pacientesGruposRepository.findFirstByIdPaciente(view.getIdPaciente()).getIdGroup());
                   view.setGroupName(apiServCaller.getGroupName(view.getIdGroup()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                pacienteViewList.add(view);
            });
            PageImpl<PacientePageView> pacienteViewPage = new PageImpl<PacientePageView>(pacienteViewList, request, pacientePage.getTotalElements());
            return pacienteViewPage;
         } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            PacienteException pe = new PacienteException("Algun parametro no es correcto:", PacienteException.LAYER_SERVICE, PacienteException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
         } catch (Exception ex) {
            PacienteException pacienteException = new PacienteException("Ocurrio un error al seleccionar lista Pacientes canalizados paginable", PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista Pacientes canalizados paginable - CODE: {}", pacienteException.getExceptionCode(), ex);
            throw pacienteException;
         }
      }




   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }

   @Transactional(readOnly = false, rollbackFor = {PacienteException.class})
   @Override
   public Page<PacientePageView> getPadecimientoSearch(Boolean active, String datosBusqueda, String nombre, String apellidoPaterno,
                                                       String apellidoMaterno, String telefonoMovil, String telefonoFijo, String correo,
                                                       String rfc, String curp, Integer page, Integer size, String orderColumn, String orderType) throws PacienteException {
      try {
         logger.info("===>>>getPacienteSearch(): " +
               "- active {} - datosBusqueda: {} - nombre: {} - apellidoPaterno: {} - apellidoMaterno: {} - telefonoMovil: {} - telefonoFijo: {} - correo: {} - rfc: {} - curp: {} - " +
               "page: {} - size: {} - orderColumn: {} - orderType: {}",
            active, datosBusqueda, nombre, apellidoPaterno, apellidoMaterno, telefonoMovil, telefonoFijo, correo, rfc, curp, page, size, orderColumn, orderType);
         List<PacientePageView> pacienteViewList = new ArrayList<>();
         Page<Paciente> pacientePage = null;
         //Page<Object[]> pacientePage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("nombre"));
         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         final String patternName = "%" + nombre.toLowerCase() + "%";
         final String patternAppellidoPa = "%" + apellidoPaterno.toLowerCase() + "%";
         final String patternAppellidoMa = "%" + apellidoMaterno.toLowerCase() + "%";
         Specifications<Paciente> spec = Specifications.where(
            (root, query, cb) -> {
               Predicate tc = null;
               if (datosBusqueda != null && !datosBusqueda.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch)));
               }
               if (active != null) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), active)) : cb.equal(root.get("activo"), active));
               }
               if (nombre != null && !nombre.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("nombre"))), sinAcentos(patternName))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("nombre"))), sinAcentos(patternName)));
               }

               if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("apellidoPaterno"))), sinAcentos(patternAppellidoPa))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("apellidoPaterno"))), sinAcentos(patternAppellidoPa)));
               }
               if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("apellidoMaterno"))), sinAcentos(patternAppellidoMa))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("apellidoMaterno"))), sinAcentos(patternAppellidoMa)));
               }

               if (telefonoMovil != null && !telefonoMovil.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("telefonoCelular"), telefonoMovil)) : cb.equal(root.get("telefonoCelular"), telefonoMovil));
               }
               if (telefonoFijo != null && !telefonoFijo.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("telefonoLocal"), telefonoFijo)) : cb.equal(root.get("telefonoLocal"), telefonoFijo));
               }
               if (correo != null && !correo.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("email"), correo)) : cb.equal(root.get("email"), correo));
               }
               if (rfc != null && !rfc.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("rfc"), rfc)) : cb.equal(root.get("rfc"), rfc));
               }
               if (curp != null && !curp.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("curp"), curp)) : cb.equal(root.get("curp"), curp));
               }

               query.multiselect(root.get("nombre"), root.get("fechaCreacion"), root.get("idUsuario"),
                  root.get("userName"), root.get("idPaciente"), root.get("curp")).where(tc);
               return tc;
            }
         );


         if (spec == null) {
            pacientePage = pacienteRepository.findAll(request);
         } else {
            pacientePage = pacienteRepository.findAll(spec, request);
         }

         pacientePage.getContent().forEach(paciente -> {
            pacienteViewList.add(pacienteConverter.toViewPage(paciente));
         });
         PageImpl<PacientePageView> pacienteViewPage = new PageImpl<PacientePageView>(pacienteViewList, request, pacientePage.getTotalElements());
         return pacienteViewPage;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         PacienteException pe = new PacienteException("Algun parametro no es correcto", PacienteException.LAYER_SERVICE, PacienteException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         PacienteException pacienteException = new PacienteException("Ocurrio un error al seleccionar lista Paciente paginable", PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista Paciente paginable - CODE: {}", pacienteException.getExceptionCode(), ex);
         throw pacienteException;
      }
   }

    // Sre19062020 Nuevo servicio para actualizar grupos de paciente
    @Transactional(readOnly = false, rollbackFor = {PacienteException.class})
    @Override
    public void updatePacienteGroups(Long idUsuario, List<Long> pacienteGroups) throws PacienteException {
      try {
            Paciente paciente = pacienteRepository.findByIdUsuario(idUsuario);
            if (paciente == null) {
               logger.error("===>>>idUsuario no encontrado: {}", idUsuario);
               PacienteException pe = new PacienteException("No se encuentra en sistema Paciente.", PacienteException.LAYER_DAO, PacienteException.ACTION_VALIDATE);
               pe.addError("idUsuario no encontrado:" + idUsuario);
               throw pe;
            }
            // Ahora obtengo una conn y opero
            Session session = (Session) entityManager.getDelegate();
            session.doWork(new Work() {
                @Override
                public void execute(Connection connectionToUse) throws SQLException {
                    // Aca opero borrar los que haya para medico
                    // Borro los que haya delete from public.medicos_grupos where id_medico=?
                    // Ahora inserto los que vengan en medicoGroups
                    boolean commitMode = connectionToUse.getAutoCommit();
                    try {
                        connectionToUse.setAutoCommit(false);
                        String query = "DELETE FROM public.pacientes_grupos WHERE id_paciente=?";
                        String queryIns = "INSERT INTO public.pacientes_grupos (id_paciente, id_group) values (?, ?)";
                        PreparedStatement pstmt = connectionToUse.prepareStatement(query);
                        pstmt.setString(1, paciente.getIdPaciente());
                        int resDel = pstmt.executeUpdate();
                        int resIns = 0;
                        pstmt = connectionToUse.prepareStatement(queryIns);
                        for (Long unGrupo : pacienteGroups) {
                            pstmt.setString(1, paciente.getIdPaciente());
                            pstmt.setLong(2, unGrupo);
                            resIns += pstmt.executeUpdate();
                        }
                        connectionToUse.commit();
                        logger.info("Se actualizaron {}/{} grupos del paciente", resDel, resIns);
                    } finally {
                        // Restore commit mode
                        connectionToUse.setAutoCommit(commitMode);
                    }
                }
            });
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible actualizar los grupos del Paciente", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al actualizar los grupos del Paciente - CODE: {} - {}", pe.getExceptionCode(), idUsuario, ex);
         throw pe;
      }
    }

    // GGR20200626 Obtener la segunda lista de pacientes canalizados
    @Override
   public Page<PacientePageView> getPacientePageCanalizados(String datosBusqueda, Boolean active, Integer page, Integer size, String orderColumn, String orderType, Long idUser) throws PacienteException {
      try {
         logger.info("===>>>getPacientePageCanalizados(): - datosBusqueda {} - active {} - page {} - size: {} - orderColumn: {} - orderType: {} - idUser: {}",
                 datosBusqueda, active, page, size, orderColumn, orderType, idUser);
         List<PacientePageView> pacienteViewList = new ArrayList<>();
         Page<Paciente> pacientePage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("nombre"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }

         }
         PageRequest request = new PageRequest(page, size, sort);
//         final String patternSearch = "%" + name.toLowerCase() + "%";
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
//         System.out.println("----->sin acentos----->" + sinAcentos(patternSearch));
         Specifications<Paciente> spec = Specifications.where(
                 (root, query, cb) -> {
                    Predicate tc = null;
//               if (name != null && !name.isEmpty()) {
                    if (datosBusqueda != null && !datosBusqueda.isEmpty()) {
                       tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch)));
                    }
                    if (active != null) {
                       tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), active)) : cb.equal(root.get("activo"), active));
                    }
                    return tc;
                 }
         );
         List<Long> losPacientesCanalizados = new ArrayList<>();
         try {
            losPacientesCanalizados = apiServCaller.getListaCanalizados(idUser);
            logger.info ("===>>>getPacientePageCanalizados(): Los usuarios canalizados para el idUser {} son: {}", idUser, losPacientesCanalizados);
         } catch (Exception e) {
            logger.info("===>>>Algo falló al pedir la lista de usuarios canalizados");
         }
         if (losPacientesCanalizados.isEmpty()) {
            losPacientesCanalizados.add(new Long(0));
         }

         if (spec == null) {
            pacientePage = pacienteRepository.findAllByGroup(losPacientesCanalizados, patternSearch, request);
         } else {
            pacientePage = pacienteRepository.findAllByGroup(losPacientesCanalizados, patternSearch, request);
         }
         pacientePage.getContent().forEach(paciente -> {
            PacientePageView view = pacienteConverter.toViewPage(paciente);
            try {
               logger.info("Viendo de que grupo es el usuario {}", view.getIdPaciente());
               view.setIdGroup(pacientesGruposRepository.findFirstByIdPaciente(view.getIdPaciente()).getIdGroup());
               view.setGroupName(apiServCaller.getGroupName(view.getIdGroup()));
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
            pacienteViewList.add(view);
         });
         PageImpl<PacientePageView> pacienteViewPage = new PageImpl<PacientePageView>(pacienteViewList, request, pacientePage.getTotalElements());
         return pacienteViewPage;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         PacienteException pe = new PacienteException("Algun parametro no es correcto:", PacienteException.LAYER_SERVICE, PacienteException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         PacienteException pacienteException = new PacienteException("Ocurrio un error al seleccionar lista Pacientes canalizados paginable", PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista Pacientes canalizados paginable - CODE: {}", pacienteException.getExceptionCode(), ex);
         throw pacienteException;
      }
   }




   @Transactional(readOnly = false, rollbackFor = {PacienteException.class})
   @Override
   public void deleteRollback(Integer idUserApp) throws PacienteException {
      try {
         Paciente paciente = pacienteRepository.findByIdUsuario(idUserApp);
         if(paciente == null) {
            logger.error("Error al hacer ROLLBACK, usuario " + idUserApp + " no encontrado");
            PacienteException exception = new PacienteException("Ocurrió un error al hacer ROLLBACK de idUserApp: " + idUserApp + " - no encontrado", PacienteException.LAYER_DAO, GenericException.ACTION_DELETE);
            exception.addError("Usuario no encontrado");
            throw exception;
         }
         logger.info("Borrando servicios registrados");
         serviciosRest.deleteServiceById(paciente.getIdPaciente());

         logger.info("Borrando usuario: {}", idUserApp);
         pacienteRepository.delete(paciente);

         logger.info("ROLLBACK terminado");
      } catch (Exception ex) {
         logger.error("Error al hacer ROLLBACK: {}", ex.getMessage());
         PacienteException exception = new PacienteException("Ocurrió un error al hacer ROLLBACK de idUserApp: " + idUserApp, PacienteException.LAYER_DAO, GenericException.ACTION_DELETE);
         exception.addError(ex.getLocalizedMessage());
         throw exception;
      }
   }

   @Transactional(rollbackFor = {PacienteException.class})
   @Override
   public void updateIdDevice(Integer idUsuario, String idDevice) throws PacienteException {
      try {
         Paciente paciente = pacienteRepository.findByIdUsuario(idUsuario.longValue());
         if(paciente == null) {
            logger.info("Paciente no encontrado");
            PacienteException exception = new PacienteException("Ocurrió un error al buscar el paciente", PacienteException.LAYER_DAO, GenericException.ACTION_SELECT);
            exception.addError("Paciente no encontrado");
            throw exception;
         }
         if(idDevice.equalsIgnoreCase("null"))
            paciente.setIdDevice(null);
         else
            paciente.setIdDevice(idDevice);
         pacienteRepository.save(paciente);
      } catch (PacienteException ex) {
         logger.error("Error: ", ex);
         throw ex;
      } catch (Exception ex) {
         logger.error("Error: {}", ex.getLocalizedMessage());
         PacienteException exception = new PacienteException("Ocurrió un error al hacer Update de idUserApp: " + idUsuario, PacienteException.LAYER_DAO, GenericException.ACTION_UPDATE);
         exception.addError(ex.getLocalizedMessage());
         throw exception;
      }
   }
   @Override
   public PacienteTitularView getTitularPorTelefono(String telefono) {
      Paciente paciente = pacienteRepository.findByEsTitularTrueAndTelefonoCelular(telefono);

      if (paciente == null) {
         return null;
      }

      return pacienteConverter.toPacienteTitularView(paciente);
   }

    @Override
    public List<PacienteDeviceMinView> getPacientesByGroupWithDevice(Long selectGroup,
                                                                     String orderColumn,
                                                                     String orderType) throws PacienteException {
        try {
            logger.info("===>>>getPacientesByGroupWithDevice(): selectGroup={} orderColumn={} orderType={}",
                    selectGroup, orderColumn, orderType);

            if (selectGroup == null) {
                PacienteException pe = new PacienteException("selectGroup es requerido",
                        PacienteException.LAYER_SERVICE, PacienteException.ACTION_VALIDATE);
                pe.addError("El parámetro selectGroup no puede ser null");
                throw pe;
            }

            // 1) Resolver usuarios del grupo (JOIN pacientes_grupos)
            List<Integer> idsInt = pacienteRepository.findIdByGroup(selectGroup);
            List<Long> usuarios = new ArrayList<>();
            if (idsInt != null) for (Integer i : idsInt) usuarios.add(i.longValue());
            if (usuarios.isEmpty()) return new ArrayList<>();

            // 2) Traer TODOS los pacientes (sin paginado), activos y con idDevice válido
            List<Paciente> lista = pacienteRepository.findAllByUsuariosWithDeviceActivos(usuarios);

            // 3) Mapear a la vista mínima (nombre completo, idPaciente, idDevice)
            List<PacienteDeviceMinView> out = new ArrayList<>();
            for (Paciente p : lista) {
                out.add(pacienteConverter.toDeviceMinView(p));
            }

            // 4) (Opcional) Ordenar en memoria; default: nombre ASC
            String by = (orderColumn == null || orderColumn.trim().isEmpty()) ? "nombre" : orderColumn.trim();
            boolean desc = "desc".equalsIgnoreCase(orderType);

            java.util.Comparator<PacienteDeviceMinView> cmp;
            switch (by) {
                case "idPaciente":
                    cmp = java.util.Comparator.comparing(PacienteDeviceMinView::getIdPaciente,
                            java.util.Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
                case "idDevice":
                    cmp = java.util.Comparator.comparing(PacienteDeviceMinView::getIdDevice,
                            java.util.Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
                case "nombre":
                default:
                    cmp = java.util.Comparator.comparing(PacienteDeviceMinView::getNombre,
                            java.util.Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
            }
            if (desc) cmp = cmp.reversed();
            out.sort(cmp);

            return out;

        } catch (PacienteException pe) {
            throw pe;
        } catch (Exception ex) {
            PacienteException pe = new PacienteException(
                    "Ocurrió un error al obtener lista de Pacientes por grupo con device",
                    PacienteException.LAYER_SERVICE, PacienteException.ACTION_SELECT);
            logger.error("===>>>Error en getPacientesByGroupWithDevice - CODE: {}", pe.getExceptionCode(), ex);
            throw pe;
        }
    }
}









