package com.iconnect.erp_ventas.tests;

import com.iconnect.erp_ventas.domain.inventario.Producto;
import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import com.iconnect.erp_ventas.domain.ventas.DetalleVenta;
import com.iconnect.erp_ventas.domain.ventas.Venta;
import com.iconnect.erp_ventas.repository.ProductoRepository;
import com.iconnect.erp_ventas.repository.VentaRepository;
import com.iconnect.erp_ventas.service.VentaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NuevaVentaTest {

    @Mock // Crea un repositorio falso
    private VentaRepository ventaRepository;

    @Mock // Crea otro repositorio falso
    private ProductoRepository productoRepository;

    @InjectMocks // Inyecta los falsos dentro del servicio real
    private VentaService ventaService;

    @Test
    @DisplayName("Debe calcular el total y guardar la venta correctamente")
    void guardarVentaExitosamente() {

        // Creamos un producto
        Producto iphone = Producto.builder()
                .id(1L)
                .modelo("16 Pro")
                .variante("256GB")
                .build();

        // Creamos un detalle
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(iphone);
        detalle.setCantidad(2); // 2 unidades
        detalle.setPrecioUnitario(new BigDecimal(1000));

        // Creamos un Cliente
        Cliente cliente = Cliente.builder()
                .nombre("Juli")
                .apellido("Cao")
                .build();

        // Creamos una Venta
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setDetalles(new ArrayList<>(List.of(detalle))); // Lista mutable

        ventaService.save(venta);

        // A. Verificamos que se calculó el total (2 * 1000 = 2000)
        assertEquals(new BigDecimal("2000"), venta.getTotal());

        // B. Verificamos que el detalle se vinculó al padre
        assertNotNull(venta.getDetalles().getFirst().getVenta(), "El detalle debe tener asignada la venta padre");

        // C. Verificamos que se llamó al repositorio 1 vez
        verify(ventaRepository, times(1)).save(venta);
    }

    @Test
    @DisplayName("Debe fallar si la venta no tiene productos")
    void fallarSiVentaVacia() {
        Venta ventaVacia = new Venta();
        ventaVacia.setDetalles(new ArrayList<>()); // Lista vacía

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ventaService.save(ventaVacia);
        });

        assertEquals("No puedes guardar una venta vacía.", exception.getMessage());

        verify(ventaRepository, never()).save(any());
    }

}
