package messenger.service;



import messenger.dto.user.response.GetUserInfoResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.UUID;


@FeignClient(name = "chat-user-cloud")
public interface CustomerServiceClient {

    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/accounts/users/{id}")
    Mono<ResponseEntity<GetUserInfoResponse>> getAccountById(@PathVariable("id") UUID id);
}
