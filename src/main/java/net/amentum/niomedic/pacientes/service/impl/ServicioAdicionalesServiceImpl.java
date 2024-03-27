package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.ServicioAdicionalesConverter;
import net.amentum.niomedic.pacientes.exception.ServicioAdicionalesException;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.ServicioAdicionales;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.persistence.ServicioAdicionalesRepository;
import net.amentum.niomedic.pacientes.service.ServicioAdicionalesService;
import net.amentum.niomedic.pacientes.views.PacienteView;
import net.amentum.niomedic.pacientes.views.ServicioAdicionalesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ServicioAdicionalesServiceImpl implements ServicioAdicionalesService {
   private final Logger logger = LoggerFactory.getLogger(ServicioAdicionalesServiceImpl.class);
   private ServicioAdicionalesRepository servicioAdicionalesRepository;
   private ServicioAdicionalesConverter servicioAdicionalesConverter;
   private PacienteRepository pacienteRepository;

   @Autowired
   public void setServicioAdicionalesRepository(ServicioAdicionalesRepository servicioAdicionalesRepository) {
      this.servicioAdicionalesRepository = servicioAdicionalesRepository;
   }

   @Autowired
   public void setServicioAdicionalesConverter(ServicioAdicionalesConverter servicioAdicionalesConverter) {
      this.servicioAdicionalesConverter = servicioAdicionalesConverter;
   }

   @Autowired
   public void setPacienteRepository(PacienteRepository pacienteRepository) {
      this.pacienteRepository = pacienteRepository;
   }


   @Transactional(readOnly = false, rollbackFor = {ServicioAdicionalesException.class})
   @Override
   public void createServicioAdicionales(ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList, String idPaciente) throws ServicioAdicionalesException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            ServicioAdicionalesException sae = new ServicioAdicionalesException("No se encuentra en el sistema al paciente.", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_VALIDATE);
            sae.addError("idPaciente no encontrado:" + idPaciente);
            throw sae;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         ArrayList<ServicioAdicionales> servicioAdicionalesArrayList = new ArrayList<>();
         servicioAdicionalesConverter.toEntity(servicioAdicionalesViewArrayList, servicioAdicionalesArrayList, paciente, Boolean.FALSE);
         logger.debug("===>>>Insertar nuevos servicio adicionales: {}", servicioAdicionalesArrayList);
         servicioAdicionalesRepository.save(servicioAdicionalesArrayList);
      } catch (ServicioAdicionalesException sae) {
         throw sae;
      } catch (DataIntegrityViolationException dte) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("No fue posible agregar servicio adicionales", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_INSERT);
         sae.addError("Ocurrio un error al agregar servicio adicionales");
         logger.error("===>>>Error al insertar nuevo servicio adicionales - CODE: {} - {}", sae.getExceptionCode(), servicioAdicionalesViewArrayList, dte);
         throw sae;
      } catch (Exception ex) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("Error inesperado al agregar servicio adicionales", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_INSERT);
         sae.addError("Ocurrio un error al agregar servicio adicionales");
         logger.error("===>>>Error al insertar nuevo servicio adicionales - CODE: {} - {}", sae.getExceptionCode(), servicioAdicionalesViewArrayList, ex);
         throw sae;
      }
   }


   @Transactional(readOnly = false, rollbackFor = {ServicioAdicionalesException.class})
   @Override
   public void updateServicioAdicionales(ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList, String idPaciente) throws ServicioAdicionalesException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            ServicioAdicionalesException sae = new ServicioAdicionalesException("No se encuentra en el sistema al paciente.", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_VALIDATE);
            sae.addError("idPaciente no encontrado:" + idPaciente);
            throw sae;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setServicioAdicionalesViewList(servicioAdicionalesViewArrayList);

//         los elimino de la DB
         Collection<String> noExistenServicioAdicionalesView = servicioAdicionalesConverter.obtenerIDNoExistentesServicioAdicionales(paciente, pacienteView);
         ServicioAdicionales sa;
         for (String IDsa : noExistenServicioAdicionalesView) {
            sa = servicioAdicionalesRepository.findOne(IDsa);
            sa.setPaciente(null);
            servicioAdicionalesRepository.delete(IDsa);
         }
//         limpiar la lista
         paciente.setServicioAdicionalesList(new ArrayList<>());
//         la "nueva lista"
         Collection<ServicioAdicionales> servicioAdicionales = new ArrayList<>();

         servicioAdicionalesConverter.toEntity(servicioAdicionalesViewArrayList, servicioAdicionales, paciente, Boolean.TRUE);
         logger.debug("===>>>Modificar  Datos Contacto: {}", servicioAdicionales);
         servicioAdicionalesRepository.save(servicioAdicionales);
      } catch (ServicioAdicionalesException sae) {
         throw sae;
      } catch (DataIntegrityViolationException dte) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("No fue posible modificar servicio adicionales", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_UPDATE);
         sae.addError("Ocurrio un error al modificar servicio adicionales");
         logger.error("===>>>Error al modificar  servicio adicionales - CODE: {} - {}", sae.getExceptionCode(), servicioAdicionalesViewArrayList, dte);
         throw sae;
      } catch (Exception ex) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("Error inesperado al modificar servicio adicionales", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_UPDATE);
         sae.addError("Ocurrio un error al modificar servicio adicionales");
         logger.error("===>>>Error al modificar nuevo servicio adicionales - CODE: {} - {}", sae.getExceptionCode(), servicioAdicionalesViewArrayList, ex);
         throw sae;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {ServicioAdicionalesException.class})
   @Override
   public void deleteServicioAdicionales(ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList, String idPaciente) throws ServicioAdicionalesException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            ServicioAdicionalesException sae = new ServicioAdicionalesException("No se encuentra en el sistema al paciente.", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_VALIDATE);
            sae.addError("idPaciente no encontrado:" + idPaciente);
            throw sae;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setServicioAdicionalesViewList(servicioAdicionalesViewArrayList);

//         obtener los id a borrar
         Collection<String> idsView = new ArrayList<>();
         idsView.addAll(
            pacienteView.getServicioAdicionalesViewList().stream()
               .map(saV -> {
                  String idV = saV.getIdServicioAdicionales();
                  return idV;
               }).collect(Collectors.toList())
         );
//         los elimino de la DB
         ServicioAdicionales sa;
         for (String IDsa : idsView) {
            sa = servicioAdicionalesRepository.findOne(IDsa);
            if(sa!=null) {
               sa.setPaciente(null);
               servicioAdicionalesRepository.delete(IDsa);
            }else{
               logger.error("===>>>idServicioAdicionales no existente: {}", IDsa);
               ServicioAdicionalesException sae = new ServicioAdicionalesException("No se encuentra en el sistema al idServicioAdicionales.", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_VALIDATE);
               sae.addError("idServicioAdicionales no existente: " + IDsa);
               throw sae;
            }
         }
//         limpiar la lista
         paciente.setServicioAdicionalesList(new ArrayList<>());
//         la "nueva lista"
         Collection<ServicioAdicionales> servicioAdicionales = new ArrayList<>();

         servicioAdicionalesRepository.save(servicioAdicionales);

      } catch (ServicioAdicionalesException sae) {
         throw sae;
      } catch (Exception ex) {
         ServicioAdicionalesException sae = new ServicioAdicionalesException("Error inesperado al borrar servicio adicionales", ServicioAdicionalesException.LAYER_DAO, ServicioAdicionalesException.ACTION_DELETE);
         sae.addError("Ocurrio un error al borrar servicio adicionales");
         logger.error("===>>>Error al borrar servicio adicionales - CODE: {} - {}", sae.getExceptionCode(), servicioAdicionalesViewArrayList, ex);
         throw sae;
      }
   }


}
