package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "control_pagos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_folio_sucursal", columnNames = {"folio_venta", "numero_sucursal"})
        })
public class ControlPago implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_transaccion", nullable = false)
    String idTransaccion;

    @Column(name = "folio_venta", nullable = false, length = 100)
    String folioVenta;

    @Column(name = "id_pac", nullable = false)
    String idPac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pac", referencedColumnName = "id_paciente",
            foreignKey = @ForeignKey(name = "fk_control_pagos_paciente"),
            insertable = false, updatable = false, nullable = false)
    Paciente paciente;

    @CreationTimestamp
    @Column(name = "pago_fecha_datetime", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date pagoFechaDatetime;

    @Column(name = "pago_importe", nullable = false, precision = 10, scale = 2)
    BigDecimal pagoImporte;

    @Column(name = "pago_tipo", nullable = false)
    Long pagoTipo;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date fechaCreacion;

    @Column(name = "estatus_folio", insertable = false)
    Long estatusFolio;

    @Column(name = "numero_sucursal", nullable = false)
    Long numeroSucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_sucursal", referencedColumnName = "id_sucursal",
            foreignKey = @ForeignKey(name = "fk_control_pagos_sucursal"),
            insertable = false, updatable = false, nullable = false)
    Sucursal sucursal;

    @Override
    public String toString() {
        return "ControlPago{" +
                "idTransaccion='" + idTransaccion + '\'' +
                ", folioVenta='" + folioVenta + '\'' +
                ", idPac='" + idPac + '\'' +
                ", pagoFechaDatetime=" + pagoFechaDatetime +
                ", pagoImporte=" + pagoImporte +
                ", pagoTipo=" + pagoTipo +
                ", fechaCreacion=" + fechaCreacion +
                ", estatusFolio=" + estatusFolio +
                ", numeroSucursal=" + numeroSucursal +
                '}';
    }

}
