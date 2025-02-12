package feed.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountWasNotInsertExceptionTest {

    @Test
    void testConstructorWithUUID() {
        UUID testId = UUID.randomUUID();
        UserAccountWasNotInsertException exception = new UserAccountWasNotInsertException(testId);
        assertEquals("User Account was not updated=" + testId, exception.getMessage());
    }

    @Test
    void testConstructorWithNullUUID() {
        UserAccountWasNotInsertException exception = new UserAccountWasNotInsertException(null);
        assertEquals("User Account was not updated=null", exception.getMessage());
    }
}