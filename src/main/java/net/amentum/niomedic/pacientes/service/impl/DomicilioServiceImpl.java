package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.DomicilioConverter;
import net.amentum.niomedic.pacientes.exception.DomicilioException;
import net.amentum.niomedic.pacientes.model.Domicilio;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.persistence.DomicilioRepository;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.service.DomicilioService;
import net.amentum.niomedic.pacientes.views.DomicilioView;
import net.amentum.niomedic.pacientes.views.PacienteView;
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
public class DomicilioServiceImpl implements DomicilioService {
   private final Logger logger = LoggerFactory.getLogger(DomicilioServiceImpl.class);

   private DomicilioRepository domicilioRepository;
   private DomicilioConverter domicilioConverter;
   private PacienteRepository pacienteRepository;

   @Autowired
   public void setDomicilioRepository(DomicilioRepository domicilioRepository) {
      this.domicilioRepository = domicilioRepository;
   }

   @Autowired
   public void setDomicilioConverter(DomicilioConverter domicilioConverter) {
      this.domicilioConverter = domicilioConverter;
   }

   @Autowired
   public void setPacienteRepository(PacienteRepository pacienteRepository) {
      this.pacienteRepository = pacienteRepository;
   }


   @Transactional(readOnly = false, rollbackFor = {DomicilioException.class})
   @Override
   public void createDomicilio(ArrayList<DomicilioView> domicilioViewArrayList, String idPaciente) throws DomicilioException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DomicilioException dome = new DomicilioException("No se encuentra en el sistema al paciente.", DomicilioException.LAYER_DAO, DomicilioException.ACTION_VALIDATE);
            dome.addError("idPaciente no encontrado:" + idPaciente);
            throw dome;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         ArrayList<Domicilio> domicilioArrayList = new ArrayList<>();
         domicilioConverter.toEntity(domicilioViewArrayList, domicilioArrayList, paciente, Boolean.FALSE);
         logger.debug("===>>>Insertar nuevas domicilio: {}", domicilioArrayList);
         domicilioRepository.save(domicilioArrayList);
      } catch (DomicilioException dome) {
         throw dome;
      } catch (DataIntegrityViolationException dte) {
         DomicilioException dome = new DomicilioException("No fue posible agregar domicilio", DomicilioException.LAYER_DAO, DomicilioException.ACTION_INSERT);
         dome.addError("Ocurrio un error al agregar domicilio");
         logger.error("===>>>Error al insertar nueva domicilio - CODE: {} - {}", dome.getExceptionCode(), domicilioViewArrayList, dte);
         throw dome;
      } catch (Exception ex) {
         DomicilioException dome = new DomicilioException("Error inesperado al agregar domicilio", DomicilioException.LAYER_DAO, DomicilioException.ACTION_INSERT);
         dome.addError("Ocurrio un error al agregar domicilio");
         logger.error("===>>>Error al insertar nueva domicilio - CODE: {} - {}", dome.getExceptionCode(), domicilioViewArrayList, ex);
         throw dome;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {DomicilioException.class})
   @Override
   public void updateDomicilio(ArrayList<DomicilioView> domicilioViewArrayList, String idPaciente) throws DomicilioException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DomicilioException dome = new DomicilioException("No se encuentra en el sistema al paciente.", DomicilioException.LAYER_DAO, DomicilioException.ACTION_VALIDATE);
            dome.addError("idPaciente no encontrado:" + idPaciente);
            throw dome;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setDomicilioViewList(domicilioViewArrayList);

//         los elimino de la DB
         Collection<String> noExistenDomicilioView = domicilioConverter.obtenerIDNoExistentesDomicilio(paciente, pacienteView);
         Domicilio dom;
         for (String IDdom : noExistenDomicilioView) {
            dom = domicilioRepository.findOne(IDdom);
            dom.setPaciente(null);
            domicilioRepository.delete(IDdom);
         }
//         limpiar la lista
         paciente.setDomicilioList(new ArrayList<>());
//         la "nueva lista"
         Collection<Domicilio> domicilio = new ArrayList<>();

         domicilioConverter.toEntity(domicilioViewArrayList, domicilio, paciente, Boolean.TRUE);
         logger.debug("Modificar domicilio: {}", domicilio);
         domicilioRepository.save(domicilio);
      } catch (DomicilioException dome) {
         throw dome;
      } catch (DataIntegrityViolationException dte) {
         DomicilioException dome = new DomicilioException("No fue posible modificar domicilio", DomicilioException.LAYER_DAO, DomicilioException.ACTION_UPDATE);
         dome.addError("Ocurrio un error al modificar domicilio");
         logger.error("===>>>Error al modificar domicilio - CODE: {} - {}", dome.getExceptionCode(), domicilioViewArrayList, dte);
         throw dome;
      } catch (Exception ex) {
         DomicilioException dome = new DomicilioException("Error inesperado al modificar domicilio", DomicilioException.LAYER_DAO, DomicilioException.ACTION_UPDATE);
         dome.addError("Ocurrio un error al modificar domicilio");
         logger.error("===>>>Error al modificar nuevo domicilio - CODE: {} - {}", dome.getExceptionCode(), domicilioViewArrayList, ex);
         throw dome;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {DomicilioException.class})
   @Override
   public void deleteDomicilio(ArrayList<DomicilioView> domicilioViewArrayList, String idPaciente) throws DomicilioException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DomicilioException dome = new DomicilioException("No se encuentra en el sistema al paciente.", DomicilioException.LAYER_DAO, DomicilioException.ACTION_VALIDATE);
            dome.addError("idPaciente no encontrado:" + idPaciente);
            throw dome;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setDomicilioViewList(domicilioViewArrayList);

//         obtener los id a borrar
         Collection<String> idsView = new ArrayList<>();
         idsView.addAll(
            pacienteView.getDomicilioViewList().stream()
               .map(domV -> {
                  String idV = domV.getIdDomicilio();
                  return idV;
               }).collect(Collectors.toList())
         );
//         los elimino de la DB
         Domicilio dom;
         for (String IDdom : idsView) {
            dom = domicilioRepository.findOne(IDdom);
            if (dom != null) {
               dom.setPaciente(null);
               domicilioRepository.delete(IDdom);
            } else {
               logger.error("===>>>idDomicilio no encotrado: {}", IDdom);
               DomicilioException dome = new DomicilioException("No se encuentra en el sistema al idDomicilio.", DomicilioException.LAYER_DAO, DomicilioException.ACTION_VALIDATE);
               dome.addError("idDomicilio no encotrado: " + IDdom);
               throw dome;
            }
         }
//         limpiar la lista
         paciente.setDomicilioList(new ArrayList<>());
//         la "nueva lista"
         Collection<Domicilio> domicilio = new ArrayList<>();

         domicilioRepository.save(domicilio);
      } catch (DomicilioException dome) {
         throw dome;
      } catch (Exception ex) {
         DomicilioException dome = new DomicilioException("Error inesperado al borrar domicilio", DomicilioException.LAYER_DAO, DomicilioException.ACTION_DELETE);
         dome.addError("Ocurrio un error alborrar domicilio");
         logger.error("===>>>Error al borrar domicilio - CODE: {} - {}", dome.getExceptionCode(), idPaciente, ex);
         throw dome;
      }

   }

}
