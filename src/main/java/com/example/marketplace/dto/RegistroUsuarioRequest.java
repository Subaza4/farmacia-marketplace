package com.example.marketplace.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroUsuarioRequest {
    private String nombre;
    @NotNull(message = "El correo no puede ser nulo")
    private String correo;
    @NotNull(message = "La contraseña no puede estar en blanco")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String contrasena;
    private String telefono;
    private String direccion;
}