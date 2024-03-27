package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.DatosContactoConverter;
import net.amentum.niomedic.pacientes.exception.DatosContactoException;
import net.amentum.niomedic.pacientes.model.DatosContacto;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.persistence.DatosContactoRepository;
import net.amentum.niomedic.pacientes.persistence.PacienteRepository;
import net.amentum.niomedic.pacientes.service.DatosContactoService;
import net.amentum.niomedic.pacientes.views.DatosContactoView;
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
public class DatosContactoServiceImpl implements DatosContactoService {
   private final Logger logger = LoggerFactory.getLogger(DatosContactoServiceImpl.class);

   private DatosContactoRepository datosContactoRepository;
   private DatosContactoConverter datosContactoConverter;
   private PacienteRepository pacienteRepository;

   @Autowired
   public void setDatosContactoRepository(DatosContactoRepository datosContactoRepository) {
      this.datosContactoRepository = datosContactoRepository;
   }

   @Autowired
   public void setDatosContactoConverter(DatosContactoConverter datosContactoConverter) {
      this.datosContactoConverter = datosContactoConverter;
   }

   @Autowired
   public void setPacienteRepository(PacienteRepository pacienteRepository) {
      this.pacienteRepository = pacienteRepository;
   }


   @Transactional(readOnly = false, rollbackFor = {DatosContactoException.class})
   @Override
   public void createDatosContacto(ArrayList<DatosContactoView> datosContactoViewArrayList, String idPaciente) throws DatosContactoException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DatosContactoException dce = new DatosContactoException("No se encuentra en el sistema al paciente.", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_VALIDATE);
            dce.addError("idPaciente no encontrado:" + idPaciente);
            throw dce;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         ArrayList<DatosContacto> datosContactoArrayList = new ArrayList<>();
         datosContactoConverter.toEntity(datosContactoViewArrayList, datosContactoArrayList, paciente, Boolean.FALSE);
         logger.debug("===>>>Insertar nuevos datos contacto: {}", datosContactoArrayList);
         datosContactoRepository.save(datosContactoArrayList);
      } catch (DatosContactoException dce) {
         throw dce;
      } catch (DataIntegrityViolationException dte) {
         DatosContactoException dce = new DatosContactoException("No fue posible agregar datos contacto", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_INSERT);
         dce.addError("Ocurrio un error al agregar datos contacto");
         logger.error("===>>>Error al insertar nueva datos contacto - CODE: {} - {}", dce.getExceptionCode(), datosContactoViewArrayList, dte);
         throw dce;
      } catch (Exception ex) {
         DatosContactoException dce = new DatosContactoException("Error inesperado al agregar datos contacto", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_INSERT);
         dce.addError("Ocurrio un error al agregar datos contacto");
         logger.error("===>>>Error al insertar nueva datos contacto - CODE: {} - {}", dce.getExceptionCode(), datosContactoViewArrayList, ex);
         throw dce;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {DatosContactoException.class})
   @Override
   public void updateDatosContacto(ArrayList<DatosContactoView> datosContactoViewArrayList, String idPaciente) throws DatosContactoException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DatosContactoException dce = new DatosContactoException("No se encuentra en el sistema al paciente.", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_VALIDATE);
            dce.addError("idPaciente no encontrado:" + idPaciente);
            throw dce;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setDatosContactoViewList(datosContactoViewArrayList);

//         los elimino de la DB
         Collection<String> noExistenDatosContactoView = datosContactoConverter.obtenerIDNoExistentesDatosContacto(paciente, pacienteView);

         DatosContacto dc;
         for (String IDdc : noExistenDatosContactoView) {
            dc = datosContactoRepository.findOne(IDdc);
            dc.setPaciente(null);
            datosContactoRepository.delete(IDdc);
         }
//         limpiar la lista
         paciente.setDatosContactoList(new ArrayList<>());
//         la "nueva lista"
         Collection<DatosContacto> datosContacto = new ArrayList<>();

         datosContactoConverter.toEntity(datosContactoViewArrayList, datosContacto, paciente, Boolean.TRUE);
         logger.debug("===>>>Modificar  Datos Contacto: {}", datosContacto);
         datosContactoRepository.save(datosContacto);
      } catch (DatosContactoException dce) {
         throw dce;
      } catch (DataIntegrityViolationException dte) {
         DatosContactoException dce = new DatosContactoException("No fue posible modificar datos contacto", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_UPDATE);
         dce.addError("Ocurrio un error al modificar datos contacto");
         logger.error("===>>>Error al modificar  datos contacto - CODE: {} - {}", dce.getExceptionCode(), datosContactoViewArrayList, dte);
         throw dce;
      } catch (Exception ex) {
         DatosContactoException dce = new DatosContactoException("Error inesperado al modificar datos contacto", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_UPDATE);
         dce.addError("Ocurrio un error al modificar datos contacto");
         logger.error("===>>>Error al modificar datos contacto - CODE: {} - {}", dce.getExceptionCode(), datosContactoViewArrayList, ex);
         throw dce;
      }
   }

   @Transactional(readOnly = false, rollbackFor = {DatosContactoException.class})
   @Override
   public void deleteDatosContacto(ArrayList<DatosContactoView> datosContactoViewArrayList, String idPaciente) throws DatosContactoException {
      try {
         if (!pacienteRepository.exists(idPaciente)) {
            logger.error("===>>>idPaciente no encotrado: {}", idPaciente);
            DatosContactoException dce = new DatosContactoException("No se encuentra en el sistema al paciente.", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_VALIDATE);
            dce.addError("idPaciente no encontrado:" + idPaciente);
            throw dce;
         }
         Paciente paciente = pacienteRepository.findOne(idPaciente);
         PacienteView pacienteView = new PacienteView();
         pacienteView.setDatosContactoViewList(datosContactoViewArrayList);

//         obtener los id a borrar
         Collection<String> idsView = new ArrayList<>();
         idsView.addAll(
            pacienteView.getDatosContactoViewList().stream()
               .map(dcV -> {
                  String idV = dcV.getIdDatosContacto();
                  return idV;
               }).collect(Collectors.toList())
         );

//         los elimino de la DB
         DatosContacto dc;
         for (String IDdc : idsView) {
            dc = datosContactoRepository.findOne(IDdc);
            if (dc != null) {
               dc.setPaciente(null);
               datosContactoRepository.delete(IDdc);
            } else {
               logger.error("===>>>idDatosContacto no existente: {}", IDdc);
               DatosContactoException dce = new DatosContactoException("No se encuentra en el sistema al idDatosContacto.", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_VALIDATE);
               dce.addError("idDatosContacto no existente: " + IDdc);
               throw dce;
            }
         }
//         limpiar la lista
         paciente.setDatosContactoList(new ArrayList<>());
//         la "nueva lista"
         Collection<DatosContacto> datosContacto = new ArrayList<>();

         datosContactoRepository.save(datosContacto);
      } catch (DatosContactoException dce) {
         throw dce;
      } catch (Exception ex) {
         DatosContactoException dce = new DatosContactoException("Error inesperado al borrar datos contacto", DatosContactoException.LAYER_DAO, DatosContactoException.ACTION_DELETE);
         dce.addError("Ocurrio un error al borrar datos contacto");
         logger.error("===>>>Error al borrar datos contacto - CODE: {} - {}", dce.getExceptionCode(), idPaciente, ex);
         throw dce;
      }

   }

}
