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
@Table(name = "pacientes_grupos")
public class PacientesGrupos implements Serializable {

   @Column(name = "id_paciente")
   private String idPaciente;

   @Column(name = "id_group")
   private Integer idGroup;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_pacientes_grupos")
   private Integer idPacientesGrupos;

}
