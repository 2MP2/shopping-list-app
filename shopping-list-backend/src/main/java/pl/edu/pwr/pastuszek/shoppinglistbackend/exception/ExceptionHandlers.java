package pl.edu.pwr.pastuszek.shoppinglistbackend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom.InvitationException;
import pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom.RegistrationException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);
    private static final String ERROR_KEY = "cause";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.error(errorMessage, fieldName);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegistrationException.class)
    public Map<String, String> handleRegistrationExceptions(RegistrationException e) {
        logger.error(e.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredJwtException.class)
    public Map<String, String> handleExpiredJwtExceptions(ExpiredJwtException e) {
        logger.error(e.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvitationException.class)
    public Map<String, String> handleAcceptInvitationExceptions(InvitationException e) {
        logger.error(e.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, String> handleAuthenticationExceptions(AuthenticationException e) {
        logger.error(e.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }

}
