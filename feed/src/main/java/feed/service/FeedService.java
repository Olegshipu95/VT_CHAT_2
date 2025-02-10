package feed.service;


import feed.dto.feed.request.CreatePostRequest;
import feed.dto.feed.request.DeletePostRequest;
import feed.dto.feed.response.PostForResponse;
import feed.dto.feed.response.FeedResponse;
import feed.entity.Post;
import feed.entity.User;
import feed.exception.UserAccountNotFoundException;
import feed.repository.FeedRepository;
import feed.utils.ErrorMessages;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedService {

    private final UserService userService;
    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository, UserService userService) {
        this.feedRepository = feedRepository;
        this.userService = userService;
    }

    @Transactional
    public UUID createFeed(CreatePostRequest createPostRequest) {
        log.debug("CREATE: start createFeed with userId: {}", createPostRequest.userId());
        validateUsersExist(createPostRequest.userId());
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(createPostRequest.userId());
        post.setTitle(createPostRequest.title());
        post.setText(createPostRequest.text());
        post.setImages(createPostRequest.images());
        post.setPostedTime(new Timestamp(System.currentTimeMillis()));

        feedRepository.save(post);
        log.info("CREATE: new feed {}", post.getId());
        return post.getId();
    }

    public void deletePost(DeletePostRequest request) {
        log.debug("DELETE: start with feedId: {}", request.feedId());
        Optional<Post> post = feedRepository.findById(request.feedId());
        if (post.isEmpty()) throw new RuntimeException(ErrorMessages.POST_NOT_FOUND);
        feedRepository.deleteById(request.feedId());
        log.info("DELETE: ID: {} has been successfully deleted.", request.feedId());
    }

    public FeedResponse getFeedByUserId(UUID userId, Long page, Long count) {
        log.debug("GET: start getFeedByUserId for userId: {}", userId);
        try {
            Page<Post> pageOfPosts = feedRepository.findByUserId(userId, PageRequest.of(page.intValue(), count.intValue()));
            FeedResponse feedResponse =  new FeedResponse(
                    pageOfPosts.stream()
                            .map(PostForResponse::new)
                            .collect(Collectors.toList())
            );
            log.info("GET: getFeedByUserId: {} has been successfully retrieved.", userId);
            return feedResponse;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    private void validateUsersExist(UUID userId) {
        try {
            if (userService.findById(userId) != null) {
                log.info("User ID {} does not exist", userId);
                throw new UserAccountNotFoundException(userId);
            }
        } catch (FeignException e) {
            log.warn("Ошибка при вызове Feign-клиента: {}", e.getMessage());
        }
    }
}
