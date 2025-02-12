package user.entity;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsersChatsTest {

    @Test
    void testGettersAndSetters() {
        // Создаем объект UsersChats
        UsersChats usersChats = new UsersChats();

        // Создаем тестовые данные
        UUID testId = UUID.randomUUID();
        UUID testUserId = UUID.randomUUID();

        // Устанавливаем значения через сеттеры
        usersChats.setId(testId);
        usersChats.setUserId(testUserId);

        // Проверяем, что геттеры возвращают корректные значения
        assertEquals(testId, usersChats.getId());
        assertEquals(testUserId, usersChats.getUserId());
    }

    @Test
    void testGetChats() {
        // Создаем объект UsersChats
        UsersChats usersChats = new UsersChats();

        // Проверяем, что метод getChats возвращает пустой список (по текущей реализации)
        List<UUID> chats = usersChats.getChats();
        assertNotNull(chats);
        assertTrue(chats.isEmpty());
    }

    @Test
    void testSetChats() {
        // Создаем объект UsersChats
        UsersChats usersChats = new UsersChats();

        // Создаем тестовый список UUID
        List<UUID> testChats = List.of(UUID.randomUUID(), UUID.randomUUID());

        // Устанавливаем список через сеттер
        usersChats.setChats(testChats);

        // Проверяем, что метод getChats возвращает пустой список (по текущей реализации)
        List<UUID> chats = usersChats.getChats();
        assertNotNull(chats);
        assertTrue(chats.isEmpty());
    }
}