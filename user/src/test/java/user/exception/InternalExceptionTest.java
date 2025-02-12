package user.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InternalExceptionTest {

    @Test
    void testDefaultConstructor() {
        // Создаем объект с конструктором по умолчанию
        InternalException exception = new InternalException();

        // Проверяем, что значения инициализированы корректно
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(ErrorCode.SERVICE_UNAVAILABLE, exception.getErrorCode());
    }

    @Test
    void testParameterizedConstructor() {
        // Создаем объект с пользовательскими значениями
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        ErrorCode expectedErrorCode = ErrorCode.INVALID_REQUEST;
        InternalException exception = new InternalException(expectedHttpStatus, expectedErrorCode);

        // Проверяем, что значения инициализированы корректно
        assertEquals(expectedHttpStatus, exception.getHttpStatus());
        assertEquals(expectedErrorCode, exception.getErrorCode());
    }

    @Test
    void testGetHttpStatus() {
        // Проверяем работу геттера для httpStatus
        HttpStatus expectedHttpStatus = HttpStatus.FORBIDDEN;
        InternalException exception = new InternalException(expectedHttpStatus, ErrorCode.FORBIDDEN);

        assertEquals(expectedHttpStatus, exception.getHttpStatus());
    }

    @Test
    void testGetErrorCode() {
        // Проверяем работу геттера для errorCode
        ErrorCode expectedErrorCode = ErrorCode.USER_NOT_FOUND;
        InternalException exception = new InternalException(HttpStatus.NOT_FOUND, expectedErrorCode);

        assertEquals(expectedErrorCode, exception.getErrorCode());
    }
}