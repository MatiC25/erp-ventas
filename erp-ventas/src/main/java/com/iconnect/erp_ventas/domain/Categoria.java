package com.iconnect.erp_ventas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Entity
@Table(name = "categoria")
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String nombre;

    @NotNull
    private String tipoAsociado; // INGRESO, EGRESO, TRANSFERENCIA

    private boolean activa = true;

    private String colorHex = "#94a3b8";
}
