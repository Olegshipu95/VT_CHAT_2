package user.entity;


import java.util.List;
import java.util.UUID;

public class UsersChats {
    private UUID id;
    private UUID userId;
    private List<UUID> chats;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getChats() {
        return chats;
    }

    public void setChats(List<UUID> chats) {
        this.chats = chats;
    }
}
