package pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("No authorization to perform this action.");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
