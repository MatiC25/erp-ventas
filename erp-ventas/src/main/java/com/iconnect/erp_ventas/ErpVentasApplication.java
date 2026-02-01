package com.iconnect.erp_ventas;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
//@Theme("my-theme")
public class ErpVentasApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(ErpVentasApplication.class, args);
	}

}
