package messenger.controller;

import messenger.dto.chat.request.CreateChatRequest;
import messenger.dto.chat.request.SearchChatRequest;
import messenger.dto.chat.request.SearchMessageRequest;
import messenger.dto.chat.response.MessageForResponse;
import messenger.entity.Message;
import messenger.service.ChatService;
import messenger.utils.ErrorMessages;
import messenger.entity.UsersChats;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;


    @Autowired
    public ChatController(ChatService aChatService) {
        this.chatService = aChatService;
    }

    public ResponseEntity<?> addUserChats(@RequestBody UsersChats usersChats) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.addUserChats(usersChats));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/chat/start")
    public ResponseEntity<?> createChat(@Valid @RequestBody CreateChatRequest createChatRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(createChatRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchChat(@Valid @RequestBody SearchChatRequest searchChatRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.searchChat(searchChatRequest.getUserId(), searchChatRequest.getRequest(), searchChatRequest.getPageNumber(), searchChatRequest.getCountChatsOnPage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{chatId}/search")
    public ResponseEntity<?> searchMessage(@Valid @RequestBody SearchMessageRequest searchMessageRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.searchMessage(searchMessageRequest.getChatId(), searchMessageRequest.getRequest(), searchMessageRequest.getPageNumber(), searchMessageRequest.getCountMessagesOnPage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<?> deleteChat(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                        @PathVariable UUID chatId) {
        try {
            chatService.deleteChat(chatId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllChatsByUserId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                 @PathVariable UUID userId,
                                                 @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                                 @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                 @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                                 @RequestParam(value = "countChatsOnPage", required = false, defaultValue = "20") Long countChatsOnPage) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.getAllChatsByUserId(userId, pageNumber, countChatsOnPage));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> getAllMessagesByChatId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                    @PathVariable UUID chatId,
                                                    @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                    @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                                    @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long countMessagesOnPage) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody Message message) {
        try {
            UUID response = chatService.sendMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/subscribe/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<MessageForResponse> subscribe(@Valid @PathVariable UUID chatId, WebRequest request) {
        return chatService.subscribeOnChat(chatId);
    }
}
