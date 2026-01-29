package com.iconnect.erp_ventas.views.ventas.components;

import com.iconnect.erp_ventas.domain.core.CanalVenta;
import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import com.iconnect.erp_ventas.service.ClienteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class DatosCliente extends VerticalLayout {

    private final ClienteService clienteService;

    private FormLayout form;
    private TextField nombre;
    private TextField apellido;
    private TextField email;
    private TextField contacto;
    private Select<CanalVenta> canalesSelect;

    private Binder<Cliente> binder = new Binder<>(Cliente.class);

    // Buscador de clientes viejos
    private HorizontalLayout buscadorLayout;
    private ComboBox<Cliente> buscadorCombo;
    private Button btnCambiarModo;
    private boolean modoNuevoCliente = true;


    public DatosCliente(ClienteService clienteService) {
        this.clienteService = clienteService;
        setPadding(false);
        setSpacing(false);

        InicializarForm();
        add(form);

    }

    private void InicializarForm() {
        form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        nombre = new TextField("Nombre");
        apellido = new TextField("Apellido");
        email = new TextField("Email");
        contacto = new TextField("Telefono / Whatsapp");

        canalesSelect = new Select<>("Canales");
        canalesSelect.setItems(CanalVenta.values());
        canalesSelect.setItemLabelGenerator(CanalVenta::getDescripcion);

        binder.forField(nombre).asRequired("El nombre es obligatorio").bind(Cliente::getNombre, Cliente::setNombre);
        binder.bind(apellido, Cliente::getApellido, Cliente::setApellido);
        binder.bind(email, Cliente::getEmail, Cliente::setEmail);
        binder.bind(contacto, Cliente::getTelefono, Cliente::setTelefono);

        form.add(nombre, apellido, email, contacto, canalesSelect);
        form.addClassNames(
                LumoUtility.Border.ALL,
                LumoUtility.BorderColor.CONTRAST_10,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.MEDIUM
        );
    }

    public CanalVenta getCanalVentaSeleccionado() {
        return canalesSelect.getValue();
    }

    public Cliente obtenerClienteParaVenta() throws ValidationException {
        Cliente cliente = new Cliente();

        // Asignamos al cliente creado
        binder.writeBean(cliente);

        return cliente;
    }

}
