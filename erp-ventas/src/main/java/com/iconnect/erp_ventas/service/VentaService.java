package com.iconnect.erp_ventas.service;

import com.iconnect.erp_ventas.domain.ventas.DetalleVenta;
import com.iconnect.erp_ventas.domain.ventas.Venta;
import com.iconnect.erp_ventas.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Transactional
    public Venta save(Venta venta){

        if(venta.getDetalles().isEmpty()){
            throw new RuntimeException("No puedes guardar una venta vacía.");
        }

        venta.calcularTotal();

        for (DetalleVenta detalle : venta.getDetalles()) {
            detalle.setVenta(venta);
        }

        return ventaRepository.save(venta);
    }

}
