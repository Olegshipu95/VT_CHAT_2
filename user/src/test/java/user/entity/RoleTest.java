package user.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testEnumValues() {
        // Проверяем, что все значения перечисления корректны
        Role[] roles = Role.values();
        assertEquals(3, roles.length);
        assertEquals(Role.CUSTOMER, roles[0]);
        assertEquals(Role.EXECUTOR, roles[1]);
        assertEquals(Role.SUPERVISOR, roles[2]);
    }

    @Test
    void testEnumValueOf() {
        // Проверяем, что метод valueOf корректно возвращает значения перечисления
        assertEquals(Role.CUSTOMER, Role.valueOf("CUSTOMER"));
        assertEquals(Role.EXECUTOR, Role.valueOf("EXECUTOR"));
        assertEquals(Role.SUPERVISOR, Role.valueOf("SUPERVISOR"));
    }

    @Test
    void testEnumOrdinal() {
        // Проверяем порядок значений в перечислении
        assertEquals(0, Role.CUSTOMER.ordinal());
        assertEquals(1, Role.EXECUTOR.ordinal());
        assertEquals(2, Role.SUPERVISOR.ordinal());
    }
}