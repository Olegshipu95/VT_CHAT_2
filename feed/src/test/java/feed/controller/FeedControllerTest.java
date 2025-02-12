package feed.controller;

import feed.dto.feed.request.CreatePostRequest;
import feed.dto.feed.request.DeletePostRequest;
import feed.dto.feed.response.FeedResponse;
import feed.dto.feed.response.PostForResponse;
import feed.entity.Post;
import feed.service.FeedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedControllerTest {

    @Mock
    private FeedService feedService;

    @InjectMocks
    private FeedController feedController;

    private UUID testUserId;
    private UUID testPostId;
    private CreatePostRequest testCreatePostRequest;
    private DeletePostRequest testDeletePostRequest;
    private FeedResponse testFeedResponse;
    private PostForResponse testPostForResponse;
    private Post testPost;

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
    void testCreatePost() {
        when(feedService.createFeed(testCreatePostRequest)).thenReturn(testPostId);

        ResponseEntity<UUID> response = feedController.createPost(testCreatePostRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPostId, response.getBody());

        verify(feedService, times(1)).createFeed(testCreatePostRequest);
    }

    @Test
    void testDeletePostSuccess() {
        doNothing().when(feedService).deletePost(testDeletePostRequest);

        ResponseEntity<?> response = feedController.deletePost(testDeletePostRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        verify(feedService, times(1)).deletePost(testDeletePostRequest);
    }

    @Test
    void testDeletePostFailure() {
        RuntimeException exception = new RuntimeException("Failed to delete post");
        doThrow(exception).when(feedService).deletePost(testDeletePostRequest);

        ResponseEntity<?> response = feedController.deletePost(testDeletePostRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());

        verify(feedService, times(1)).deletePost(testDeletePostRequest);
    }

    @Test
    void testGetAllPostsByUserIdSuccess() {
        when(feedService.getFeedByUserId(testUserId, 0L, 20L)).thenReturn(testFeedResponse);

        ResponseEntity<?> response = feedController.getAllPostsByUserId(testUserId, 0L, 20L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testFeedResponse, response.getBody());

        verify(feedService, times(1)).getFeedByUserId(testUserId, 0L, 20L);
    }

    @Test
    void testGetAllPostsByUserIdFailure() {
        RuntimeException exception = new RuntimeException("Failed to fetch posts");
        when(feedService.getFeedByUserId(testUserId, 0L, 20L)).thenThrow(exception);

        ResponseEntity<?> response = feedController.getAllPostsByUserId(testUserId, 0L, 20L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());

        verify(feedService, times(1)).getFeedByUserId(testUserId, 0L, 20L);
    }
}