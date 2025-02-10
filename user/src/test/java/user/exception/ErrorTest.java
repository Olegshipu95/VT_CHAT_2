package user.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ErrorTest {

    @Test
    void testErrorInitialization() {
        // Создаем объект Error
        int expectedStatus = 404;
        ErrorCode expectedCode = ErrorCode.USER_NOT_FOUND;
        Error error = new Error(expectedStatus, expectedCode);

        // Проверяем, что поля инициализированы корректно
        assertNotNull(error.getErrorId());
        assertFalse(error.getErrorId().isEmpty());
        assertEquals(expectedStatus, error.getStatus());
        assertEquals(expectedCode, error.getCode());
    }

    @Test
    void testAsResponseEntity() {
        // Создаем объект Error
        int expectedStatus = 400;
        ErrorCode expectedCode = ErrorCode.INVALID_REQUEST;
        Error error = new Error(expectedStatus, expectedCode);

        // Получаем ResponseEntity
        ResponseEntity<Error> responseEntity = error.asResponseEntity();

        // Проверяем, что статус и тело ответа соответствуют ожидаемым
        assertEquals(HttpStatus.valueOf(expectedStatus), responseEntity.getStatusCode());
        assertEquals(error, responseEntity.getBody());
    }
}