package user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.entity.User;
import user.entity.UsersChats;
import user.exception.UserAccountNotFoundException;
import user.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersChatsServiceClient usersChatsService;

    private UUID testUserId;
    private CreateUserAccountRequest createUserAccountRequest;
    private UpdateUserInfoRequest updateUserInfoRequest;
    private GetUserInfoResponse getUserInfoResponse;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        createUserAccountRequest = new CreateUserAccountRequest(
                "John",
                "Doe",
                "john.doe@example.com",
                "Software Engineer",
                "New York",
                LocalDate.of(1990, 1, 1),
                "http://example.com/logo.png"
        );
        updateUserInfoRequest = new UpdateUserInfoRequest(
                testUserId,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "Senior Software Engineer",
                "San Francisco",
                LocalDate.of(1985, 5, 15),
                "http://example.com/new-logo.png"
        );
        getUserInfoResponse = new GetUserInfoResponse(
                testUserId,
                "John",
                "Doe",
                "john.doe@example.com",
                "Software Engineer",
                "New York",
                LocalDate.of(1990, 1, 1),
                "http://example.com/logo.png"
        );
        testUser = new User(
                testUserId,
                "John",
                "Doe",
                "john.doe@example.com",
                "Software Engineer",
                "New York",
                LocalDate.of(1990, 1, 1),
                "http://example.com/logo.png"
        );
    }

    @Test
    void testCreateAccount() {
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));
        when(usersChatsService.save(any(UsersChats.class))).thenReturn(new UsersChats());

        Mono<UUID> result = userService.createAccount(createUserAccountRequest);

        StepVerifier.create(result)
                .expectNext(testUserId)
                .verifyComplete();

        verify(userRepository, times(1)).save(any(User.class));
        verify(usersChatsService, times(1)).save(any(UsersChats.class));
    }

    @Test
    void testUpdateAccount() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.just(testUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        Mono<UUID> result = userService.updateAccount(updateUserInfoRequest);

        StepVerifier.create(result)
                .expectNext(testUserId)
                .verifyComplete();

        verify(userRepository, times(1)).findById(testUserId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateAccountNotFound() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.empty());

        Mono<UUID> result = userService.updateAccount(updateUserInfoRequest);

        StepVerifier.create(result)
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(testUserId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetAccountById() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.just(testUser));

        Mono<GetUserInfoResponse> result = userService.getAccountById(testUserId);

        StepVerifier.create(result)
                .expectNext(getUserInfoResponse)
                .verifyComplete();

        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void testGetAccountByIdNotFound() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.empty());

        Mono<GetUserInfoResponse> result = userService.getAccountById(testUserId);

        StepVerifier.create(result)
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void testDeleteAccountById() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.just(testUser));
        when(userRepository.deleteById(testUserId)).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteAccountById(testUserId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository, times(1)).findById(testUserId);
        verify(userRepository, times(1)).deleteById(testUserId);
    }

    @Test
    void testDeleteAccountByIdNotFound() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteAccountById(testUserId);

        StepVerifier.create(result)
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(testUserId);
        verify(userRepository, never()).deleteById(testUserId);
    }

    @Test
    void testFindById() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.just(testUser));

        Mono<User> result = userService.findById(testUserId);

        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();

        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(testUserId)).thenReturn(Mono.empty());

        Mono<User> result = userService.findById(testUserId);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        verify(userRepository, times(1)).findById(testUserId);
    }
}
