package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relacion_tutores")
public class RelacionTutores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relacion")
    private Integer idRelacion;
    @Column(name = "idtutor")
    private Integer idTutor;  // Cambia a Integer
    @Column(name = "id_pac_tutor")
    private String idPacTutor;
    @Column(name = "parentesco")
    private String parentesco;
}
