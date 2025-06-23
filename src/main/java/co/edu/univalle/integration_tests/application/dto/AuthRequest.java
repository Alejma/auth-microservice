package co.edu.univalle.integration_tests.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Schema(
                description = "Correo electrónico del usuario",
                example = "usuario@univalle.edu.co",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank @Email String email,
        @Schema(
                description = "Contraseña del usuario",
                example = "password123",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 8
        )
        @NotBlank String password
) {}
