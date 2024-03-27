package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.DatosAdicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface DatosAdicionalesRepository extends JpaRepository<DatosAdicionales, String>, JpaSpecificationExecutor {
   DatosAdicionales findByPacienteIdPaciente(@NotNull String pacienteId) throws Exception;
}
