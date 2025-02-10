package feed.repository;

import feed.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.userId = :userId ORDER BY p.postedTime DESC")
    Page<Post> findByUserId(@Param("userId") UUID userId, Pageable pageable);
}
