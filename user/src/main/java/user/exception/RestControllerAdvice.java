package user.exception;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.lang.Error;

@ControllerAdvice
public class RestControllerAdvice {

/*    @ApiResponse(responseCode = "500", useReturnTypeSchema = true)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<java.lang.Error> defaultHandler(Exception ex) {
        return new java.lang.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.SERVICE_UNAVAILABLE)
                .asResponseEntity();
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<java.lang.Error> internalException(InternalException ex) {
        return new java.lang.Error()
                .asResponseEntity();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<java.lang.Error> jwtException(JwtException ex) {
        return new java.lang.Error(HttpStatus.BAD_REQUEST.value(), ErrorCode.TOKEN_INCORRECT_FORMAT)
                .asResponseEntity();
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            WebExchangeBindException.class,
            ServerWebInputException.class
    })
    public ResponseEntity<java.lang.Error> validationExceptions(Exception ex) {
        return new java.lang.Error(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_REQUEST)
                .asResponseEntity();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<java.lang.Error> authorizationDeniedException(AccessDeniedException ex) {
        return new Error(HttpStatus.FORBIDDEN.value(), ErrorCode.FORBIDDEN)
                .asResponseEntity();
    }*/
}