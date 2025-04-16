package co.edu.univalle.authmicroservice.domain.exceptions;

public abstract class AuthException extends RuntimeException {
    public AuthException(String message) { super(message); }
}

