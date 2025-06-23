package co.edu.univalle.unit_tests;

import co.edu.univalle.integration_tests.application.dto.AuthRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    private static Validator validador;

    @BeforeAll
    static void configuracionInicial() {
        ValidatorFactory fabrica = Validation.buildDefaultValidatorFactory();
        validador = fabrica.getValidator();
    }

    @Test
    void cuandoTodosLosCamposSonCorrectos_entoncesNoHayViolaciones() {
        AuthRequest solicitud = new AuthRequest("usuario@univalle.edu.co", "contrasena123");
        Set<ConstraintViolation<AuthRequest>> violaciones = validador.validate(solicitud);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    void cuandoElCorreoEstaVacio_entoncesHayUnaViolacion() {
        AuthRequest solicitud = new AuthRequest("", "contrasena123");
        Set<ConstraintViolation<AuthRequest>> violaciones = validador.validate(solicitud);
        assertEquals(1, violaciones.size());
        assertEquals("email", violaciones.iterator().next().getPropertyPath().toString());
    }

    @Test
    void cuandoElCorreoEsInvalido_entoncesHayUnaViolacion() {
        AuthRequest solicitud = new AuthRequest("correo-invalido", "contrasena123");
        Set<ConstraintViolation<AuthRequest>> violaciones = validador.validate(solicitud);
        assertEquals(1, violaciones.size());
        assertEquals("email", violaciones.iterator().next().getPropertyPath().toString());
    }

    @Test
    void cuandoLaContrasenaEstaVacia_entoncesHayUnaViolacion() {
        AuthRequest solicitud = new AuthRequest("usuario@univalle.edu.co", "");
        Set<ConstraintViolation<AuthRequest>> violaciones = validador.validate(solicitud);
        assertEquals(1, violaciones.size());
        assertEquals("password", violaciones.iterator().next().getPropertyPath().toString());
    }

    @Test
    void cuandoAmbosCamposSonInvalidos_entoncesHayDosViolaciones() {
        AuthRequest solicitud = new AuthRequest("", "");
        Set<ConstraintViolation<AuthRequest>> violaciones = validador.validate(solicitud);
        assertEquals(2, violaciones.size());
    }
}