package feed.dto.feed.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeletePostRequest(

        @NotNull(message = "feed id cannot be null")
        UUID feedId,

        @NotNull(message = "user id cannot be null")
        UUID userId

) {
}
