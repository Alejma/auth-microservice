package co.edu.univalle.authmicroservice.domain.exceptions;

// InvalidTokenException.java
public class InvalidTokenException extends AuthException {
    public InvalidTokenException(String refreshTokenInválidoOExpirado) { super("Token JWT inválido o expirado"); }
}
