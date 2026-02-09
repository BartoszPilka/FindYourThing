package FindYourThingApplication.services.providers;

import FindYourThingApplication.entities.Review;
import FindYourThingApplication.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewProvider
{
    private final ReviewRepository reviewRepository;

    public ReviewProvider(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review getReviewFromId(Integer reviewId)
    {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("This review does not exist")
        );
    }
}
