package com.iconnect.erp_ventas.views.ventas.components.formularios;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.math.BigDecimal;

public class DatosTradeIn extends VerticalLayout {

    private Checkbox checkTradeIn;
    private VerticalLayout formularioContainer;

    private TextField categoriaField;
    private TextField modeloField;
    private TextField capacidadField;
    private TextField colorField;
    private TextField imeiField;
    private TextField estadoBateriaField;
    private TextField comentarioField;
    private BigDecimalField valorTasacionField;

    public DatosTradeIn() {

        setPadding(false);
        setSpacing(false);

        checkTradeIn = new Checkbox("Tomar dispositivo como parte de pago");
        checkTradeIn.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.TextColor.PRIMARY);

        initFormulario();

        // Lógica de visualización
        checkTradeIn.addValueChangeListener(e -> {
            formularioContainer.setVisible(e.getValue());
            if (!e.getValue()) limpiarFormulario();
        });

        // Estilo de tarjeta
        addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderColor.CONTRAST_10, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Padding.MEDIUM);

        add(checkTradeIn, formularioContainer);

    }

    private void initFormulario() {
        formularioContainer = new VerticalLayout();
        formularioContainer.setPadding(false);
        formularioContainer.setVisible(false);

        FormLayout form = new FormLayout();

        categoriaField = new TextField("Categoria");
        categoriaField.setPlaceholder("Ej: iPhone");

        modeloField = new TextField("Modelo");
        modeloField.setPlaceholder("Ej: 16 Pro");

        capacidadField = new TextField("Capacidad");
        capacidadField.setPlaceholder("Ej: 256GB");

        colorField = new TextField("Color");
        colorField.setPlaceholder("Ej: Blue");

        imeiField = new TextField("IMEI");
        imeiField.setPlaceholder("Escanea o escribe el IMEI");
        imeiField.setHelperText("Fundamental para verificar procedencia");

        estadoBateriaField = new TextField("Estado");
        estadoBateriaField.setPlaceholder("Ej: Usado 86% Bateria");

        comentarioField = new TextField("Comentarios");
        comentarioField.setPlaceholder("Ej: Notas Adicionales");

        valorTasacionField = new BigDecimalField("Valor de Toma ($)");
        valorTasacionField.setPlaceholder("Ej: 0.00");

        form.add(categoriaField,
                modeloField,
                capacidadField,
                colorField,
                imeiField,
                estadoBateriaField,
                comentarioField,
                valorTasacionField);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));

        formularioContainer.add(form);
    }

    private void limpiarFormulario() {
        categoriaField.clear();
        modeloField.clear();
        capacidadField.clear();
        colorField.clear();
        imeiField.clear();
        estadoBateriaField.clear();
        comentarioField.clear();
        valorTasacionField.clear();
    }

    public boolean hayTradeIn() { return checkTradeIn.getValue(); }
    public String getModelo() { return modeloField.getValue(); }
    public String getImei() { return imeiField.getValue(); }
    public String getEstado() { return estadoBateriaField.getValue(); }

    public BigDecimal getValorTasacion() {
        return valorTasacionField.getValue() != null ? valorTasacionField.getValue() : BigDecimal.ZERO;
    }




}
