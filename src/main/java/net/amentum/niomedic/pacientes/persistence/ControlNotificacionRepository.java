package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.ControlNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlNotificacionRepository extends JpaRepository<ControlNotificacion, Long> {
    List<ControlNotificacion> findAllByIdUserAndIdDeviceAndTipoNotificacion(Long idUserApp, String idDevice, Long tipoNotificacion);
}
