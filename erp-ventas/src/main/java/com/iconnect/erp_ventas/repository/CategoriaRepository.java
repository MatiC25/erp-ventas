package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.finanzas.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

}
