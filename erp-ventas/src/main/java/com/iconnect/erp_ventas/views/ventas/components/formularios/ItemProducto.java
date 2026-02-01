package com.iconnect.erp_ventas.views.ventas.components.formularios;


import com.iconnect.erp_ventas.domain.core.EstadoDispositivo;
import com.iconnect.erp_ventas.domain.inventario.CategoriaDispositivo;
import com.iconnect.erp_ventas.domain.inventario.Modelo;
import com.iconnect.erp_ventas.domain.inventario.Producto;
import com.iconnect.erp_ventas.repository.ModeloRepository;
import com.iconnect.erp_ventas.service.ProductoService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

// Representa UNA fila/tarjeta de producto (como en tu imagen)
public class ItemProducto extends Div {

    private final ProductoService productoService;
    private final ModeloRepository modeloRepository;


    private final int numeroItem;
    private final Consumer<ItemProducto> onDelete; // Para avisar que me quiero borrar

    // Seleccionables
    private ComboBox<CategoriaDispositivo> categoriaSelect; // Simplificado String para el ejemplo
    private ComboBox<Modelo> modeloSelect;  // Aquí cargas los productos
    private ComboBox<String> varianteSelect; // Capacidad
    private ComboBox<String> colorSelect;

    private Producto productoDetectado;

    // Campos completables
    private ComboBox<EstadoDispositivo> estadoSelect;
    private IntegerField cantidadField;
    private TextField imeiField;
    private BigDecimalField precioInput;
    private BigDecimalField costoInput;

    public ItemProducto(int numeroItem,
                        ProductoService service,
                        Consumer<ItemProducto> onDelete,
                        ModeloRepository modeloRepository) {
        this.numeroItem = numeroItem;
        this.productoService = service;
        this.onDelete = onDelete;
        this.modeloRepository = modeloRepository;

        addClassNames(
                "relative",
                "transition-all", "duration-300",
                "rounded-xl",
                "border", "border-slate-800", // border-border/40 approx
                "p-6",
                "mb-6",
                "bg-slate-900/50" // Un fondo base sutil
        );

        // Construir la UI interna
        crearFormularioCascada();
        add(crearHeader(onDelete), crearFormulario());
    }

    private HorizontalLayout crearHeader(Consumer<ItemProducto> onDelete) {
        Span titulo = new Span("PRODUCTO " + numeroItem);
        titulo.addClassNames(
                "text-xs",
                "font-bold",
                "text-indigo-500",
                "uppercase",
                "tracking-wider"
        );

        Button btnEliminar = new Button(VaadinIcon.TRASH.create());
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        btnEliminar.addClassNames("rounded-full", "hover:bg-red-900/20");
        btnEliminar.addClickListener(e -> onDelete.accept(this)); // "Me suicido"

        HorizontalLayout header = new HorizontalLayout(titulo, btnEliminar);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.addClassNames("mb-4");
        return header;
    }

    private FormLayout crearFormulario(){
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
                // En celular (0px en adelante): 1 sola columna (todo vertical)
                new FormLayout.ResponsiveStep("0", 1),
                // En tablet (600px en adelante): 2 columnas
                new FormLayout.ResponsiveStep("600px", 2),
                // En PC (1000px en adelante): 4 columnas (Como tu diseño)
                new FormLayout.ResponsiveStep("1000px", 4)
        );

        //form.setColspan(imeiField, 2);

        form.setColspan(precioInput, 2);
        form.setColspan(costoInput, 2);
        form.add(
                categoriaSelect,
                modeloSelect,
                varianteSelect,
                colorSelect,
                cantidadField,
                estadoSelect,
                imeiField,
                precioInput,
                costoInput
        );

        return form;

    }

    private void crearFormularioCascada() {
        // 1. CATEGORÍA
        categoriaSelect = new ComboBox<>("Categoría");
        categoriaSelect.setItems(productoService.findAllCategoriaDispositivos());
        categoriaSelect.setItemLabelGenerator(CategoriaDispositivo::getNombre);

        // 2. MODELO (Se activa al elegir Categoría)
        modeloSelect = new ComboBox<>("Modelo");
        modeloSelect.setItemLabelGenerator(Modelo::getNombre);
        modeloSelect.setEnabled(false);

        categoriaSelect.addValueChangeListener(e -> {
            modeloSelect.clear();
            varianteSelect.clear();
            colorSelect.clear();
            if (e.getValue() != null) {
                // Buscamos modelos de esa categoría
                modeloSelect.setItems(modeloRepository.findBycategoriaDispositivo(e.getValue()));
                modeloSelect.setEnabled(true);
            } else {
                modeloSelect.setEnabled(false);
            }
        });

        // 3. VARIANTE (Se activa al elegir Modelo)
        varianteSelect = new ComboBox<>("Capacidad / Variante");
        varianteSelect.setEnabled(false);

        modeloSelect.addValueChangeListener(e -> {
            varianteSelect.clear();
            colorSelect.clear();
            if (e.getValue() != null) {
                // ¡MAGIA! Solo traemos las capacidades que existen para este modelo
                List<String> variantes = productoService.findVariantesByModelo(e.getValue());
                varianteSelect.setItems(variantes);
                varianteSelect.setEnabled(true);
            } else {
                varianteSelect.setEnabled(false);
            }
        });

        // 4. COLOR (Se activa al elegir Variante)
        colorSelect = new ComboBox<>("Color");
        colorSelect.setEnabled(false);

        varianteSelect.addValueChangeListener(e -> {
            colorSelect.clear();
            if (e.getValue() != null && modeloSelect.getValue() != null) {
                // ¡MAGIA 2! Solo colores disponibles para esa capacidad y modelo
                List<String> colores = productoService.findColoresByModeloAndVariante(
                        modeloSelect.getValue(),
                        e.getValue()
                );
                colorSelect.setItems(colores);
                colorSelect.setEnabled(true);
            } else {
                colorSelect.setEnabled(false);
            }
        });

        colorSelect.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                buscarProductoFinal();
            }
        });

        estadoSelect = new ComboBox<>("Estado");
        estadoSelect.setItems(EstadoDispositivo.values());
        estadoSelect.setValue(EstadoDispositivo.NUEVO);

        cantidadField = new IntegerField("Cantidad");
        cantidadField.setValue(1);

        precioInput = new BigDecimalField("Precio");
        precioInput.setPlaceholder("0.00");

        costoInput= new BigDecimalField("Costo");
        costoInput.setPlaceholder("0.00");

        imeiField = new TextField("IMEI");
        imeiField.setPlaceholder("Escanear...");
        imeiField.setPrefixComponent(VaadinIcon.SCISSORS.create());
        imeiField.addFocusListener(e -> abrirModalScanner());


        add(
                categoriaSelect,
                modeloSelect,
                varianteSelect,
                colorSelect,
                estadoSelect,
                cantidadField,
                imeiField,
                precioInput,
                costoInput);
    }

    private void buscarProductoFinal() {
        Optional<Producto> p = productoService.findByModeloAndVarianteAndColor(
                modeloSelect.getValue(),
                varianteSelect.getValue(),
                colorSelect.getValue()
        );

        if (p.isPresent()) {
            this.productoDetectado = p.get();
            Notification.show("Producto Encontrado: " + productoDetectado.getDescripcionCompleta());
        }

    }

    private void abrirModalScanner() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Escanear IMEI / Serial");

        // Input grande estilo terminal
        TextField inputScan = new TextField();
        inputScan.setPlaceholder("Esperando escaneo...");
        inputScan.addClassNames(
                "text-center", "text-lg", "font-mono", "tracking-wider", "w-full"
        );
        inputScan.setAutofocus(true);

        // Texto de ayuda
        Span helpText = new Span("Escanea con la pistola o presiona Enter");
        helpText.addClassNames("text-sm", "text-slate-400", "block", "text-center", "mb-4");

        // Botones
        Button btnConfirmar = new Button("Confirmar", e -> {
            this.imeiField.setValue(inputScan.getValue());
            dialog.close();
        });
        btnConfirmar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnCancelar = new Button("Cancelar", e -> dialog.close());

        // Layout del Modal
        VerticalLayout layout = new VerticalLayout(helpText, inputScan, new HorizontalLayout(btnCancelar, btnConfirmar));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Enter para confirmar
        inputScan.addKeyDownListener(Key.ENTER, e -> btnConfirmar.click());

        dialog.add(layout);
        dialog.open();
    }
}
