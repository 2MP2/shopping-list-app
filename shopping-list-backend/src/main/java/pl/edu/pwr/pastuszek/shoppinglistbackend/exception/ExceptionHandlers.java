package pl.edu.pwr.pastuszek.shoppinglistbackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom.InvitationException;
import pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom.RegistrationException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);
    private static final String ERROR_KEY = "cause";
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable cause = ex.getRootCause();
        if (cause instanceof SQLException sqlException) {
            String sqlState = sqlException.getSQLState();

            if ("23505".equals(sqlState)) { // PostgreSQL error code for unique constraint violation
                String errorMessage = sqlException.getMessage();
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }

        logger.error("DataIntegrityViolationException: " + ex.getMessage(), ex);
        return new ResponseEntity<>("Data integrity violation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeExceptions(RuntimeException e) {
        logger.error(e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }

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
        logger.error(e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvitationException.class)
    public Map<String, String> handleAcceptInvitationExceptions(InvitationException e) {
        logger.error(e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_KEY, e.getMessage());
        return errors;
    }
}
