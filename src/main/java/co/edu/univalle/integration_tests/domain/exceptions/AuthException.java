package co.edu.univalle.integration_tests.domain.exceptions;

public abstract class AuthException extends RuntimeException {
    public AuthException(String message) { super(message); }
}

