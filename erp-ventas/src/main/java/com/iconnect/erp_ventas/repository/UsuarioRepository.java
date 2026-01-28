package com.iconnect.erp_ventas.repository;


import com.iconnect.erp_ventas.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Vital para el Login
    Optional<Usuario> findByUsername(String username);
}
