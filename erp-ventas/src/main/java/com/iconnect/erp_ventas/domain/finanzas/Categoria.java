package com.iconnect.erp_ventas.domain.finanzas;

import com.iconnect.erp_ventas.domain.core.TipoOperacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "categorias")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoOperacion tipoAsociado; // INGRESO, EGRESO, TRANSFERENCIA

    private boolean activa = true;

    private String colorHex = "#94a3b8";
}
