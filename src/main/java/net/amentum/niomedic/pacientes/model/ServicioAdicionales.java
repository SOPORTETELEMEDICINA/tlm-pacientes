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
@Table(name = "servicio_adicionales")
public class ServicioAdicionales implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idServicioAdicionales;
    private String tipoServicio;
    private String nombre;
    private String domicilio;
    private String comentarios;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
    private Paciente paciente;

    @Override
    public String toString() {
        return "ServicioAdicionales{" +
                "idServicioAdicionales='" + idServicioAdicionales + '\'' +
                ", tipoServicio='" + tipoServicio + '\'' +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
