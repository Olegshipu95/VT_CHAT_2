package subscription.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import subscription.dto.subs.request.CreateSubRequest;
import subscription.dto.subs.response.SubscriptionResponse;
import subscription.entity.Subscribers;
import subscription.service.SubscriptionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {

    @Mock
    private SubscriptionService service;

    @InjectMocks
    private SubscriptionController controller;

    private UUID testSubId;
    private UUID testUserId;
    private UUID testSubscribedUserId;
    private CreateSubRequest testRequest;
    private Subscribers testSubscriber;
    private SubscriptionResponse testSubscriptionResponse;
    private LocalDateTime testSubscriptionTime;

    @BeforeEach
    void setUp() {
        testSubId = UUID.randomUUID();
        testUserId = UUID.randomUUID();
        testSubscribedUserId = UUID.randomUUID();
        testSubscriptionTime = LocalDateTime.now();

        testRequest = new CreateSubRequest(testUserId, testSubscribedUserId);
        testSubscriber = new Subscribers();
        testSubscriber.setId(testSubId);
        testSubscriptionResponse = new SubscriptionResponse(testSubId, testSubscribedUserId, testSubscriptionTime);
    }

    @Test
    void testMakeSubscription() {
        // Настройка моков
        when(service.createSub(testRequest)).thenReturn(testSubId);

        // Вызов метода контроллера
        ResponseEntity<UUID> response = controller.makeSubscription(testRequest);

        // Проверка результата
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testSubId, response.getBody());

        // Проверка вызова сервиса
        verify(service, times(1)).createSub(testRequest);
    }

    @Test
    void testGetSubscriptionById() {
        // Настройка моков
        when(service.getSub(testSubId)).thenReturn(testSubscriber);

        // Вызов метода контроллера
        ResponseEntity<Subscribers> response = controller.getSubscriptionById(testSubId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubscriber, response.getBody());

        // Проверка вызова сервиса
        verify(service, times(1)).getSub(testSubId);
    }

    @Test
    void testDeleteSubscription() {
        // Вызов метода контроллера
        ResponseEntity<Void> response = controller.deleteSubscription(testSubId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        // Проверка вызова сервиса
        verify(service, times(1)).deleteSub(testSubId);
    }

    @Test
    void testGetSubscriptionsByUserId() {
        // Настройка моков
        List<SubscriptionResponse> subscriptions = List.of(testSubscriptionResponse);
        when(service.getSubscriptionsByUserId(testUserId)).thenReturn(subscriptions);

        // Вызов метода контроллера
        ResponseEntity<List<SubscriptionResponse>> response = controller.getSubscriptionsByUserId(testUserId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriptions, response.getBody());

        // Проверка вызова сервиса
        verify(service, times(1)).getSubscriptionsByUserId(testUserId);
    }
}