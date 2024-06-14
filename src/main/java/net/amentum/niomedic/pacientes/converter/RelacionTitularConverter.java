package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.model.RelacionTitular;
import org.springframework.stereotype.Component;

@Component
public class RelacionTitularConverter {
   public PacienteBeneficiarioDTO convertToPacienteBeneficiarioDTO(RelacionTitular pacienteBeneficiario) {
      String nombreCompleto = String.format("%s %s %s", pacienteBeneficiario.getPacienteBeneficiario().getNombre(), pacienteBeneficiario.getPacienteBeneficiario().getApellidoPaterno(),  pacienteBeneficiario.getPacienteBeneficiario().getApellidoMaterno());
      return new PacienteBeneficiarioDTO(pacienteBeneficiario.getPacienteBeneficiario().getIdPaciente(), nombreCompleto,  pacienteBeneficiario.getPacienteBeneficiario().getTelefonoCelular(),  pacienteBeneficiario.getPacienteBeneficiario().getEmail(), pacienteBeneficiario.getParentesco());
   }
}
