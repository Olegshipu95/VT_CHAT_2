package feed.service;

import feed.dto.feed.request.CreatePostRequest;
import feed.dto.feed.request.DeletePostRequest;
import feed.dto.feed.response.FeedResponse;
import feed.dto.feed.response.PostForResponse;
import feed.entity.Post;
import feed.exception.UserAccountNotFoundException;
import feed.repository.FeedRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Mock
    private FeedRepository feedRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FeedService feedService;

    private UUID testUserId;
    private UUID testPostId;
    private CreatePostRequest testCreatePostRequest;
    private DeletePostRequest testDeletePostRequest;
    private Post testPost;
    private PostForResponse testPostForResponse;
    private FeedResponse testFeedResponse;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        testPostId = UUID.randomUUID();

        testCreatePostRequest = new CreatePostRequest(testUserId, "Test Title", "Test Content", null);
        testDeletePostRequest = new DeletePostRequest(testPostId, testUserId);

        testPost = new Post();
        testPost.setId(testPostId);
        testPost.setUserId(testUserId);
        testPost.setTitle("Test Title");
        testPost.setText("Test Content");
        testPost.setImages(null);
        testPost.setPostedTime(new Timestamp(System.currentTimeMillis()));

        testPostForResponse = new PostForResponse(testPost);
        testFeedResponse = new FeedResponse(List.of(testPostForResponse));
    }

    @Test
    void testCreateFeed() {
        UUID expectedUUID = UUID.randomUUID();

        try (MockedStatic<UUID> mockedUUID = Mockito.mockStatic(UUID.class)) {
            mockedUUID.when(UUID::randomUUID).thenReturn(expectedUUID);
            when(feedRepository.save(any(Post.class))).thenReturn(testPost);
            UUID result = feedService.createFeed(testCreatePostRequest);
            assertEquals(expectedUUID, result); // Проверяем, что результат совпадает с ожидаемым UUID
            verify(feedRepository, times(1)).save(any(Post.class));
            verify(userService, times(1)).findById(testUserId);
        }
    }

    @Test
    void testCreateFeedWithUserNotFound() {
        when(userService.findById(testUserId)).thenThrow(new UserAccountNotFoundException(testUserId));

        assertThrows(UserAccountNotFoundException.class, () -> feedService.createFeed(testCreatePostRequest));

        verify(userService, times(1)).findById(testUserId);
        verify(feedRepository, never()).save(any());
    }

    @Test
    void testDeletePost() {
        when(feedRepository.findById(testPostId)).thenReturn(Optional.of(testPost));

        feedService.deletePost(testDeletePostRequest);

        verify(feedRepository, times(1)).findById(testPostId);
        verify(feedRepository, times(1)).deleteById(testPostId);
    }

    @Test
    void testDeletePostNotFound() {
        when(feedRepository.findById(testPostId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> feedService.deletePost(testDeletePostRequest));

        assertEquals("Post was not found", exception.getMessage());
        verify(feedRepository, times(1)).findById(testPostId);
        verify(feedRepository, never()).deleteById(testPostId);
    }

    @Test
    void testGetFeedByUserId() {
        Page<Post> pageOfPosts = new PageImpl<>(List.of(testPost));
        when(feedRepository.findByUserId(testUserId, PageRequest.of(0, 20))).thenReturn(pageOfPosts);

        FeedResponse result = feedService.getFeedByUserId(testUserId, 0L, 20L);

        assertEquals(testFeedResponse.feed().size(), result.feed().size());
        assertEquals(testFeedResponse.feed().get(0).getId(), result.feed().get(0).getId());
        assertEquals(testFeedResponse.feed().get(0).getTitle(), result.feed().get(0).getTitle());
        verify(feedRepository, times(1)).findByUserId(testUserId, PageRequest.of(0, 20));
    }

    @Test
    void testGetFeedByUserIdWithException() {
        when(feedRepository.findByUserId(testUserId, PageRequest.of(0, 20))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> feedService.getFeedByUserId(testUserId, 0L, 20L));

        assertEquals("The problem with the database query", exception.getMessage());
        verify(feedRepository, times(1)).findByUserId(testUserId, PageRequest.of(0, 20));
    }
}