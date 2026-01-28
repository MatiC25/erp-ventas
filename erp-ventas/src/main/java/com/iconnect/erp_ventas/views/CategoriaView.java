package com.iconnect.erp_ventas.views;

import com.iconnect.erp_ventas.domain.Categoria;
import com.iconnect.erp_ventas.service.CategoriaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("categoria")
public class CategoriaView extends VerticalLayout {

    private final CategoriaService categoriaService;
    private final Grid<Categoria> grid = new Grid<>(Categoria.class);

    // Formulario
    private final TextField nombre = new TextField("Nombre");
    private final ComboBox<Categoria> tipo = new ComboBox<>("Tipo");
    private final ComboBox<String> tipoPrueba = new ComboBox<>("Tipo Prueba");
    private final Button save = new Button("Guardar");

    public CategoriaView(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;

        setSizeFull();
        configureForm();
        configureGrid();

        HorizontalLayout formulario = new HorizontalLayout(nombre, tipoPrueba, save);
        formulario.setAlignItems(Alignment.BASELINE);

        add(formulario, grid);

        actualizarLista();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("nombre", "tipoAsociado", "activa"); // Qué columnas mostrar
        grid.getColumnByKey("nombre").setHeader("Categoría");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureForm(){
        tipoPrueba.setItems("INGRESO", "EGRESO", "TRANSFERENCIA");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickListener(e -> {
            try {
                Categoria nueva = new Categoria();
                nueva.setNombre(nombre.getValue());
                nueva.setTipoAsociado(tipoPrueba.getValue());
                nueva.setActiva(true);

                categoriaService.save(nueva);

                actualizarLista();
                nombre.clear();
                tipo.clear();
                Notification.show("Categoría guardada con éxito");

            }
            catch (Exception ex) {
                Notification.show("Error al guardar: " + e.getSource());
            }
        });

    }

    private void actualizarLista() {
        grid.setItems(categoriaService.findAll());
    }

}
