package com.iconnect.erp_ventas.domain.core;

public enum Moneda {
    USD("USD"),
    ARS("ARS"),
    EUROS("EUROS"),
    UY("UY");

    private final String nombre;
    Moneda(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
}
