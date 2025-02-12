package subscription.service;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import subscription.dto.subs.request.CreateSubRequest;
import subscription.dto.subs.response.SubscriptionResponse;
import subscription.entity.Subscribers;
import subscription.exception.UserAccountNotFoundException;
import subscription.repository.SubRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private SubRepository subscribersRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private UUID testUserId;
    private UUID testSubscribedUserId;
    private UUID testSubId;
    private CreateSubRequest testRequest;
    private Subscribers testSubscriber;
    private SubscriptionResponse testSubscriptionResponse;
    private LocalDateTime testSubscriptionTime;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        testSubscribedUserId = UUID.randomUUID();
        testSubId = UUID.randomUUID();
        testSubscriptionTime = LocalDateTime.now();

        testRequest = new CreateSubRequest(testUserId, testSubscribedUserId);
        testSubscriber = new Subscribers();
        testSubscriber.setId(testSubId);
        testSubscriber.setUserId(testUserId);
        testSubscriber.setSubscribedUserId(testSubscribedUserId);
        testSubscriber.setSubscriptionTime(testSubscriptionTime);

        testSubscriptionResponse = new SubscriptionResponse(testSubId, testSubscribedUserId, testSubscriptionTime);
    }

    @Test
    void testCreateSub() {
        when(subscribersRepository.save(any(Subscribers.class))).thenReturn(testSubscriber);
        UUID result = subscriptionService.createSub(testRequest);
        assertEquals(testSubId, result);
        verify(subscribersRepository, times(1)).save(any(Subscribers.class));
        verify(userService, times(1)).findById(testUserId);
        verify(userService, times(1)).findById(testSubscribedUserId);
    }

    @Test
    void testCreateSubWithUserNotFound() {
        when(userService.findById(testUserId)).thenThrow(new UserAccountNotFoundException(testUserId));
        assertThrows(UserAccountNotFoundException.class, () -> subscriptionService.createSub(testRequest));
        verify(userService, times(1)).findById(testUserId);
        verify(userService, never()).findById(testSubscribedUserId);
        verify(subscribersRepository, never()).save(any());
    }

    @Test
    void testGetSub() {
        when(subscribersRepository.findById(testSubId)).thenReturn(Optional.of(testSubscriber));
        Subscribers result = subscriptionService.getSub(testSubId);
        assertEquals(testSubscriber, result);
        verify(subscribersRepository, times(1)).findById(testSubId);
    }

    @Test
    void testGetSubNotFound() {
        when(subscribersRepository.findById(testSubId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.getSub(testSubId));
        verify(subscribersRepository, times(1)).findById(testSubId);
    }

    @Test
    void testDeleteSub() {
        when(subscribersRepository.existsById(testSubId)).thenReturn(true);
        subscriptionService.deleteSub(testSubId);
        verify(subscribersRepository, times(1)).existsById(testSubId);
        verify(subscribersRepository, times(1)).deleteById(testSubId);
    }

    @Test
    void testDeleteSubNotFound() {
        when(subscribersRepository.existsById(testSubId)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.deleteSub(testSubId));
        verify(subscribersRepository, times(1)).existsById(testSubId);
        verify(subscribersRepository, never()).deleteById(testSubId);
    }

    @Test
    void testGetSubscriptionsByUserId() {
        List<SubscriptionResponse> subscriptions = List.of(testSubscriptionResponse);
        when(subscribersRepository.getSubResponseByUserId(testUserId)).thenReturn(subscriptions);
        List<SubscriptionResponse> result = subscriptionService.getSubscriptionsByUserId(testUserId);
        assertEquals(subscriptions, result);
        verify(subscribersRepository, times(1)).getSubResponseByUserId(testUserId);
    }
}