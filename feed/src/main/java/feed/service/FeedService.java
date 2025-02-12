package feed.service;

import feed.dto.request.CreatePostRequest;
import feed.dto.response.FeedResponse;
import feed.dto.response.PostForResponse;
import feed.entity.Post;
import feed.repository.FeedRepository;
import feed.utils.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    public Page<Post> getFeedByUserId(String userId, Pageable pageable) {
        log.debug("GET: start getFeedByUserId for userId: {}", userId);
        Page<Post> page = feedRepository.findByUserId(String.valueOf(userId), pageable);
        log.info("GET: getFeedByUserId: {} has been successfully retrieved.", userId);
        return page;
    }

    public UUID createFeed(CreatePostRequest createPostRequest) {
        String userId = SecurityContextHolder.getUserId();
        log.debug("CREATE: start createFeed with userId: {}", userId);
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(userId);
        post.setTitle(createPostRequest.getTitle());
        post.setText(createPostRequest.getText());
        post.setImagesUrls(createPostRequest.getImages());
        post.setPostedTime(new Timestamp(System.currentTimeMillis()));
        feedRepository.save(post);
        return post.getId();
    }

    public void deletePost(UUID feedID) {
        log.debug("DELETE: start with feedId: {}", feedID);
        feedRepository.deleteById(feedID);
    }
}
