package net.amentum.niomedic.pacientes.persistence;



import net.amentum.niomedic.pacientes.model.PacientesGrupos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface PacientesGruposRepository extends JpaRepository<PacientesGrupos,Integer>, JpaSpecificationExecutor {

    PacientesGrupos findFirstByIdPaciente(@Param("idPaciente") String idPaciente);
}
