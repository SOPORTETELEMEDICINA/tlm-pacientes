package net.amentum.niomedic.pacientes.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.pacientes.exception.ControlPagosException;
import net.amentum.niomedic.pacientes.service.ControlPagoService;
import net.amentum.niomedic.pacientes.views.ControlPagosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("control_pagos")
public class ControlPagosRest extends BaseController {
    final Logger logger = LoggerFactory.getLogger(ControlPagosRest.class);

    @Autowired
    ControlPagoService controlPagoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFolioVenta(@RequestBody @Valid ControlPagosView view) throws ControlPagosException {
        logger.info("Agregando folio de venta: {}", view);
        controlPagoService.addFolioVenta(view);
    }

    @PutMapping("/atender")
    public void atenderFolioVenta(@RequestParam String folioVenta, @RequestParam Long numeroSucursal) throws ControlPagosException {
        controlPagoService.atenderFolioVenta(folioVenta, numeroSucursal);
    }

    @PutMapping("/cancelar")
    public void cancelarFolioVenta(@RequestParam String folioVenta, @RequestParam Long numeroSucursal) throws ControlPagosException {
        controlPagoService.cancelarFolioVenta(folioVenta, numeroSucursal);
    }

    @PutMapping("/finalizar")
    public void finalizarFolioVenta(@RequestParam String folioVenta, @RequestParam Long numeroSucursal) throws ControlPagosException {
        controlPagoService.finalizarFolioVenta(folioVenta, numeroSucursal);
    }

    @GetMapping
    public ControlPagosView getFolioVenta(@RequestParam String folioVenta, @RequestParam Long numeroSucursal) throws ControlPagosException {
        return controlPagoService.getFolioVenta(folioVenta, numeroSucursal);
    }

}
