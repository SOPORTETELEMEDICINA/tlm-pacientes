package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "control_notificaciones_logs", indexes =  {
        @Index(name = "idx_control_notificaciones_logs_id", columnList = "id_error"),
        @Index(name = "idx_control_notificaciones_logs_id2", columnList = "id_error, id_paciente"),
        @Index(name = "idx_control_notificaciones_logs_id3", columnList = "id_error, id_paciente, id_user")
})
public class ControlNotificacionLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_error")
    Long id;

    @Column(name = "id_paciente")
    String idPaciente;

    @Column(name = "id_user")
    Long idUser;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

    @Column(name = "tipo_notificacion")
    Long tipoNotif;

    @Column(name = "descripcion")
    String descripcion;

}
