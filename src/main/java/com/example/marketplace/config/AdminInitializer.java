package com.example.marketplace.config;

import com.example.marketplace.model.Usuario;
import com.example.marketplace.model.utils.Rol;
import com.example.marketplace.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByCorreo("admin@mail.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setCorreo("admin@mail.com");
            admin.setContrasena(passwordEncoder.encode("123"));
            admin.setRol(Rol.ADMIN);

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario ADMIN creado automáticamente");
        }
    }
}
