package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.RelacionTutores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelacionTutoresRespository extends JpaRepository<RelacionTutores, Integer>, JpaSpecificationExecutor {

    @Query(value = "select case when count(rt)>0 then true else false end from relacion_tutores rt where rt.id_pac_tutor = :idPaciente", nativeQuery = true)
    boolean existTutor(@Param("idPaciente") String idPaciente);

    RelacionTutores findByIdPacTutor(String idPacTutor);

    RelacionTutores findByIdTutor(Integer idTutor);
}
