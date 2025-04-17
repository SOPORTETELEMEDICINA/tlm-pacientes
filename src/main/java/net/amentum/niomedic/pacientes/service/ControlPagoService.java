package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.ControlPagosException;
import net.amentum.niomedic.pacientes.views.ControlPagosView;

public interface ControlPagoService {

    void addFolioVenta(ControlPagosView view) throws ControlPagosException;

    void atenderFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException;

    void cancelarFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException;

    void finalizarFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException;

    ControlPagosView getFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException;
}
