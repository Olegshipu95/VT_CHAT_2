package messenger.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.dto.chat.request.CreateChatRequest;
import messenger.dto.chat.request.SearchChatRequest;
import messenger.dto.chat.request.SearchMessageRequest;
import messenger.dto.chat.response.MessageForResponse;
import messenger.entity.Message;
import messenger.entity.UsersChats;
import messenger.service.ChatService;
import messenger.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/users")
    public ResponseEntity<?> addUserChats(@RequestBody UsersChats usersChats) {
        return ResponseEntity.status(HttpStatus.OK).body(chatService.addUserChats(usersChats));
    }

    @PostMapping
    public ResponseEntity<?> createChat(@Valid @RequestBody CreateChatRequest createChatRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(createChatRequest));
    }

    @GetMapping
    public ResponseEntity<?> getUsersChats(@Valid @RequestBody SearchChatRequest searchChatRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(chatService.getUsersChats(searchChatRequest.getUserId(), searchChatRequest.getRequest(), searchChatRequest.getPageNumber(), searchChatRequest.getCountChatsOnPage()));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getMessage(@Valid @RequestBody SearchMessageRequest searchMessageRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(chatService.searchMessage(searchMessageRequest.getChatId(), searchMessageRequest.getRequest(), searchMessageRequest.getPageNumber(), searchMessageRequest.getCountMessagesOnPage()));
    }


    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteChat(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL) @PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllChatsByUserId(
        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @PathVariable UUID userId,
        @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
        @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
        @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
        @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
        @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
        @RequestParam(value = "countChatsOnPage", required = false, defaultValue = "20") Long countChatsOnPage
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(chatService.getAllChatsByUserId(userId, pageNumber, countChatsOnPage));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> getAllMessagesByChatId(
        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @PathVariable UUID chatId,
        @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
        @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
        @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
        @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
        @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
        @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long countMessagesOnPage
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(chatService.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage));
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody Message message) {
        UUID response = chatService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/subscribe/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<MessageForResponse> subscribe(@Valid @PathVariable UUID chatId, WebRequest request) {
        return chatService.subscribeOnChat(chatId);
    }
}
