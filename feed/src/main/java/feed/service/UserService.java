package feed.service;

import feed.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "chat-user-cloud")
public interface UserService {

    @GetMapping("/{id}")
    User findById(@PathVariable UUID uuid);
}
