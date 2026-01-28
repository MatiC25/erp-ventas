package com.iconnect.erp_ventas.domain.inventario;

import com.iconnect.erp_ventas.domain.core.EstadoDispositivo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="productos")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name="id_cate_dispositivo")
    private CategoriaDispositivo categoriaDispositivo;

    private String modelo;

    private String variante;

    private String color;

    //private String imagen;

    private Long UPC;

    @Enumerated(EnumType.STRING)
    private EstadoDispositivo estado;

}
