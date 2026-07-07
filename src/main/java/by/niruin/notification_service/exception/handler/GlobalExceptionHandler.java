package by.niruin.notification_service.exception.handler;

import by.niruin.notification_service.exception.JwtException;
import by.niruin.notification_service.exception.MappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import by.niruin.notification_service.model.error.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception) {
        log.warn("Jwt processing error: {}", exception.getMessage(), exception);

        var errorResponse = new ErrorResponse("Jwt error",
                "Jwt processing error",
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<ErrorResponse> handleMappingException(MappingException exception) {
        log.warn("Event mapping exception: {}", exception.getMessage(), exception);

        var errorResponse = new ErrorResponse("Event mapping error",
                "Incorrect event data",
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.warn("Unknown exception occurred: {}", exception.getMessage(), exception);

        var errorResponse = new ErrorResponse("Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
