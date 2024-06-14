package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.RelacionTitular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelacionTitularRepository extends JpaRepository<RelacionTitular, String>, JpaSpecificationExecutor {
    List<RelacionTitular> findAllByIdPacienteTitular(String idPacienteTitular) throws Exception;
}
