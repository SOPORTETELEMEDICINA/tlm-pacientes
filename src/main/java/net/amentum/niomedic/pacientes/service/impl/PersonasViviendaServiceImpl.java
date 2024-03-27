package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.PersonasViviendaConverter;
import net.amentum.niomedic.pacientes.exception.PersonasViviendaException;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.PersonasVivienda;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.persistence.PersonasViviendaRepository;
import net.amentum.niomedic.pacientes.service.PersonasViviendaService;
import net.amentum.niomedic.pacientes.views.PacienteView;
import net.amentum.niomedic.pacientes.views.PersonasViviendaView;
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
public class PersonasViviendaServiceImpl implements PersonasViviendaService {
   private final Logger logger = LoggerFactory.getLogger(PersonasViviendaServiceImpl.class);

   private PersonasViviendaRepository personasViviendaRepository;
   private PersonasViviendaConverter personasViviendaConverter;
   private PacienteRepository pacienteRepository;

   @Autowired
   public void setPersonasViviendaRepository(PersonasViviendaRepository personasViviendaRepository) {
      this.personasViviendaRepository = personasViviendaRepository;
   }

   @Autowired
   public void setPersonasViviendaConverter(PersonasViviendaConverter personasViviendaConverter) {
      this.personasViviendaConverter = personasViviendaConverter;
   }

   @Autowired
   public void setPacienteRepository(PacienteRepository pacienteRepository) {
      this.pacienteRepository = pacienteRepository;
   }


   @Transactional(readOnly = false, rollbackFor = {PersonasViviendaException.class})
   @Override
   public void createPersonasVivienda(ArrayList<PersonasViviendaView> personasViviendaViewArrayList, String idPaciente) throws PersonasViviendaException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            PersonasViviendaException pve = new PersonasViviendaException("No se encuentra en el sistema al paciente.", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_VALIDATE);
            pve.addError("Paciente no encontrado: "+idPaciente);
            throw pve;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         ArrayList<PersonasVivienda> personasViviendaArrayList = new ArrayList<>();
         personasViviendaConverter.toEntity(personasViviendaViewArrayList, personasViviendaArrayList, paciente, Boolean.FALSE);
         logger.debug("===>>>Insertar nuevas personas vivienda: {}", personasViviendaArrayList);
         personasViviendaRepository.save(personasViviendaArrayList);
      } catch (PersonasViviendaException pve) {
         throw pve;
      } catch (DataIntegrityViolationException dte) {
         PersonasViviendaException pve = new PersonasViviendaException("No fue posible agregar personas vivienda", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_INSERT);
         pve.addError("Ocurrio un error al agregar personas vivienda");
         logger.error("===>>>Error al insertar nueva personas vivienda - CODE: {} - {}", pve.getExceptionCode(), personasViviendaViewArrayList, dte);
         throw pve;
      } catch (Exception ex) {
         PersonasViviendaException pve = new PersonasViviendaException("Error inesperado al agregar personas vivienda", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_INSERT);
         pve.addError("Ocurrio un error al agregar personas vivienda");
         logger.error("===>>>Error al insertar nueva personas vivienda - CODE: {} - {}", pve.getExceptionCode(), personasViviendaViewArrayList, ex);
         throw pve;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {PersonasViviendaException.class})
   @Override
   public void updatePersonasVivienda(ArrayList<PersonasViviendaView> personasViviendaViewArrayList, String idPaciente) throws PersonasViviendaException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            PersonasViviendaException pve = new PersonasViviendaException("No se encuentra en el sistema al paciente.", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_VALIDATE);
            pve.addError("Paciente no encontrado: " + idPaciente);
            throw pve;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setPersonasViviendaViewList(personasViviendaViewArrayList);

//         los elimino de la DB
         Collection<String> noExistenPersonasViviendaView = personasViviendaConverter.obtenerIDNoExistentesPersonasVivienda(paciente, pacienteView);
         PersonasVivienda pv;
         for (String IDpv : noExistenPersonasViviendaView) {
            pv = personasViviendaRepository.findOne(IDpv);
            pv.setPaciente(null);
            personasViviendaRepository.delete(IDpv);
         }
//         limpiar la lista
         paciente.setPersonasViviendaList(new ArrayList<>());
//         la "nueva lista"
         Collection<PersonasVivienda> personasVivienda = new ArrayList<>();

         personasViviendaConverter.toEntity(personasViviendaViewArrayList, personasVivienda, paciente, Boolean.TRUE);
         logger.debug("===>>>Modificar  Personas Vivienda: {}", personasVivienda);
         personasViviendaRepository.save(personasVivienda);
      } catch (PersonasViviendaException pve) {
         throw pve;
      } catch (DataIntegrityViolationException dte) {
         PersonasViviendaException pve = new PersonasViviendaException("No fue posible modificar personas vivienda", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_UPDATE);
         pve.addError("Ocurrio un error al modificar personas vivienda");
         logger.error("===>>>Error al modificar nuevo personas vivienda - CODE: {} - {}", pve.getExceptionCode(), personasViviendaViewArrayList, dte);
         throw pve;
      } catch (Exception ex) {
         PersonasViviendaException pve = new PersonasViviendaException("Error inesperado al modificar personas vivienda", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_UPDATE);
         pve.addError("Ocurrio un error al modificar personas vivienda");
         logger.error("===>>>Error al modificar nuevo personas vivienda - CODE: {} - {}", pve.getExceptionCode(), personasViviendaViewArrayList, ex);
         throw pve;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {PersonasViviendaException.class})
   @Override
   public void deletePersonasVivienda(ArrayList<PersonasViviendaView> personasViviendaViewArrayList, String idPaciente) throws PersonasViviendaException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            PersonasViviendaException pve = new PersonasViviendaException("No se encuentra en el sistema al paciente.", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_VALIDATE);
            pve.addError("Paciente no encontrado: " + idPaciente);
            throw pve;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setPersonasViviendaViewList(personasViviendaViewArrayList);

//         obtener los id a borrar
         Collection<String> idsView = new ArrayList<>();
         idsView.addAll(
            pacienteView.getPersonasViviendaViewList().stream()
               .map(pvV -> {
                  String idV = pvV.getIdPersonasVivienda();
                  return idV;
               }).collect(Collectors.toList())
         );
//         los elimino de la DB
         PersonasVivienda pv;
         for (String IDpv : idsView) {
            pv = personasViviendaRepository.findOne(IDpv);
            if(pv!=null) {
               pv.setPaciente(null);
               personasViviendaRepository.delete(IDpv);
            }else{
               logger.error("===>>>idPersonasVivienda no existente: {}", IDpv);
               PersonasViviendaException pve = new PersonasViviendaException("No se encuentra en el sistema al idPersonasVivienda.", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_VALIDATE);
               pve.addError("idPersonasVivienda no existente: " + IDpv);
               throw pve;
            }
         }
//         limpiar la lista
         paciente.setPersonasViviendaList(new ArrayList<>());
//         la "nueva lista"
         Collection<PersonasVivienda> personasVivienda = new ArrayList<>();

         personasViviendaRepository.save(personasVivienda);
      } catch (PersonasViviendaException pve) {
         throw pve;
      } catch (Exception ex) {
         PersonasViviendaException pve = new PersonasViviendaException("Error inesperado al borrar personas vivienda", PersonasViviendaException.LAYER_DAO, PersonasViviendaException.ACTION_DELETE);
         pve.addError("Ocurrio un error alborrar personas vivienda");
         logger.error("===>>>Error al borrar personas vivienda - CODE: {} - {}", pve.getExceptionCode(), idPaciente, ex);
         throw pve;
      }

   }

}
