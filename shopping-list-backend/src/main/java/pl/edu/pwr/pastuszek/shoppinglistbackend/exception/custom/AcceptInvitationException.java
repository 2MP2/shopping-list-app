package pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom;

public class AcceptInvitationException extends RuntimeException{
    public AcceptInvitationException() {
        super("Couldn't accept invitation");
    }
}
