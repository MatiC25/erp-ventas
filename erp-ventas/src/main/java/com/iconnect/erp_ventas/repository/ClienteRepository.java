package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Para buscar rápido mientras escriben
    Optional<Cliente> findByNombre(String nombre);
}