package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.inventario.CategoriaDispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaDispositivoRepository extends JpaRepository<CategoriaDispositivo, UUID> {
}
