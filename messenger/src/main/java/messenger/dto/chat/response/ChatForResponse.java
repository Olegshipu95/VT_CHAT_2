package messenger.dto.chat.response;


import messenger.entity.ChatType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ChatForResponse {
    UUID id;
    ChatType chatType;
    int countMembers;
    String lastMessage;
    boolean lastMessageHavePhoto;


}
