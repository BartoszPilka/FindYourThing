package FindYourThingApplication.repositories;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByReviewerIdAndItemId(Integer reviewerId, Integer itemId);
    @Query("SELECT AVG(r.grade) FROM Review r WHERE r.founder.id = :userId")
    Double findAverageGradeByFounderId(@Param("userId") Integer userId);
}
