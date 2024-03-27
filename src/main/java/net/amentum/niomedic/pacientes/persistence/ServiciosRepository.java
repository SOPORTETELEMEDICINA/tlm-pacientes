package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, String>, JpaSpecificationExecutor {

    List<Servicios> findByidPaciente(String idPaciente) throws Exception;

    @Query(value="select distinct(id_servicio) from paciente_servicios " +
            "where id_paciente = :idPaciente", nativeQuery = true)
    List<Integer> findListByIdPaciente(@Param("idPaciente") String idPaciente) throws Exception;

}
