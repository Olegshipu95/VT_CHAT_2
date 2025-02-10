package user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import user.entity.UsersChats;

@FeignClient(name = "chat-messenger-cloud")
public interface UsersChatsServiceClient {


    @PostMapping("/chats/addUserChats")
    UsersChats save(@RequestBody UsersChats usersChats);
}
