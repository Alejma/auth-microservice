package co.edu.univalle.unit_tests;

import co.edu.univalle.integration_tests.application.dto.AuthRequest;
import co.edu.univalle.integration_tests.application.dto.AuthResponse;
import co.edu.univalle.integration_tests.application.dto.RefreshTokenRequest;
import co.edu.univalle.integration_tests.application.service.AuthService;
import co.edu.univalle.integration_tests.controllers.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService servicioAuth;

    @InjectMocks
    private AuthController controladorAuth;

    @BeforeEach
    void configuracionInicial() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cuandoLoginConCredencialesValidas_entoncesRetornaAuthResponse() {
        AuthRequest solicitud = new AuthRequest("usuario@univalle.edu.co", "contrasena123");
        AuthResponse respuestaEsperada = new AuthResponse("jwt-token", "token-de-refresco");

        when(servicioAuth.authenticate(any(AuthRequest.class))).thenReturn(respuestaEsperada);

        ResponseEntity<AuthResponse> respuesta = controladorAuth.login(solicitud);

        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(respuestaEsperada, respuesta.getBody());
    }

    @Test
    void cuandoRefreshTokenConTokenValido_entoncesRetornaNuevoAuthResponse() {
        RefreshTokenRequest solicitud = new RefreshTokenRequest("token-de-refresco-valido");
        AuthResponse respuestaEsperada = new AuthResponse("nuevo-jwt-token", "nuevo-token-de-refresco");

        when(servicioAuth.refreshToken(any(RefreshTokenRequest.class))).thenReturn(respuestaEsperada);

        ResponseEntity<AuthResponse> respuesta = controladorAuth.refreshToken(solicitud);

        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(respuestaEsperada, respuesta.getBody());
    }
}