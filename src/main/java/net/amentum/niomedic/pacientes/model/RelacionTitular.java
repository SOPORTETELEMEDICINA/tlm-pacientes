package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relacion_titular")
public class RelacionTitular implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "idpaciente_titular")
    private String idPacienteTitular;
    @Column(name = "parentesco")
    private String parentesco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpaciente_beneficiario")
    private Paciente pacienteBeneficiario;

    @Override
    public String toString() {
        return "RelactionTitular{" +
                "id='" + id + '\'' +
                ", idpaciente_titular='" + idPacienteTitular + '\'' +
                ", idpaciente_beneficiario='" + pacienteBeneficiario.getIdPaciente() + '\'' +
                ", parentesco='" + parentesco + '\'' +
                '}';
    }
}
