package com.iconnect.erp_ventas.views.ventas.components.formularios;

import com.iconnect.erp_ventas.DTOS.PagoDTO;
import com.iconnect.erp_ventas.domain.core.Moneda;
import com.iconnect.erp_ventas.domain.finanzas.Caja;
import com.iconnect.erp_ventas.service.CajaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DatosPago extends VerticalLayout {

    private final CajaService cajaService; // Necesitamos acceso a BD

    // Inputs
    private Select<Moneda> monedaSelect;
    private BigDecimalField montoInput;
    private ComboBox<Caja> cajaSelect;
    private Button btnAgregarPago;

    // Datos
    private List<PagoDTO> listaPagos = new ArrayList<>();
    private Grid<PagoDTO> gridPagos;

    public DatosPago(CajaService cajaService) {
        this.cajaService = cajaService;

        setPadding(false);
        setSpacing(false);

        // Header
        Span titulo = new Span("Método de Pago");
        titulo.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.FontSize.LARGE);

        // Armado
        VerticalLayout card = new VerticalLayout(
                titulo,
                crearBarraInputs(),
                crearGridPagos()
        );
        card.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderColor.CONTRAST_10, LumoUtility.BorderRadius.MEDIUM);
        add(card);
    }

    private HorizontalLayout crearBarraInputs() {
        // 1. SELECTOR DE MONEDA
        monedaSelect = new Select<>();
        monedaSelect.setLabel("Moneda");
        monedaSelect.setItems(Moneda.values());
        monedaSelect.setItemLabelGenerator(Moneda::getNombre);
        monedaSelect.setWidth("150px");

        // 2. INPUT MONTO
        montoInput = new BigDecimalField("Monto");
        montoInput.setPlaceholder("0.00");

        // 3. SELECTOR DE CAJA (Dinámico)
        cajaSelect = new ComboBox<>("Destino (Caja)");
        cajaSelect.setItemLabelGenerator(Caja::getNombreConSaldo);
        cajaSelect.setWidth("250px");
        cajaSelect.setPlaceholder("Selecciona moneda primero");

        // --- LÓGICA DE CASCADA ---
        monedaSelect.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                // Buscamos en BD solo las cajas de esa moneda
                List<Caja> cajasCompatibles = cajaService.findByMoneda(e.getValue());

                cajaSelect.setItems(cajasCompatibles);
                cajaSelect.setEnabled(true);
                cajaSelect.setPlaceholder("Elige caja...");

                if (!cajasCompatibles.isEmpty()) {
                    cajaSelect.setValue(cajasCompatibles.get(0));
                } else {
                    Notification.show("No tienes cajas creadas para " + e.getValue().name());
                }
            } else {
                cajaSelect.clear();
                cajaSelect.setEnabled(false);
            }
        });

        // 4. BOTÓN AGREGAR
        btnAgregarPago = new Button("Agregar", VaadinIcon.PLUS.create());
        btnAgregarPago.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAgregarPago.addClickListener(e -> agregarPago());

        // Alineación
        HorizontalLayout barra = new HorizontalLayout(monedaSelect, montoInput, cajaSelect, btnAgregarPago);
        barra.setAlignItems(Alignment.BASELINE);
        barra.setWidthFull();

        // Trigger inicial para cargar algo por defecto (ej: USD)
        monedaSelect.setValue(Moneda.USD);

        return barra;
    }

    private VerticalLayout crearGridPagos() {
        gridPagos = new Grid<>(PagoDTO.class, false);
        gridPagos.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
        gridPagos.setAllRowsVisible(true);

        gridPagos.addColumn(PagoDTO::getMonedaNombre).setHeader("Moneda");
        gridPagos.addColumn(PagoDTO::getMonto).setHeader("Monto");
        gridPagos.addColumn(dto -> dto.getCaja().getNombre()).setHeader("Caja Destino");

        gridPagos.addComponentColumn(pago -> {
            Button btn = new Button(VaadinIcon.CLOSE_SMALL.create());
            btn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            btn.addClickListener(e -> eliminarPago(pago));
            return btn;
        });

        return new VerticalLayout(gridPagos); // Wrap simple
    }

    private void agregarPago() {
        if (montoInput.getValue() == null ||
                montoInput.getValue().compareTo(BigDecimal.ZERO) <= 0 ||
                cajaSelect.getValue() == null) {
            Notification.show("Completa monto y caja");
            return;
        }

        PagoDTO pago = new PagoDTO(
                monedaSelect.getValue(),
                montoInput.getValue(),
                cajaSelect.getValue() // Guardamos el OBJETO Caja entero
        );

        listaPagos.add(pago);
        refreshGrid();

        montoInput.clear();
        montoInput.focus();
    }

    private void eliminarPago(PagoDTO pago) {
        listaPagos.remove(pago);
        refreshGrid();
    }

    private void refreshGrid() {
        gridPagos.setItems(listaPagos);
    }

    public List<PagoDTO> getPagosRealizados() {
        return listaPagos;
    }


}