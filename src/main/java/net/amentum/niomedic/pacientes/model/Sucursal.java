package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursales")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Override
    public String toString() {
        return "Sucursal{" +
                "idSucursal='" + idSucursal + '\'' +
                ", nombre=" + nombre +
                '}';
    }
}
