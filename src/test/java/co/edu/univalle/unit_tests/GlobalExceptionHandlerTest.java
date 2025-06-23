package co.edu.univalle.unit_tests;

import co.edu.univalle.integration_tests.application.dto.ErrorResponse;
import co.edu.univalle.integration_tests.controllers.GlobalExceptionHandler;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void configuracionInicial() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void cuandoAuthException_entoncesRetornaErrorResponseConStatus401() {
        // Arrange
        String mensajeError = "Credenciales inválidas";
        AuthException authException = new AuthException(mensajeError);

        // Act
        ResponseEntity<ErrorResponse> respuesta = globalExceptionHandler.handleAuthException(authException);

        // Assert
        assertNotNull(respuesta);
        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
        assertEquals(401, respuesta.getStatusCodeValue());

        ErrorResponse errorResponse = respuesta.getBody();
        assertNotNull(errorResponse);
        assertEquals("AuthException", errorResponse.error());
        assertEquals(mensajeError, errorResponse.message());
        assertEquals(401, errorResponse.status());
        assertNull(errorResponse.details());
    }

    @Test
    void cuandoMethodArgumentNotValidException_entoncesRetornaErrorResponseConStatus400() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("authRequest", "email", "El email es requerido");
        FieldError fieldError2 = new FieldError("authRequest", "password", "La contraseña debe tener al menos 8 caracteres");
        
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));
        
        MethodArgumentNotValidException validationException = 
            new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<ErrorResponse> respuesta = globalExceptionHandler.handleValidationExceptions(validationException);

        // Assert
        assertNotNull(respuesta);
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals(400, respuesta.getStatusCodeValue());

        ErrorResponse errorResponse = respuesta.getBody();
        assertNotNull(errorResponse);
        assertEquals("ValidationError", errorResponse.error());
        assertEquals("Error de validación", errorResponse.message());
        assertEquals(400, errorResponse.status());

        List<String> detalles = errorResponse.details();
        assertNotNull(detalles);
        assertEquals(2, detalles.size());
        assertTrue(detalles.contains("email: El email es requerido"));
        assertTrue(detalles.contains("password: La contraseña debe tener al menos 8 caracteres"));
    }

    @Test
    void cuandoMethodArgumentNotValidExceptionSinErrores_entoncesRetornaErrorResponseVacio() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList());
        
        MethodArgumentNotValidException validationException = 
            new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<ErrorResponse> respuesta = globalExceptionHandler.handleValidationExceptions(validationException);

        // Assert
        assertNotNull(respuesta);
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        ErrorResponse errorResponse = respuesta.getBody();
        assertNotNull(errorResponse);
        assertEquals("ValidationError", errorResponse.error());
        assertEquals("Error de validación", errorResponse.message());
        assertEquals(400, errorResponse.status());

        List<String> detalles = errorResponse.details();
        assertNotNull(detalles);
        assertTrue(detalles.isEmpty());
    }


} 