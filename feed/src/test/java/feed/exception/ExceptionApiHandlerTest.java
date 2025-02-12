package feed.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExceptionApiHandlerTest {

    @InjectMocks
    private ExceptionApiHandler exceptionApiHandler;

    @Test
    void testHandleUserAccountNotFound() {
        UserAccountNotFoundException ex = new UserAccountNotFoundException(UUID.randomUUID());
        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleUserAccountNotFound(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ex, response.getBody().getPayload());
    }

    @Test
    void testHandleUserAccountWasNotInserted() {
        UserAccountWasNotInsertException ex = new UserAccountWasNotInsertException(UUID.randomUUID());
        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleUserAccountWasNotInserted(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ex, response.getBody().getPayload());
    }

    @Test
    void testHandleInvalidDataAccessApiUsageException() {
        InvalidDataAccessApiUsageException ex = new InvalidDataAccessApiUsageException("Invalid ID");
        ResponseEntity<String> response = exceptionApiHandler.handleInvalidDataAccessApiUsageException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The provided ID is null or invalid: Invalid ID", response.getBody());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<String> response = exceptionApiHandler.handleIllegalArgumentException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument: Invalid argument", response.getBody());
    }
}