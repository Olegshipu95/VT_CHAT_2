package user.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import user.service.UserService;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDeserializerTest {

    @Mock
    private UserService userService;

    @Mock
    private JsonParser jsonParser;

    @Mock
    private DeserializationContext deserializationContext;

    @InjectMocks
    private UserDeserializer userDeserializer;

    private UUID userId;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        expectedUser = new User();
        expectedUser.setId(userId);
    }

    @Test
    void testDeserialize() throws IOException {
        // Настройка моков
        when(jsonParser.getText()).thenReturn(userId.toString());
        when(userService.findById(userId)).thenReturn(Mono.just(expectedUser));

        // Вызов метода десериализации
        User result = userDeserializer.deserialize(jsonParser, deserializationContext);

        // Проверка результата
        assertEquals(expectedUser, result);

        // Проверка вызовов моков
        verify(jsonParser, times(1)).getText();
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void testDeserializeWithInvalidId() throws IOException {
        // Настройка моков для неверного UUID
        String invalidId = "invalid-uuid";
        when(jsonParser.getText()).thenReturn(invalidId);

        // Проверка исключения
        try {
            userDeserializer.deserialize(jsonParser, deserializationContext);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid UUID string: " + invalidId, e.getMessage());
        }

        // Проверка вызовов моков
        verify(jsonParser, times(1)).getText();
        verify(userService, never()).findById(any());
    }
}