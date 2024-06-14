package net.amentum.niomedic.pacientes.rest;

import java.util.List;
import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.model.PacienteDTO;
import net.amentum.niomedic.pacientes.model.RelacionTitular;
import net.amentum.niomedic.pacientes.service.DatosAdicionalesService;
import net.amentum.niomedic.pacientes.service.PacienteService;
import net.amentum.niomedic.pacientes.views.PacientePageView;
import net.amentum.niomedic.pacientes.views.PacienteView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("pacientes")
public class PacienteRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(PacienteRest.class);

   private PacienteService pacienteService;
   private DatosAdicionalesService datosAdicionalesService;

   @Autowired
   public void setDatosAdicionalesService(DatosAdicionalesService datosAdicionalesService) {
      this.datosAdicionalesService = datosAdicionalesService;
   }

   @Autowired
   public void setPacienteService(PacienteService pacienteService) {
      this.pacienteService = pacienteService;
   }

   @RequestMapping(method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public PacienteView createPaciente(@RequestBody @Valid PacienteView pacienteView) throws PacienteException {
      try {
         logger.info("===>>>Guardar nuevo Paciente: {}", pacienteView);
         return pacienteService.createPaciente(pacienteView);
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible insertar  Paciente", PacienteException.LAYER_REST, PacienteException.ACTION_INSERT);
         logger.error("===>>>Error al insertar  Paciente- CODE: {} - ", pe.getExceptionCode(), ex);
         throw pe;
      }
   }

   @RequestMapping(value = "{idPaciente}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public PacienteView updatePaciente(@PathVariable() String idPaciente, @RequestBody @Valid PacienteView pacienteView) throws PacienteException {
      try {
         pacienteView.setIdPaciente(idPaciente);
         logger.info("===>>>Editar Paciente: {}", pacienteView);
          return pacienteService.updatePaciente(pacienteView);
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible modificar  Paciente", PacienteException.LAYER_REST, PacienteException.ACTION_UPDATE);
         logger.error("===>>>Error al modificar  Paciente- CODE: {} - ", pe.getExceptionCode(), ex);
         throw pe;
      }
   }

   @RequestMapping(value = "{idPaciente}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public PacienteView getPacienteById(@PathVariable() String idPaciente) throws PacienteException {
      try {
         logger.info("===>>>Obtener los detalles  Paciente por Id: {}", idPaciente);
         return pacienteService.getDetailsPacienteById(idPaciente);
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible obtener  Paciente por Id", PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al obtener  Paciente por Id- CODE: {} - ", pe.getExceptionCode(), ex);
         throw pe;
      }
   }

   @RequestMapping(value = "/obtenerPorIdUsuario/{idUsuario}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public PacienteView getPacienteByIdUsuario(@PathVariable() Integer idUsuario) throws PacienteException {
      try {
         logger.info("===>>>Obtener los detalles  Paciente por IdUsuario: {}", idUsuario);
         return pacienteService.getDetailsPacienteByUsuario(idUsuario);
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible obtener  Paciente por IdUsuario", PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al obtener  Paciente por IdUsuario- CODE: {} - ", pe.getExceptionCode(), ex);
         throw pe;
      }
   }

   @RequestMapping(value = "/obtenerPorCURP/{curp}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public PacienteView getDetailsPacienteByCurp(@PathVariable() String curp) throws PacienteException {
      try {
         logger.info("===>>>Obtener los detalles  Paciente por CURP: {}", curp);
         return pacienteService.getDetailsPacienteByCurp(curp);
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException pe = new PacienteException("No fue posible obtener  Paciente por CURP", PacienteException.LAYER_REST, PacienteException.ACTION_SELECT);
         logger.error("===>>>Error al obtener  Paciente por CURP- CODE: {} - ", pe.getExceptionCode(), ex);
         throw pe;
      }
   }


   @RequestMapping(value = "page", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<PacientePageView> getPacientePage(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                 @RequestParam(required = false) Boolean active,
                                                //  @RequestParam(required = false, defaultValue = "") String name,
                                                 @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false) String orderColumn,
                                                 @RequestParam(required = false) String orderType,
                                                 @RequestParam(required = false) Long selectGroup) throws PacienteException {

      logger.info("===>>>getPacientePage(): - datosBusqueda {} - active {} - page {} - size {} - orderColumn {} - orderType {} - selectGroup {}",
         datosBusqueda, active, page, size, orderColumn, orderType, selectGroup); // GGR20200618 Agrego grupo seleccionado

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "nombre";

      // return pacienteService.getPacientePage(datosBusqueda,active, name != null ? name : "", page, size, orderColumn, orderType);
      return pacienteService.getPacientePage(datosBusqueda,active, page, size, orderColumn, orderType, selectGroup);
   }


   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<PacientePageView> getPacienteSearch(@RequestParam(required = false) Boolean active,
                                                   @RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                   @RequestParam(required = false, defaultValue = "") String nombre,
                                                   @RequestParam(required = false, defaultValue = "") String apellidoPaterno,
                                                   @RequestParam(required = false, defaultValue = "") String apellidoMaterno,
                                                   @RequestParam(required = false, defaultValue = "") String telefonoMovil,
                                                   @RequestParam(required = false, defaultValue = "") String telefonoFijo,
                                                   @RequestParam(required = false, defaultValue = "") String correo,
                                                   @RequestParam(required = false, defaultValue = "") String rfc,
                                                   @RequestParam(required = false, defaultValue = "") String curp,
                                                   @RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam(required = false) String orderColumn,
                                                   @RequestParam(required = false) String orderType) throws PacienteException {
      logger.info("===>>>getPacienteSearch(): " +
            "- active {} - datosBusqueda: {} - nombre: {} - apellidoPaterno: {} - apellidoMaterno: {} - telefonoMovil: {} - telefonoFijo: {} - correo: {} - rfc: {} - curp: {} - " +
            "page: {} - size: {} - orderColumn: {} - orderType: {}",
         active, datosBusqueda, nombre, apellidoPaterno, apellidoMaterno, telefonoMovil, telefonoFijo, correo, rfc, curp, page, size, orderColumn, orderType);

      if (active == null)
         active = true;
      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "nombre";

      return pacienteService.getPadecimientoSearch(active, datosBusqueda, nombre, apellidoPaterno, apellidoMaterno, telefonoMovil, telefonoFijo, correo, rfc, curp, page, size, orderColumn, orderType);
   }

    // Sre19062020 Agrego metodo para actualizar grupos de pacientes
   @RequestMapping(value = "/grupos/{idUsuario}", method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.OK)
   public void updateMedicoGroups(@PathVariable() Long idUsuario, @RequestBody List<Long> medicoGroups) throws PacienteException {
       // Aca debo actualizar los grupos del médico
      try {
         logger.info("Actualizar los grupos del medico por idUsuario: {}", idUsuario);
         pacienteService.updatePacienteGroups(idUsuario, medicoGroups);
      } catch (PacienteException pe) {
         throw pe;
      } catch (Exception ex) {
         PacienteException me = new PacienteException("No fue posible actualizar los grupos del medico por idUsuario", PacienteException.LAYER_REST, PacienteException.ACTION_UPDATE);
         logger.error("Error al actualizar los grupos del medico por idUsuario- CODE: {} - ", me.getExceptionCode(), ex);
         throw me;
      }
   }

   // GGR20200626 Obtener la segunda lista de pacientes canalizados
   @RequestMapping(value = "page/canalizado", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<PacientePageView> getPacientePageCanalizados(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                 @RequestParam(required = false) Boolean active,
                                                 //  @RequestParam(required = false, defaultValue = "") String name,
                                                 @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false) String orderColumn,
                                                 @RequestParam(required = false) String orderType,
                                                 @RequestParam(required = false) Long idUser) throws PacienteException {

      logger.info("===>>>getPacientePageCanalizados(): - datosBusqueda {} - active {} - page {} - size {} - orderColumn {} - orderType {} - idUser {}",
              datosBusqueda, active, page, size, orderColumn, orderType, idUser); // GGR20200618 Agrego grupo seleccionado

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "nombre";

      return pacienteService.getPacientePageCanalizados(datosBusqueda,active, page, size, orderColumn, orderType, idUser);
   }


   @RequestMapping(value = "page/atendidos", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<PacientePageView> getPacientePageAtendidos(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                            @RequestParam(required = false) Boolean active,
                                                            //  @RequestParam(required = false, defaultValue = "") String name,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) String orderColumn,
                                                            @RequestParam(required = false) String orderType,
                                                            @RequestParam(required = false) Long idUser) throws PacienteException {

      logger.info("===>>>getPacientePageAtendidos(): - datosBusqueda {} - active {} - page {} - size {} - orderColumn {} - orderType {} - idUser {}",
              datosBusqueda, active, page, size, orderColumn, orderType, idUser); // GGR20200618 Agrego grupo seleccionado

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "nombre";

      return pacienteService.getPacientePageAtendidos(datosBusqueda,active, page, size, orderColumn, orderType, idUser);
   }

   @RequestMapping(value = "rollback/{idUserApp}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.OK)
   public void deleteRollback(@PathVariable("idUserApp") Integer idUserApp) throws PacienteException {
      logger.info("Rollback para usuario: {}", idUserApp);
      pacienteService.deleteRollback(idUserApp);
   }

   @RequestMapping(value = "setIdDevice", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public void setIdDevice(@RequestParam("idUsuario") Integer idUsuario, @RequestParam String idDevice) throws PacienteException {
      if(idDevice == null || idDevice.isEmpty())  {
         logger.info("IdDevice vacio, Usuario: {}", idUsuario);
         PacienteException exception = new PacienteException("No fue posible actualizar el Usuario", PacienteException.LAYER_REST, PacienteException.ACTION_VALIDATE);
         exception.addError("id de dispositivo vacio");
         throw exception;
      }
      if(idUsuario == null || idUsuario < 0)  {
         logger.info("Id usuario vacio/0");
         PacienteException exception = new PacienteException("No fue posible actualizar el Usuario", PacienteException.LAYER_REST, PacienteException.ACTION_VALIDATE);
         exception.addError("id de usuario vacio/0");
         throw exception;
      }
      logger.info("Usuario para actualizar: {} - idDevice: {}", idUsuario, idDevice);
      pacienteService.updateIdDevice(idUsuario, idDevice);
   }

   @RequestMapping(value = "/getTitularByTelefono/{telefono}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<PacienteDTO> getTitularPorTelefono(@PathVariable("telefono") String telefono) {
      PacienteDTO pacienteDTO = pacienteService.getTitularPorTelefono(telefono);
      if (pacienteDTO == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(pacienteDTO);
   }

   @RequestMapping(value = "/beneficiarios-titular/{idPacienteTitular}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<List<PacienteBeneficiarioDTO>> getBeneficiariosTitular(@PathVariable("idPacienteTitular") String idPacienteTitular) throws Exception {
      List<PacienteBeneficiarioDTO> pacientes = pacienteService.getBeneficiariosTitular(idPacienteTitular);
      if (pacientes == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(pacientes);
   }
}
