package com.iconnect.erp_ventas.views;


import com.iconnect.erp_ventas.views.ventas.NuevaVentaView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;

@Layout
public class MainLayout extends AppLayout {

    public MainLayout() {
        this.crearHeader();
        this.crearDrawer();
    }

    private void crearHeader(){
        H1 title = new H1("Ejemplo de TItulo");
        title.addClassNames("header", "text-lm", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle() ,title);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void crearDrawer(){
        SideNav sideNav = new SideNav();
        SideNav sideNav2 = new SideNav();

        sideNav.addItem(new SideNavItem("Categorías", CategoriaView.class, VaadinIcon.TAGS.create()));
        sideNav2.addItem(new SideNavItem("Nueva Venta", NuevaVentaView.class, VaadinIcon.CART.create()));

        addToDrawer(sideNav, sideNav2);
    }

}
