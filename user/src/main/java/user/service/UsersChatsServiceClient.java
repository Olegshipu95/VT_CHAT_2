package user.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import user.entity.UsersChats;

@FeignClient(name = "chat-messenger-cloud")
public interface UsersChatsServiceClient {


    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.POST, value = "/chats/addUserChats")
    UsersChats save(@RequestBody UsersChats usersChats);
}
