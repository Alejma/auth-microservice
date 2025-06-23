package co.edu.univalle.integration_tests.domain.exceptions;

// InvalidCredentialsException.java
public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException(String usuarioNoEncontrado) { super("Credenciales inválidas"); }
}
