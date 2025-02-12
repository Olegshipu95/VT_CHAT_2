package user.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountWasNotInsertExceptionTest {

    @Test
    void testConstructorWithUUID() {
        // Создаем UUID для теста
        UUID testId = UUID.randomUUID();

        // Создаем исключение
        UserAccountWasNotInsertException exception = new UserAccountWasNotInsertException(testId);

        // Проверяем, что сообщение об ошибке корректно
        String expectedMessage = "User Account was not updated=" + testId;
        assertEquals(expectedMessage, exception.getMessage());

        // Проверяем, что исключение является экземпляром NotFoundException
        assertTrue(exception instanceof NotFoundException);
    }

    @Test
    void testConstructorWithNullUUID() {
        // Создаем исключение с null UUID
        UserAccountWasNotInsertException exception = new UserAccountWasNotInsertException(null);

        // Проверяем, что сообщение об ошибке корректно
        String expectedMessage = "User Account was not updated=null";
        assertEquals(expectedMessage, exception.getMessage());

        // Проверяем, что исключение является экземпляром NotFoundException
        assertTrue(exception instanceof NotFoundException);
    }
}