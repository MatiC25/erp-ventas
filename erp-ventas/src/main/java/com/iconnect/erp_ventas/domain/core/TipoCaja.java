package com.iconnect.erp_ventas.domain.core;

public enum TipoCaja {
    EFECTIVO("Efectivo"),
    BANCO("Banco"),
    BILLETERA_VIRTUAL("Billetera Virtual"),;

    private String nombre;
    TipoCaja(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() { return nombre; }
}
