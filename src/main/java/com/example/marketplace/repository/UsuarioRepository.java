package com.example.marketplace.repository;

import com.example.marketplace.model.Usuario;
import com.example.marketplace.model.utils.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

    @Query("SELECT u.rol FROM Usuario u WHERE u.correo = :correo")
    Rol findRolByCorreo(String correo);
}