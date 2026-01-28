package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.Categoria;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

}
