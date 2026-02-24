package FindYourThingApplication.repositories;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByReviewerIdAndItemId(Integer reviewerId, Integer itemId);
    Optional<Review> findByIdAndReviewerId(Integer id, Integer reviewerId);

    @Query("SELECT r FROM Review r JOIN FETCH r.item JOIN FETCH r.founder WHERE r.id = :id")
    Optional<Review> findByIdWithDetails(@Param("id") Integer id);
}
