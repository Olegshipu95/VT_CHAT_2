package user.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {

    @Test
    void testEnumValues() {
        // Проверяем, что все значения перечисления корректны
        ErrorCode[] errorCodes = ErrorCode.values();
        assertEquals(10, errorCodes.length); // Количество элементов в перечислении

        // Проверяем порядок и наличие всех значений
        assertEquals(ErrorCode.INVALID_REQUEST, errorCodes[0]);
        assertEquals(ErrorCode.SERVICE_UNAVAILABLE, errorCodes[1]);
        assertEquals(ErrorCode.TOKEN_EXPIRED, errorCodes[2]);
        assertEquals(ErrorCode.TOKEN_DOES_NOT_EXIST, errorCodes[3]);
        assertEquals(ErrorCode.TOKEN_INCORRECT_FORMAT, errorCodes[4]);
        assertEquals(ErrorCode.FORBIDDEN, errorCodes[5]);
        assertEquals(ErrorCode.USER_NOT_FOUND, errorCodes[6]);
        assertEquals(ErrorCode.USER_ALREADY_EXIST, errorCodes[7]);
        assertEquals(ErrorCode.USER_PASSWORD_INCORRECT, errorCodes[8]);
        assertEquals(ErrorCode.ROLE_NOT_FOUND, errorCodes[9]);
    }

    @Test
    void testEnumValueOf() {
        // Проверяем, что метод valueOf корректно возвращает значения перечисления
        assertEquals(ErrorCode.INVALID_REQUEST, ErrorCode.valueOf("INVALID_REQUEST"));
        assertEquals(ErrorCode.SERVICE_UNAVAILABLE, ErrorCode.valueOf("SERVICE_UNAVAILABLE"));
        assertEquals(ErrorCode.TOKEN_EXPIRED, ErrorCode.valueOf("TOKEN_EXPIRED"));
        assertEquals(ErrorCode.TOKEN_DOES_NOT_EXIST, ErrorCode.valueOf("TOKEN_DOES_NOT_EXIST"));
        assertEquals(ErrorCode.TOKEN_INCORRECT_FORMAT, ErrorCode.valueOf("TOKEN_INCORRECT_FORMAT"));
        assertEquals(ErrorCode.FORBIDDEN, ErrorCode.valueOf("FORBIDDEN"));
        assertEquals(ErrorCode.USER_NOT_FOUND, ErrorCode.valueOf("USER_NOT_FOUND"));
        assertEquals(ErrorCode.USER_ALREADY_EXIST, ErrorCode.valueOf("USER_ALREADY_EXIST"));
        assertEquals(ErrorCode.USER_PASSWORD_INCORRECT, ErrorCode.valueOf("USER_PASSWORD_INCORRECT"));
        assertEquals(ErrorCode.ROLE_NOT_FOUND, ErrorCode.valueOf("ROLE_NOT_FOUND"));
    }

    @Test
    void testEnumOrdinal() {
        // Проверяем порядковый номер (ordinal) каждой константы
        assertEquals(0, ErrorCode.INVALID_REQUEST.ordinal());
        assertEquals(1, ErrorCode.SERVICE_UNAVAILABLE.ordinal());
        assertEquals(2, ErrorCode.TOKEN_EXPIRED.ordinal());
        assertEquals(3, ErrorCode.TOKEN_DOES_NOT_EXIST.ordinal());
        assertEquals(4, ErrorCode.TOKEN_INCORRECT_FORMAT.ordinal());
        assertEquals(5, ErrorCode.FORBIDDEN.ordinal());
        assertEquals(6, ErrorCode.USER_NOT_FOUND.ordinal());
        assertEquals(7, ErrorCode.USER_ALREADY_EXIST.ordinal());
        assertEquals(8, ErrorCode.USER_PASSWORD_INCORRECT.ordinal());
        assertEquals(9, ErrorCode.ROLE_NOT_FOUND.ordinal());
    }
}