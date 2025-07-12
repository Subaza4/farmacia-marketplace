package com.example.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroUsuarioRequest {

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El correo no puede estar en blanco")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "El teléfono debe contener solo números y puede incluir el prefijo internacional"
    )
    private String telefono;

    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String direccion;
}
