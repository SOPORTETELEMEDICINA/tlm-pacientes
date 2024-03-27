package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.ControlNotificacionLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlNotificacionLogsRepository extends JpaRepository<ControlNotificacionLogs, Long> {
}
