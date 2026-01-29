package com.iconnect.erp_ventas.views.ventas.components;

import com.iconnect.erp_ventas.domain.inventario.Producto;
import com.iconnect.erp_ventas.service.ProductoService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.math.BigDecimal;
import java.util.Optional;

public class DatosProducto extends VerticalLayout {

    private final ProductoService productoService;
    private TextField upcField;
    private TextField descripcionField;
    private TextField categoriaField;
    private TextField modeloField;
    private TextField varianteField;
    private TextField colorField;

    private IntegerField cantidadField;
    private BigDecimalField precioVentaInput;
    private BigDecimalField costoInput;

    private Producto productoSeleccionado;

    public DatosProducto(ProductoService productoService) {
        this.productoService = productoService;

        setPadding(false);
        setSpacing(false);

        InicializarForm();

        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        form.setColspan(upcField, 2);
        form.setColspan(descripcionField, 2);

        form.add(upcField,
                descripcionField,
                cantidadField,
                precioVentaInput,
                costoInput,
                categoriaField,
                modeloField,
                varianteField,
                colorField
        );

        // Envolvemos en un contenedor con borde para separar visualmente
        VerticalLayout card = new VerticalLayout(form);
        card.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderColor.CONTRAST_10, LumoUtility.BorderRadius.MEDIUM);

        add(card);

    }

    private void InicializarForm(){
        upcField = new TextField("UPC / Código de Barras");
        upcField.setPlaceholder("Escanea aquí...");
        upcField.setPrefixComponent(VaadinIcon.BARCODE.create());
        upcField.setAutofocus(true);

        upcField.addKeyDownListener(Key.ENTER, e -> buscarProductoPorUPC(upcField.getValue()));

//        upcField.addBlurListener(e -> {
//            if (!e.getValue().isEmpty()) buscarProductoPorUPC(e.getValue());
//        });

        descripcionField = new TextField("Producto Detectado");
        descripcionField.setReadOnly(true);
        descripcionField.setVisible(false);
        descripcionField.setPlaceholder("Esperando lectura...");

        cantidadField = new IntegerField("Cantidad");
        cantidadField.setValue(1);
        cantidadField.setMin(1);
        cantidadField.setStepButtonsVisible(true);

        precioVentaInput = new BigDecimalField("Precio Venta ($)");
        precioVentaInput.setPlaceholder("0.00");

        costoInput = new BigDecimalField("Costo ($)");
        costoInput.setPlaceholder("0.00");

        categoriaField = new TextField("Categoria");
        categoriaField.setPlaceholder("Categoria");
        categoriaField.setRequired(true);

        modeloField = new TextField("Modelo");
        modeloField.setPlaceholder("Modelo");

        varianteField = new TextField("Variante");
        varianteField.setPlaceholder("Variante");

        colorField = new TextField("Color");
        colorField.setPlaceholder("Color");
    }

    private void buscarProductoPorUPC(String UPC){
        productoService.buscarProductoPorUPC(UPC).ifPresentOrElse(
                p -> {
                    descripcionField.setVisible(true);
                    cargarProducto(p);
                    Notification.show("Producto encontrado: " + p.getUPC());
                },
                () -> {
                    limpiarCamposMenosUPC();
                    Notification.show("No se encontró ningún producto con ese código",
                            3000, Notification.Position.MIDDLE);
                    // TODO Agregar Nuevo Producto
                });

    }

    private void cargarProducto(Producto p){
        this.productoSeleccionado = p;
        descripcionField.setValue(p.getDescripcionCompleta());
        categoriaField.setValue(p.getCategoriaDispositivo().getNombre());
        modeloField.setValue(p.getModelo());
        varianteField.setValue(p.getVariante());
        colorField.setValue(p.getColor());

        precioVentaInput.focus();
    }

    private void limpiarCamposMenosUPC(){
        descripcionField.clear();
        categoriaField.clear();
        modeloField.clear();
        varianteField.clear();
        colorField.clear();
        cantidadField.setValue(1);
        productoSeleccionado = null;
    }

    // Getters para el formulario externo
    public Optional<Producto> getProductoSeleccionado() { return Optional.ofNullable(productoSeleccionado);}
    public Integer getCantidad() { return cantidadField.getValue(); }
    public BigDecimal getPrecioVenta() { return precioVentaInput.getValue(); }
    public BigDecimal getCosto() { return costoInput.getValue(); }
}
