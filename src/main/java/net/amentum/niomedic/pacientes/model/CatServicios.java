package net.amentum.niomedic.pacientes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cat_servicios")
public class CatServicios implements Serializable {

    @Id
    private Integer idServicio;
    private String nombre;
    private Boolean activo;

}
