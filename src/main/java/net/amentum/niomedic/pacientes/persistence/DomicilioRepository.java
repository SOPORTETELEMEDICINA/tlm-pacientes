package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, String>, JpaSpecificationExecutor {
}
