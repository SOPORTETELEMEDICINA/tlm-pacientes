package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.DatosContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosContactoRepository extends JpaRepository<DatosContacto, String>, JpaSpecificationExecutor {
}
