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
@Table(name = "datos_adicionales")
public class DatosAdicionales implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idDatosAdicionales;
    private String escolaridad;
    private String ocupacion;
    private String ocupacionAnterior;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    private String origenEtnico;
    private Boolean viveSolo;
    private String localidad;
    private String lenguaIndigena;
    private String antecedentes;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
    private Paciente paciente;

   @Override
   public String toString() {
      return "DatosAdicionales{" +
         "idDatosAdicionales='" + idDatosAdicionales + '\'' +
         ", escolaridad='" + escolaridad + '\'' +
         ", ocupacion='" + ocupacion + '\'' +
         ", ocupacionAnterior='" + ocupacionAnterior + '\'' +
         ", fechaCreacion=" + fechaCreacion +
         ", origenEtnico='" + origenEtnico + '\'' +
         ", viveSolo=" + viveSolo +
         ", localidad='" + localidad + '\'' +
         ", lenguaIndigena='" + lenguaIndigena + '\'' +
         ", antecedentes='" + antecedentes + '\'' +
         '}';
   }
}
