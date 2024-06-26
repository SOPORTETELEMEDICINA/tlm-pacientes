package net.amentum.niomedic.pacientes.service;

import java.util.List; // Sre19062020 nuevo
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.model.PacienteDTO;
import net.amentum.niomedic.pacientes.model.RelacionTitular;
import net.amentum.niomedic.pacientes.views.PacientePageView;
import net.amentum.niomedic.pacientes.views.PacienteTitularView;
import net.amentum.niomedic.pacientes.views.PacienteView;
import org.springframework.data.domain.Page;

public interface PacienteService {
	PacienteView createPaciente(PacienteView pacienteView) throws PacienteException;

	PacienteView updatePaciente(PacienteView pacienteView) throws PacienteException;

    PacienteView getDetailsPacienteById(String pacienteId) throws PacienteException;

    PacienteView getDetailsPacienteByUsuario(Integer idUsuario) throws PacienteException;

    PacienteView getDetailsPacienteByCurp(String curp) throws PacienteException;

//    Page<PacienteView> getPacientePage(Boolean active, String name, Integer page, Integer size, String orderColumn, String orderType) throws PacienteException;
//    Page<PacientePageView> getPacientePage(Boolean active, String name, Integer page, Integer size, String orderColumn, String orderType) throws PacienteException;
    // GGR20200618 Agrego grupo seleccionado
    Page<PacientePageView> getPacientePage(String datosBusqueda, Boolean active, Integer page, Integer size, String orderColumn, String orderType, Long selectGroup) throws PacienteException;
    Page<PacientePageView> getPadecimientoSearch(Boolean active, String datosBusqueda,String nombre,String apellidoPaterno,String apellidoMaterno,String telefonoMovil,String telefonoFijo,String correo,String rfc,String curp,Integer page,Integer size,String orderColumn,String orderType)throws PacienteException;
    // Sre19062020 Nuevo servicio para actualizar grupos de paciente
    void updatePacienteGroups(Long idUsuario, List<Long> medicoGroups) throws PacienteException;
    // GGR20200626 Obtener la segunda lista de pacientes canalizados
    Page<PacientePageView> getPacientePageCanalizados(String datosBusqueda, Boolean active, Integer page, Integer size, String orderColumn, String orderType, Long idUser) throws PacienteException;

    Page<PacientePageView> getPacientePageAtendidos   (String datosBusqueda, Boolean active, Integer page, Integer size, String orderColumn, String orderType, Long selectGroup) throws PacienteException;


    void deleteRollback(Integer idUserApp) throws PacienteException;

    void updateIdDevice(Integer idUsuario, String idDevice) throws PacienteException;

    PacienteTitularView getTitularPorTelefono(String telefono);
}
