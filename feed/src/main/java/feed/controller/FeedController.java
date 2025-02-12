package feed.controller;

import feed.dto.request.CreatePostRequest;
import feed.dto.response.FeedResponse;
import feed.entity.Post;
import feed.service.FeedService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    @PageableAsQueryParam
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllPostsByUserId(
        @PathVariable String userId,
        @Schema(hidden = true) @PageableDefault(size = 50) Pageable pageable
    ) {
        Page<Post> response = feedService.getFeedByUserId(userId, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UUID> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        UUID id = feedService.createFeed(createPostRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id) {
        feedService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
