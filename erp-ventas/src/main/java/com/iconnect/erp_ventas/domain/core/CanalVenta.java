package com.iconnect.erp_ventas.domain.core;

import lombok.Getter;

@Getter
public enum CanalVenta {
    CLIENTE("Cliente"),
    FACEBOOK("Facebook"),
    INSTAGRAM("Instagram"),
    CONTACTO("Contacto"),
    MAYORISTA("Mayorista"),
    RECOMENDACION("Recomendacion");

    private final String descripcion;
    CanalVenta(String descripcion) {
        this.descripcion = descripcion;
    }

}
