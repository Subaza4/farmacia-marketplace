package com.example.marketplace.controller;

import com.example.marketplace.dto.RegistroUsuarioRequest;
import com.example.marketplace.model.Usuario;
import com.example.marketplace.model.utils.Rol;
import com.example.marketplace.services.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Comentar luego de usar para registra el primer usuario administrador
//    @PostMapping("/registrar-admin-libre")
//    public ResponseEntity<Usuario> registrarAdminLibre(@RequestBody RegistroUsuarioRequest request) {
//        Usuario nuevo = new Usuario();
//        nuevo.setNombre(request.getNombre());
//        nuevo.setCorreo(request.getCorreo());
//        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
//        nuevo.setRol(Rol.ADMIN); // 👈 asigna rol ADMIN
//
//        return ResponseEntity.ok(usuarioService.guardar(nuevo));
//    }

    //Registro de nuevos clientes
    //@PostMapping("/registrar-cliente")
//    public ResponseEntity<Usuario> registrarCliente(@RequestBody RegistroUsuarioRequest request) {
//        Usuario nuevo = new Usuario();
//        nuevo.setNombre(request.getNombre());
//        nuevo.setCorreo(request.getCorreo());
//        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
//        nuevo.setRol(Rol.CLIENTE);
//        return ResponseEntity.ok(usuarioService.guardar(nuevo));
//    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener usuario por correo (útil para login o verificación)
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> obtenerPorCorreo(@RequestParam String correo) {
        return usuarioService.obtenerPorCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario ADMIN
    @PostMapping("/registrar-admin")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Usuario> registrarAdmin(@RequestBody RegistroUsuarioRequest request) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevo.setRol(Rol.ADMIN);  // 👈 solo ADMIN puede crear este rol

        return ResponseEntity.ok(usuarioService.guardar(nuevo));
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar usuario
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}