package subscription.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import subscription.entity.User;

import java.util.UUID;

@FeignClient(name = "chat-user-cloud")
public interface UserService {

    @GetMapping("/{id}")
    User findById(@PathVariable UUID uuid);
}
