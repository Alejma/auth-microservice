package co.edu.univalle.authmicroservice.domain.exceptions;

// InvalidCredentialsException.java
public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException(String usuarioNoEncontrado) { super("Credenciales inválidas"); }
}
