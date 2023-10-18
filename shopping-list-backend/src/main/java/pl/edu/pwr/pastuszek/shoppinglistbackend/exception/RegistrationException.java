package pl.edu.pwr.pastuszek.shoppinglistbackend.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException() {
        super("Problem with registration");
    }

    public RegistrationException(String message){
        super(message);
    }
}
