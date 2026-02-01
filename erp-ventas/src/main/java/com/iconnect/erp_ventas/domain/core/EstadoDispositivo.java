package com.iconnect.erp_ventas.domain.core;

public enum EstadoDispositivo {
    NUEVO("NUEVO"),
    USADO("USADO"),
    EN_GARANTIA("En Garantia"),;

    private String nombre;
    EstadoDispositivo(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

}
