package FindYourThingApplication.services.providers;

import FindYourThingApplication.entities.Review;
import FindYourThingApplication.exceptions.review.ReviewNotFoundException;
import FindYourThingApplication.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewProvider
{
    private final ReviewRepository reviewRepository;

    public Review getReviewFromId(Integer reviewId)
    {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException()
        );
    }

    public Review getReviewFromIdAndReviewerId(Integer reviewId, Integer reviewerId)
    {
        return reviewRepository.findByIdAndReviewerId(reviewId, reviewerId).orElseThrow(
                () -> new ReviewNotFoundException()
        );
    }

    public Review getReviewWithDetails(Integer reviewId)
    {
        return reviewRepository.findByIdWithDetails(reviewId).orElseThrow(
                () -> new ReviewNotFoundException()
        );
    }
}
