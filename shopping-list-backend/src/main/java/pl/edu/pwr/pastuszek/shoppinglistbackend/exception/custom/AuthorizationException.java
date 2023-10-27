package pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
