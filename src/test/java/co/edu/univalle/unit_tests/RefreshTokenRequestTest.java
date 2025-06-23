package co.edu.univalle.unit_tests;

import co.edu.univalle.integration_tests.application.dto.RefreshTokenRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenRequestTest {

    private static Validator validador;

    @BeforeAll
    static void configuracionInicial() {
        try {
            ValidatorFactory fabrica = Validation.buildDefaultValidatorFactory();
            validador = fabrica.getValidator();
            System.out.println("Validador inicializado correctamente");
        } catch (Exception e) {
            fail("Error al inicializar el validador: " + e.getMessage());
        }
    }

    @Test
    void cuandoElTokenEsValido_entoncesNoHayViolaciones() {
        // Arrange
        RefreshTokenRequest solicitud = new RefreshTokenRequest("token-valido");

        // Act
        Set<ConstraintViolation<RefreshTokenRequest>> violaciones = validador.validate(solicitud);

        // Debug
        System.out.println("Violaciones encontradas (válido): " + violaciones.size());
        violaciones.forEach(v -> System.out.println(" - " + v.getPropertyPath() + ": " + v.getMessage()));

        // Assert
        assertTrue(violaciones.isEmpty(), "Debería no haber violaciones con token válido");
    }

}