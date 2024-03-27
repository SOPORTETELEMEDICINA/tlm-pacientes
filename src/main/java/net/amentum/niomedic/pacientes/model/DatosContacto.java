package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "datos_contacto")
public class DatosContacto implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idDatosContacto;
    private String nombre;
    private String parentesco;
    private Integer edad;
    private String convivencia;
    private Integer llamarCasoEmergencia;
    private Boolean cuentaConLlaves;
    private String telefonoLocal;
    private String telefonoCelular;
    private String telefonoOficina;
    private String tipoApoyoMaterial;
    private String tipoApoyoEmocional;
    private String tipoApoyoSocial;
    private String tipoApoyoInstrumental;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    private String prioridad;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
    private Paciente paciente;

   @Override
   public String toString() {
      return "DatosContacto{" +
         "idDatosContacto='" + idDatosContacto + '\'' +
         ", nombre='" + nombre + '\'' +
         ", parentesco='" + parentesco + '\'' +
         ", edad=" + edad +
         ", convivencia='" + convivencia + '\'' +
         ", llamarCasoEmergencia=" + llamarCasoEmergencia +
         ", cuentaConLlaves=" + cuentaConLlaves +
         ", telefonoLocal='" + telefonoLocal + '\'' +
         ", telefonoCelular='" + telefonoCelular + '\'' +
         ", telefonoOficina='" + telefonoOficina + '\'' +
         ", tipoApoyoMaterial='" + tipoApoyoMaterial + '\'' +
         ", tipoApoyoEmocional='" + tipoApoyoEmocional + '\'' +
         ", tipoApoyoSocial='" + tipoApoyoSocial + '\'' +
         ", tipoApoyoInstrumental='" + tipoApoyoInstrumental + '\'' +
         ", fechaCreacion=" + fechaCreacion +
         ", prioridad='" + prioridad + '\'' +
         '}';
   }
}
