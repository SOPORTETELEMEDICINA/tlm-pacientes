package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.DatosAdicionalesConverter;
import net.amentum.niomedic.pacientes.exception.DatosAdicionalesException;
import net.amentum.niomedic.pacientes.model.DatosAdicionales;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.persistence.DatosAdicionalesRepository;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.service.DatosAdicionalesService;
import net.amentum.niomedic.pacientes.views.DatosAdicionalesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DatosAdicionalesServiceImpl implements DatosAdicionalesService {
   private final Logger logger = LoggerFactory.getLogger(DatosAdicionalesServiceImpl.class);
   private DatosAdicionalesRepository datosAdicionalesRepository;
   private PacienteRepository pacienteRepository;
   private DatosAdicionalesConverter datosAdicionalesConverter;

   @Autowired
   public void setDatosAdicionalesRepository(DatosAdicionalesRepository datosAdicionalesRepository) {
      this.datosAdicionalesRepository = datosAdicionalesRepository;
   }

   @Autowired
   public void setPacienteRepository(PacienteRepository pacienteRepository) {
      this.pacienteRepository = pacienteRepository;
   }

   @Autowired
   public void setDatosAdicionalesConverter(DatosAdicionalesConverter datosAdicionalesConverter) {
      this.datosAdicionalesConverter = datosAdicionalesConverter;
   }


   @Transactional(readOnly = false, rollbackFor = {DatosAdicionalesException.class})
   @Override
   public void createDatosAdicionales(DatosAdicionalesView datosAdicionalesView, String idPaciente) throws DatosAdicionalesException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DatosAdicionalesException dae = new DatosAdicionalesException("No se encuentra en el sistema al paciente.", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_VALIDATE);
            dae.addError("idPaciente no encontrado:" + idPaciente);
            throw dae;
         }
         DatosAdicionales encontrado = datosAdicionalesRepository.findByPacienteIdPaciente(idPaciente);
         if (encontrado != null) {
            logger.error("===>>>pacienteId duplicado: {}", idPaciente);
            DatosAdicionalesException dae = new DatosAdicionalesException("El registro tiene datos duplicados.", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_VALIDATE);
            dae.addError("pacienteId duplicado: " + idPaciente);
            throw dae;
         }

         Paciente paciente = pacienteRepository.findOne(idPaciente);
         DatosAdicionales datosAdicionales;

         if (paciente.getDatosAdicionales() != null) {
            String tempo = paciente.getDatosAdicionales().getIdDatosAdicionales();
            paciente.setDatosAdicionales(null);
            datosAdicionales = datosAdicionalesRepository.findOne(tempo);
            datosAdicionales.setPaciente(null);
            datosAdicionalesRepository.delete(tempo);
         }

         datosAdicionales = datosAdicionalesConverter.toEntity(datosAdicionalesView, new DatosAdicionales(), paciente, Boolean.FALSE);
         logger.debug("===>>>Insertar nuevos Datos Adicionales: {}", datosAdicionales);
         datosAdicionalesRepository.save(datosAdicionales);

      } catch (DatosAdicionalesException dae) {
         throw dae;
      } catch (DataIntegrityViolationException dte) {
         DatosAdicionalesException dae = new DatosAdicionalesException("No fue posible agregar Datos Adicionales", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_INSERT);
         dae.addError("Ocurrio un error al agregar Datos Adicionales");
         logger.error("===>>>Error al insertar nuevo Datos Adicionales - CODE: {} - {}", dae.getExceptionCode(), datosAdicionalesView, dte);
         throw dae;
      } catch (Exception ex) {
         DatosAdicionalesException dae = new DatosAdicionalesException("Error inesperado al agregar  Datos Adicionales", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_INSERT);
         dae.addError("Ocurrio un error al agregar Datos Adicionales");
         logger.error("===>>>Error al insertar nuevo Datos Adicionales - CODE: {} - {}", dae.getExceptionCode(), datosAdicionalesView, ex);
         throw dae;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {DatosAdicionalesException.class})
   @Override
   public void updateDatosAdicionales(DatosAdicionalesView datosAdicionalesView, String idPaciente) throws DatosAdicionalesException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DatosAdicionalesException dae = new DatosAdicionalesException("No se encuentra en el sistema al idPaciente", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_VALIDATE);
            dae.addError("idPaciente no encontrado:" + idPaciente);
            throw dae;
         }

         if (!datosAdicionalesRepository.exists(datosAdicionalesView.getIdDatosAdicionales())) {
            logger.error("===>>>idDatosAdicionales no encontrado: {}", datosAdicionalesView.getIdDatosAdicionales());
            DatosAdicionalesException dae = new DatosAdicionalesException("No se encuentra en el sistema idDatosAdicionales.", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_VALIDATE);
            dae.addError("idDatosAdicionales no encontrado: " + datosAdicionalesView.getIdDatosAdicionales());
            throw dae;
         }

         Paciente paciente = pacienteRepository.findOne(idPaciente);
         DatosAdicionales datosAdicionales;

         if (paciente.getDatosAdicionales() != null) {
            datosAdicionales = paciente.getDatosAdicionales();
            paciente.setDatosAdicionales(null);
            datosAdicionales = datosAdicionalesConverter.toEntity(datosAdicionalesView, datosAdicionales, paciente, Boolean.TRUE);
         } else {
            logger.error("===>>>Datos Adicionales no encontrado");
            DatosAdicionalesException dae = new DatosAdicionalesException("No se encuentra en el sistema Datos Adicionales", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_VALIDATE);
            dae.addError("Datos Adicionales no encontrado");
            throw dae;
         }
         logger.debug("===>>>Modificar Datos Adicionales: {}", datosAdicionales);
         datosAdicionalesRepository.save(datosAdicionales);
      } catch (DatosAdicionalesException dae) {
         throw dae;
      } catch (DataIntegrityViolationException dive) {
         DatosAdicionalesException dae = new DatosAdicionalesException("No fue posible editar Datos Adicionales", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_UPDATE);
         dae.addError("Ocurrio un error al editar Datos Adicionales");
         logger.error("===>>>Error al editar Datos Adicionales - CODE: {} - {}", dae.getExceptionCode(), datosAdicionalesView, dive);
         throw dae;
      } catch (Exception ex) {
         DatosAdicionalesException dae = new DatosAdicionalesException("Ocurrio un error al editar Datos Adicionales", DatosAdicionalesException.LAYER_DAO, DatosAdicionalesException.ACTION_UPDATE);
         dae.addError("Ocurrio un error al editar Datos Adicionales");
         logger.error("===>>>Error al editar Datos Adicionales - CODE: {} - {}", dae.getExceptionCode(), datosAdicionalesView, ex);
         throw dae;
      }
   }


}
