package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.model.RelacionTitular;
import net.amentum.niomedic.pacientes.views.RelacionTitularView;
import org.springframework.stereotype.Component;

@Component
public class RelacionTitularConverter {

   public RelacionTitular toEntity(RelacionTitularView view) {
      RelacionTitular entity = new RelacionTitular();
      entity.setIdPacienteTitular(view.getIdPacienteTitular());
      entity.setIdPacienteBeneficiario(view.getIdPacienteBeneficiario());
      entity.setParentesco(view.getParentesco());
      return entity;
   }
   public PacienteBeneficiarioDTO convertToPacienteBeneficiarioDTO(RelacionTitular pacienteBeneficiario, Paciente paciente) {
      String nombreCompleto = String.format("%s %s %s", paciente.getNombre(), paciente.getApellidoPaterno(),  paciente.getApellidoMaterno());
      return new PacienteBeneficiarioDTO(paciente.getIdPaciente(), nombreCompleto,  paciente.getTelefonoCelular(),  paciente.getEmail(), pacienteBeneficiario.getParentesco());
   }
}
