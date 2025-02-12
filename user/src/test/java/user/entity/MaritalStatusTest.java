package user.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaritalStatusTest {

    @Test
    void testEnumValues() {
        // Проверяем, что все значения перечисления корректны
        MaritalStatus[] statuses = MaritalStatus.values();
        assertEquals(4, statuses.length);
        assertEquals(MaritalStatus.SINGLE, statuses[0]);
        assertEquals(MaritalStatus.DATING, statuses[1]);
        assertEquals(MaritalStatus.MARRIED, statuses[2]);
        assertEquals(MaritalStatus.DIVORCED, statuses[3]);
    }

    @Test
    void testEnumValueOf() {
        // Проверяем, что метод valueOf корректно возвращает значения перечисления
        assertEquals(MaritalStatus.SINGLE, MaritalStatus.valueOf("SINGLE"));
        assertEquals(MaritalStatus.DATING, MaritalStatus.valueOf("DATING"));
        assertEquals(MaritalStatus.MARRIED, MaritalStatus.valueOf("MARRIED"));
        assertEquals(MaritalStatus.DIVORCED, MaritalStatus.valueOf("DIVORCED"));
    }

    @Test
    void testEnumOrdinal() {
        // Проверяем порядок значений в перечислении
        assertEquals(0, MaritalStatus.SINGLE.ordinal());
        assertEquals(1, MaritalStatus.DATING.ordinal());
        assertEquals(2, MaritalStatus.MARRIED.ordinal());
        assertEquals(3, MaritalStatus.DIVORCED.ordinal());
    }

    @Test
    void testStatusName() {
        // Проверяем, что поле statusName инициализируется корректно
        assertEquals("SINGLE", MaritalStatus.SINGLE.statusName);
        assertEquals("DATING", MaritalStatus.DATING.statusName);
        assertEquals("MARRIED", MaritalStatus.MARRIED.statusName);
        assertEquals("DIVORCED", MaritalStatus.DIVORCED.statusName);
    }
}