package user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.service.UserService;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    private UUID testUserId;
    private CreateUserAccountRequest createUserAccountRequest;
    private UpdateUserInfoRequest updateUserInfoRequest;
    private GetUserInfoResponse getUserInfoResponse;

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
    }

    @Test
    void testCreateAccount() {
        when(userService.createAccount(any(CreateUserAccountRequest.class))).thenReturn(Mono.just(testUserId));

        webTestClient.post()
                .uri("/accounts/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createUserAccountRequest), CreateUserAccountRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UUID.class)
                .isEqualTo(testUserId);
    }

    @Test
    void testUpdateAccount() {
        when(userService.updateAccount(any(UpdateUserInfoRequest.class))).thenReturn(Mono.just(testUserId));

        webTestClient.put()
                .uri("/accounts/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateUserInfoRequest), UpdateUserInfoRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class)
                .isEqualTo(testUserId);
    }

    @Test
    void testGetAccountById() {
        when(userService.getAccountById(testUserId)).thenReturn(Mono.just(getUserInfoResponse));

        webTestClient.get()
                .uri("/accounts/users/{id}", testUserId.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetUserInfoResponse.class)
                .isEqualTo(getUserInfoResponse);
    }

    @Test
    void testDeleteAccountById() {
        when(userService.deleteAccountById(testUserId)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/accounts/users/{id}", testUserId.toString())
                .exchange()
                .expectStatus().isNoContent();
    }
}
