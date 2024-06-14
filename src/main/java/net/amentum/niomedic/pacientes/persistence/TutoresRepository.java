package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.Tutores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface TutoresRepository extends JpaRepository<Tutores, Integer>, JpaSpecificationExecutor {

    Tutores findByIdTutor(@NotNull String idTutor);

    @Query(value = "select case when count(t)>0 then true else false end from tutores t where t.id_paciente = :idPaciente", nativeQuery = true)
    boolean existTutor(@Param("idPaciente") String idPaciente);

    List<Tutores> findAll();

}
