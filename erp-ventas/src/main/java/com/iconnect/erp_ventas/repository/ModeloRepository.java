package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.inventario.CategoriaDispositivo;
import com.iconnect.erp_ventas.domain.inventario.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModeloRepository extends JpaRepository<Modelo, UUID> {
    Modelo findBycategoriaDispositivo(CategoriaDispositivo categoriaDispositivo);
}
