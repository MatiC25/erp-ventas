package com.iconnect.erp_ventas.views.ventas;

import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import com.iconnect.erp_ventas.repository.ClienteRepository; // <--- Importante
import com.iconnect.erp_ventas.service.ClienteService;
import com.iconnect.erp_ventas.service.ProductoService;
import com.iconnect.erp_ventas.service.VentaService;
import com.iconnect.erp_ventas.views.MainLayout;
import com.iconnect.erp_ventas.views.ventas.components.DatosCliente;
import com.iconnect.erp_ventas.views.ventas.components.DatosProducto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "nueva-venta", layout = MainLayout.class)
public class NuevaVentaView extends VerticalLayout {

    // Servicios necesarios
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    // --- NUESTROS COMPONENTES HIJOS ---
    // Aquí irán apareciendo los demás (Carrito, Pagos, etc.) a medida que los limpies.
    private DatosCliente datosCliente;
    private DatosProducto datosProducto;

    public NuevaVentaView(VentaService ventaService,
                          ClienteService clienteService,
                            ProductoService productoService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.productoService = productoService;

        setSizeFull();

        this.datosCliente = new DatosCliente(clienteService);
        this.datosProducto = new DatosProducto(productoService);


        buildLayout();
    }

    private void buildLayout() {
        // Título
        add(new H2("Nueva Venta"));

        // Separador visual (Línea horizontal)
        add(new Hr());

        // AQUI ESTÁ TU FORMULARIO LIMPIO ✨
        add(datosCliente, datosProducto);

        // Separador para cuando pongas lo siguiente
        add(new Hr());

        // Botón temporal solo para que veas que funciona la integración
        Button btnTest = new Button("Test: Obtener Cliente", VaadinIcon.CHECK.create());
        btnTest.addClickListener(e -> probarIntegracion());
        add(btnTest);
    }

    private void probarIntegracion() {
        try {
            // Le pedimos al componente hijo que nos de el cliente validado
            var cliente = datosCliente.obtenerClienteParaVenta();
            Cliente clienteGuardado = clienteService.save(cliente);

            Notification.show("¡Funciona! Cliente listo: " + clienteGuardado.getNombre() + " " + clienteGuardado.getApellido());

        } catch (Exception e) {
            Notification.show("Error de validación: " + e.getMessage());
        }
    }
}