package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.CatServicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatServiciosRepository extends JpaRepository<CatServicios, Integer>, JpaSpecificationExecutor {

    @Query(value = "select * from cat_servicios cat where cat.activo = true", nativeQuery = true)
    List<CatServicios> findCatServicioActivo() throws Exception;

    @Query(value = "select case when count(cat)>0 then true else false end from cat_servicios cat where cat.id_servicio = :idServicio and cat.activo = true", nativeQuery = true)
    boolean existServicio(@Param("idServicio") Integer idServicio);

    CatServicios findByIdServicio(Integer idServicio);

}
