package co.edu.univalle.unit_tests;

import co.edu.univalle.integration_tests.application.dto.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void cuandoSeCreaAuthResponse_entoncesLosCamposSeEstablecenCorrectamente() {
        String token = "token-de-prueba";
        String tokenDeRefresco = "token-de-refresco-de-prueba";

        AuthResponse respuesta = new AuthResponse(token, tokenDeRefresco);

        assertEquals(token, respuesta.token());
        assertEquals(tokenDeRefresco, respuesta.refreshToken());
    }

    @Test
    void cuandoSeCreaAuthResponseConValoresNulos_entoncesNoLanzaExcepcion() {
        AuthResponse respuesta = new AuthResponse(null, null);

        assertNull(respuesta.token());
        assertNull(respuesta.refreshToken());
    }

    @Test
    void cuandoSeComparanObjetosIguales_entoncesSonIguales() {
        AuthResponse respuesta1 = new AuthResponse("token", "refresh");
        AuthResponse respuesta2 = new AuthResponse("token", "refresh");

        assertEquals(respuesta1, respuesta2);
        assertEquals(respuesta1.hashCode(), respuesta2.hashCode());
    }

    @Test
    void cuandoSeComparanObjetosDiferentes_entoncesNoSonIguales() {
        AuthResponse respuesta1 = new AuthResponse("token1", "refresh1");
        AuthResponse respuesta2 = new AuthResponse("token2", "refresh2");

        assertNotEquals(respuesta1, respuesta2);
    }
}