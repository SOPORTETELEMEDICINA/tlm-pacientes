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
@Table(name = "personas_vivienda")
public class PersonasVivienda implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idPersonasVivienda;
    private String parentesco;
    private Integer edad;
    private String convivencia;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
    private Paciente paciente;

    @Override
    public String toString() {
        return "PersonasVivienda{" +
                "idPersonasVivienda='" + idPersonasVivienda + '\'' +
                ", parentesco='" + parentesco + '\'' +
                ", edad=" + edad +
                ", convivencia='" + convivencia + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
