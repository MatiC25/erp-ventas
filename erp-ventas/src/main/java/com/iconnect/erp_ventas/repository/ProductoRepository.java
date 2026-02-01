package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.inventario.Modelo;
import com.iconnect.erp_ventas.domain.inventario.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar por código de barras (para la pistola lectora)
    Optional<Producto> findByUPC(String codigoBarras);

    List<Producto> findByModelo(Modelo modelo);

    @Query("SELECT DISTINCT p.variante FROM Producto p WHERE p.modelo = :modelo ORDER BY p.variante")
    List<String> findVariantesByModelo(@Param("modelo") Modelo modelo);

    @Query("SELECT DISTINCT p.color FROM Producto p WHERE p.modelo = :modelo AND p.variante = :variante ORDER BY p.color")
    List<String> findColoresByModeloAndVariante(@Param("modelo") Modelo modelo, @Param("variante") String variante);

    Optional<Producto> findByModeloAndVarianteAndColor(Modelo modelo, String variante, String color);

}