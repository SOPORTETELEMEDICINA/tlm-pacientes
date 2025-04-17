package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.ControlPagosConverter;
import net.amentum.niomedic.pacientes.exception.ControlNotificacionException;
import net.amentum.niomedic.pacientes.exception.ControlPagosException;
import net.amentum.niomedic.pacientes.model.ControlPago;
import net.amentum.niomedic.pacientes.persistence.ControlPagoRepository;
import net.amentum.niomedic.pacientes.service.ControlPagoService;
import net.amentum.niomedic.pacientes.views.ControlPagosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ControlPagoServiceImpl implements ControlPagoService {
    private final Logger logger = LoggerFactory.getLogger(ControlPagoServiceImpl.class);

    @Autowired
    private ControlPagoRepository repository;

    @Autowired
    private ControlPagosConverter converter;

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void addFolioVenta(ControlPagosView view) throws ControlPagosException {
        try {
            ControlPago entity = converter.toEntity(view);
            repository.saveAndFlush(entity);
        } catch (Exception e) {
            ControlPagosException exception = new ControlPagosException(
                    "Error al insertar folio de venta",
                    ControlPagosException.LAYER_DAO,
                    ControlPagosException.ACTION_INSERT
            );
            exception.addError(e.getMessage());
            logger.error("Error al insertar folio venta: ", e);
            throw exception;
        }
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void atenderFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException {
        updateEstatusFolioVenta(folioVenta, numeroSucursal, 2); // 2 = Atendido
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void cancelarFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException {
        updateEstatusFolioVenta(folioVenta, numeroSucursal, 0); // 0 = Cancelado
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public void finalizarFolioVenta(String folioVenta, Long numeroSucursal) throws ControlPagosException {
        updateEstatusFolioVenta(folioVenta, numeroSucursal, 3); // 3 = Finalizado
    }

    @Transactional(rollbackFor = {ControlNotificacionException.class})
    @Override
    public ControlPagosView getFolioVenta(String folio, Long numeroSucursal) throws ControlPagosException {
        try {
            Optional<ControlPago> optional = repository.findByFolioVentaAndNumeroSucursal(folio, numeroSucursal);
            if (!optional.isPresent()) {
                throw new ControlPagosException("Folio no encontrado",
                        ControlPagosException.LAYER_SERVICE,
                        ControlPagosException.ACTION_VALIDATE);
            }
            return converter.toView(optional.get());
        } catch (ControlPagosException e) {
            throw e;
        } catch (Exception e) {
            ControlPagosException exception = new ControlPagosException("Error al obtener folio de venta",
                    ControlPagosException.LAYER_DAO, ControlPagosException.ACTION_INSERT);
            exception.addError(e.getLocalizedMessage());
            logger.error("Error al obtener folio venta: {}", e.getMessage());
            throw exception;
        }
    }

    private void updateEstatusFolioVenta(String folio, Long numeroSucursal, Integer nuevoEstatus) throws ControlPagosException {
        try {
            Optional<ControlPago> optional = repository.findByFolioVentaAndNumeroSucursal(folio, numeroSucursal);
            if (!optional.isPresent()) {
                throw new ControlPagosException("Folio no encontrado",
                        ControlPagosException.LAYER_SERVICE,
                        ControlPagosException.ACTION_VALIDATE);
            }
            ControlPago controlPago = optional.get();
            controlPago.setEstatusFolio(Long.valueOf(nuevoEstatus));
            repository.saveAndFlush(controlPago);
        } catch (ControlPagosException e) {
            throw e;
        } catch (Exception e) {
            ControlPagosException exception = new ControlPagosException("Error al actualizar folio de venta",
                    ControlPagosException.LAYER_DAO, ControlPagosException.ACTION_INSERT);
            exception.addError(e.getLocalizedMessage());
            logger.error("Error al actualizar folio venta: {}", e.getMessage());
            throw exception;
        }
    }
}
