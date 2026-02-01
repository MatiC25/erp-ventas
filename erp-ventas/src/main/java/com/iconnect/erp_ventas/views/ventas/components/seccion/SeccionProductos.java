package com.iconnect.erp_ventas.views.ventas.components;

import com.iconnect.erp_ventas.repository.ModeloRepository;
import com.iconnect.erp_ventas.service.ProductoService;
import com.iconnect.erp_ventas.views.ventas.components.formularios.ItemProducto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class SeccionProductos extends VerticalLayout {

    private final ProductoService productoService;
    private final ModeloRepository modeloRepository;
    private final VerticalLayout listaItemsLayout; // Aquí se apilan las "tarjetas"
    private int contadorItems = 1;

    public SeccionProductos(ProductoService productoService, ModeloRepository modeloRepository) {
        this.productoService = productoService;
        this.modeloRepository = modeloRepository;

        setPadding(false);
        setSpacing(false);

        // 1. Header con Título y Botón Agregar
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 titulo = new H3("Productos");
        titulo.addClassNames(LumoUtility.Margin.Vertical.MEDIUM);

        Button btnAgregar = new Button("Agregar", VaadinIcon.PLUS.create());
        btnAgregar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAgregar.addClickListener(e -> agregarNuevoItem());

        header.add(titulo, btnAgregar);

        // 2. Contenedor de la lista
        listaItemsLayout = new VerticalLayout();
        listaItemsLayout.setPadding(false);
        listaItemsLayout.setSpacing(true);

        add(header, listaItemsLayout);

        // Agregamos uno por defecto al iniciar para no ver vacío
        agregarNuevoItem();
    }

    private void agregarNuevoItem() {
        // Creamos la tarjeta y le pasamos la lógica para "auto-eliminarse"

        ItemProducto item = new ItemProducto(contadorItems++, productoService, this::eliminarItem, modeloRepository);
        listaItemsLayout.add(item);
    }

    private void eliminarItem(ItemProducto item) {
        contadorItems--;
        listaItemsLayout.remove(item);
    }


}