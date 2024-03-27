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
@Table(name = "domicilio")
public class Domicilio implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idDomicilio;
    private String domicilio;
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
    private String cp;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    private Boolean activo;
    //relaciones
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
//    private Paciente paciente;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
   private Paciente paciente;


   @Override
   public String toString() {
      return "Domicilio{" +
         "idDomicilio='" + idDomicilio + '\'' +
         ", domicilio='" + domicilio + '\'' +
         ", colonia='" + colonia + '\'' +
         ", municipio='" + municipio + '\'' +
         ", estado='" + estado + '\'' +
         ", pais='" + pais + '\'' +
         ", cp='" + cp + '\'' +
         ", fechaCreacion=" + fechaCreacion +
         ", activo=" + activo +
         '}';
   }
}
