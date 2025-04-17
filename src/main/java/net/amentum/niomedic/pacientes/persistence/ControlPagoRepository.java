package net.amentum.niomedic.pacientes.persistence;

import net.amentum.niomedic.pacientes.model.ControlPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ControlPagoRepository extends JpaRepository<ControlPago, String> {
    Optional<ControlPago> findByFolioVentaAndNumeroSucursal(String folioVenta, Long numeroSucursal);
}
