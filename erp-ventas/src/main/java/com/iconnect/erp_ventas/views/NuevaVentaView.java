package com.iconnect.erp_ventas.views;


import com.iconnect.erp_ventas.domain.inventario.Producto;
import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import com.iconnect.erp_ventas.domain.ventas.DetalleVenta;
import com.iconnect.erp_ventas.domain.ventas.Venta;
import com.iconnect.erp_ventas.service.ClienteService;
import com.iconnect.erp_ventas.service.ProductoService;
import com.iconnect.erp_ventas.service.VentaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.util.ArrayList;

@Route(value = "nueva-venta", layout = MainLayout.class)
public class NuevaVentaView extends VerticalLayout {

    private final VentaService ventaService;
    private final ProductoService productoService;
    private final ClienteService clienteService;

    private Venta ventaActual;

    private ComboBox<Cliente> clienteSelect;
    private ComboBox<Producto> productoSelect;
    private IntegerField cantidadInput;
    private BigDecimalField precioUnitarioInput;
    private Grid<DetalleVenta> gridDetalles;
    private BigDecimalField totalField;

    public NuevaVentaView(VentaService ventaService,
                          ProductoService productoService,
                          ClienteService clienteService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.clienteService = clienteService;

        setSizeFull();
        iniciarNuevaVenta();
        configureComponents();
        buildLayout();
    }

    private void iniciarNuevaVenta() {
        this.ventaActual = new Venta();
        this.ventaActual.setDetalles(new ArrayList<>());
    }

    private void configureComponents(){
        clienteSelect = new ComboBox<>("Cliente");
        clienteSelect.setItems(clienteService.findAll()); // Idealmente usarías un Service
        clienteSelect.setItemLabelGenerator(Cliente::getNombre);

        clienteSelect.setWidth("300px");

        // 2. Selector de Producto (Buscador)
        productoSelect = new ComboBox<>("Buscar Producto");
        productoSelect.setItems(productoService.findAll());
        productoSelect.setItemLabelGenerator(Producto::getDescripcion);
        productoSelect.setWidth("300px");

        cantidadInput = new IntegerField("Cant.");
        cantidadInput.setValue(1);
        cantidadInput.setMin(1);
        cantidadInput.setWidth("80px");

        precioUnitarioInput = new BigDecimalField("Precio Unitario");

        // 4. Grid (La Canasta)
        gridDetalles = new Grid<>(DetalleVenta.class, false);
        gridDetalles.addColumn(detalle -> detalle.getProducto().getDescripcion()).setHeader("Producto");
        gridDetalles.addColumn(DetalleVenta::getCantidad).setHeader("Cant.");
        gridDetalles.addColumn(DetalleVenta::getPrecioUnitario).setHeader("Precio U.");
        gridDetalles.addColumn(DetalleVenta::getSubtotal).setHeader("Subtotal");

        // Botón de eliminar renglón
        gridDetalles.addComponentColumn(detalle -> {
            Button btn = new Button(VaadinIcon.TRASH.create());
            btn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            btn.addClickListener(e -> {
                ventaActual.getDetalles().remove(detalle);
                refreshGrid();
            });
            return btn;
        });

        // 5. Total
        totalField = new BigDecimalField("Total a Pagar");
        totalField.setReadOnly(true);
        totalField.addThemeVariants(com.vaadin.flow.component.textfield.TextFieldVariant.LUMO_ALIGN_RIGHT);
    }

    private void buildLayout() {
        H2 titulo = new H2("Nueva Venta");
        HorizontalLayout header = new HorizontalLayout(titulo, totalField);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.BASELINE);

        // Barra de agregar producto
        Button btnAgregar = new Button("Agregar", VaadinIcon.PLUS.create());
        btnAgregar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAgregar.addClickListener(e -> agregarProducto());

        HorizontalLayout barraProducto = new HorizontalLayout(productoSelect, cantidadInput, btnAgregar);
        barraProducto.setAlignItems(Alignment.BASELINE);

        // Botón Finalizar
        Button btnFinalizar = new Button("Finalizar Venta", VaadinIcon.CHECK.create());
        btnFinalizar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        btnFinalizar.addClickListener(e -> guardarVenta());

        // Armar todo
        add(header, clienteSelect, barraProducto, gridDetalles, btnFinalizar);
    }

    private void agregarProducto() {
        Producto producto = productoSelect.getValue();
        Integer cantidad = cantidadInput.getValue();

        if (producto != null && cantidad != null && cantidad > 0) {
            // Crear el renglón
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);

            // FOTO DEL PRECIO: Usamos el precio actual del producto
            // Asegurate que tu entidad Producto tenga getPrecioVenta() y sea BigDecimal
            detalle.setPrecioUnitario(precioUnitarioInput.getValue());

            detalle.setVenta(ventaActual);

            // Agregar a la lista en memoria
            ventaActual.getDetalles().add(detalle);

            // Limpiar inputs
            productoSelect.clear();
            cantidadInput.setValue(1);
            productoSelect.focus(); // Volver el foco para seguir escaneando

            refreshGrid();
        }
    }

    private void refreshGrid() {
        // Recargar la tabla
        gridDetalles.setItems(ventaActual.getDetalles());

        // Recalcular Total
        ventaActual.calcularTotal();
        totalField.setValue(ventaActual.getTotal());
    }

    private void guardarVenta() {
        if (clienteSelect.getValue() == null) {
            Notification.show("Debes seleccionar un cliente");
            return;
        }

        try {
            ventaActual.setCliente(clienteSelect.getValue());
            ventaService.save(ventaActual);

            Notification.show("Venta registrada con éxito");

            // Resetear pantalla para la siguiente venta
            iniciarNuevaVenta();
            gridDetalles.setItems();
            clienteSelect.clear();
            totalField.setValue(BigDecimal.ZERO);

        } catch (Exception e) {
            Notification.show("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
