package com.example.marketplace.controller;

import com.example.marketplace.dto.RegistroUsuarioRequest;
import com.example.marketplace.model.Usuario;
import com.example.marketplace.model.utils.JwtUtil;
import com.example.marketplace.model.utils.Rol;
import com.example.marketplace.services.impl.UserDetailsServiceImpl;
import com.example.marketplace.services.service.UsuarioService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login-cliente")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getCorreo());
        String token = jwtUtil.generarToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registra-cliente")
    public ResponseEntity<Usuario> registrarCliente(@RequestBody RegistroUsuarioRequest request) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevo.setRol(Rol.CLIENTE);
        return ResponseEntity.ok(usuarioService.guardar(nuevo));
    }

    @PostMapping("/registrar-admin-libre")
    public ResponseEntity<Usuario> registrarAdminLibre(@RequestBody RegistroUsuarioRequest request) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevo.setRol(Rol.ADMIN); // 👈 asigna rol ADMIN

        return ResponseEntity.ok(usuarioService.guardar(nuevo));
    }

    @Data
    public static class AuthRequest {
        private String correo;
        private String contrasena;
    }
}