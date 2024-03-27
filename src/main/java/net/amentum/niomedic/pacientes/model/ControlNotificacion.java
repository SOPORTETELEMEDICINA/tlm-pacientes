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
@Table(name = "control_notificaciones", indexes = {
        @Index(name = "idx_control_notificaciones", columnList = "id_control_notificacion"),
        @Index(name = "idx_control_notificaciones_2", columnList = "id_user, id_device"),
        @Index(name = "idx_control_notificaciones_3", columnList = "id_user, id_notification"),
        @Index(name = "idx_control_notificaciones_4", columnList = "id_user, id_device, id_notification")})
public class ControlNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_control_notificacion")
    Long idControl;
    @Column(name = "id_user")
    Long idUser;
    @Column(name = "id_device")
    String idDevice;
    @Column(name = "id_notification")
    String idNotification;
    @Column(name = "tipo_notificacion")
    Long tipoNotificacion;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

}
