package FindYourThingApplication.services;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Review;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.CreateReviewRequest;
import FindYourThingApplication.entities.dto.responses.ReviewResponse;
import FindYourThingApplication.mappers.ReviewMapper;
import FindYourThingApplication.repositories.ItemRepository;
import FindYourThingApplication.repositories.ReviewRepository;
import FindYourThingApplication.services.providers.ItemProvider;
import FindYourThingApplication.services.providers.ReviewProvider;
import FindYourThingApplication.services.providers.UserProvider;
import FindYourThingApplication.services.validation.ReviewValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService
{
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    private final ReviewValidation reviewValidation;

    private final ReviewMapper reviewMapper;

    private final UserProvider userProvider;
    private final ItemProvider itemProvider;
    private  final ReviewProvider reviewProvider;


    public List<ReviewResponse> getUserReviewsReceived(Integer userId){
        User user = userProvider.getUserFromId(userId);
        return user.getReviewsReceived().stream()
                .map(reviewMapper::mapToDTO)
                .toList();
    }

    @Transactional
    public ReviewResponse createReview(Integer reviewerId, CreateReviewRequest request)
    {
        User reviewer = userProvider.getUserFromId(reviewerId);
        User founder = userProvider.getUserFromId(request.getFounderId());
        Item item = itemProvider.getItemFromId(request.getItemId());

        reviewValidation.validateCreateReview(item, founder, reviewer);

        Review review = reviewMapper.mapToEntity(request, reviewer, founder, item);
        Review saved = reviewRepository.save(review);
        return reviewMapper.mapToDTO(saved);
    }

    public Double getUserAverageGrade(Integer userId)
    {
        if(userId == null)
            throw new IllegalArgumentException("ID of an user cannot be null");

        User user = userProvider.getUserFromId(userId);
        Double average = reviewRepository.findAverageGradeByFounderId(userId);
        if(average == null)
            average = 0.0;

        return Math.round(average * 100.0) / 100.0;
    }

    @Transactional
    public void deleteReview(Integer reviewId, Integer reviewerId)
    {
        if(reviewId == null)
            throw new IllegalArgumentException("ID of a review must not be null");
        if(reviewerId == null)
            throw new IllegalArgumentException("ID of an user must not be null");

        Review review = reviewProvider.getReviewFromId(reviewId);

        if(!review.getReviewer().getId().equals(reviewerId))
            throw new RuntimeException("This user is not the author of the review");

        reviewRepository.delete(review);
    }

    @Transactional
    public void editReview(Integer reviewId, Integer reviewerId, Integer grade)
    {
        if(reviewId == null)
            throw new IllegalArgumentException("ID of a review must not be null");
        if(reviewerId == null)
            throw new IllegalArgumentException("ID of a reviewer must not be null");
        if(grade == null || grade > 5 || grade < 1)
            throw new IllegalArgumentException("Grade must be from 1 to 5");

        Review review = reviewProvider.getReviewFromId(reviewId);

        if(!review.getReviewer().getId().equals(reviewerId))
            throw new RuntimeException("You are not allowed to edit this review");

        review.setGrade(grade);
        reviewRepository.save(review);
    }
}
