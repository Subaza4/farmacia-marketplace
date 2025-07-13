package com.example.marketplace.services.service;

import com.example.marketplace.model.Usuario;
import com.example.marketplace.model.utils.Rol;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listarTodos();

    Optional<Usuario> obtenerPorId(Long id);

    Optional<Usuario> obtenerPorCorreo(String correo);

    Usuario guardar(Usuario usuario);

    Usuario actualizar(Long id, Usuario usuarioActualizado);

    void eliminar(Long id);

    Rol getRolUser(String correo);
}