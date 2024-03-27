package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.ServicioAdicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioAdicionalesRepository extends JpaRepository<ServicioAdicionales, String>, JpaSpecificationExecutor {
}
