package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.model.RelacionTitular;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String>, JpaSpecificationExecutor {
   Paciente findByIdUsuario(@NotNull long idUsuario) throws Exception;

   Paciente findByCurp(@NotNull String curp) throws Exception;

   Paciente findByRfc(@NotNull String rfc) throws Exception;

   //Page<Paciente> findAll(PageRequest request) throws Exception;

   // Inicia GGR20200618
   @Query(value = "select pac.id_usuario from paciente pac join pacientes_grupos pg on pg.id_paciente = pac.id_paciente where pg.id_group = :idGroup", nativeQuery = true)
   List<Integer> findIdByGroup(@Param("idGroup") Long idGroup) throws Exception;

   @Query(value = "SELECT p FROM Paciente p WHERE idUsuario in(:usuarios) " +
               "and (lower(p.datosBusqueda) like :datosBusqueda " +
               "or lower(p.telefonoCelular) like :datosBusqueda " +
               "or lower(p.email) like :datosBusqueda)")
   Page<Paciente> findAllByGroup(@Param("usuarios") List<Long> usuarios, @Param("datosBusqueda") String datosBusqueda, Pageable pageable) throws Exception;

   @Query(value = "SELECT p FROM Paciente p WHERE lower(datosBusqueda) like :datosBusqueda")
   Page<Paciente> findAllByGroup(@Param("datosBusqueda") String datosBusqueda, Pageable pageable) throws Exception;
   // Fin GGR20200618
   @Query("SELECT p FROM Paciente p WHERE p.es_titular = true AND p.telefonoCelular = ?1")
   Paciente findByEsTitularTrueAndTelefonoCelular(String telefono);

    // Solo ACTIVOS, pertenecen al grupo, y con idDevice REAL (no null, no vacío, no "null")
    @Query("SELECT p FROM Paciente p " +
            "WHERE p.activo = TRUE " +
            "AND p.idUsuario IN (:usuarios) " +
            "AND p.idDevice IS NOT NULL " +
            "AND LENGTH(TRIM(p.idDevice)) > 0 " +
            "AND LOWER(TRIM(p.idDevice)) <> 'null'")
    Page<Paciente> findByUsuariosWithDeviceActivos(@Param("usuarios") List<Long> usuarios,
                                                   Pageable pageable);


}
