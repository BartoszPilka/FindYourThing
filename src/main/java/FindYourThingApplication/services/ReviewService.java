package FindYourThingApplication.services;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Review;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.CreateReviewRequest;
import FindYourThingApplication.entities.dto.requests.EditReviewRequest;
import FindYourThingApplication.entities.dto.responses.AvgGradeResponse;
import FindYourThingApplication.entities.dto.responses.ReviewResponse;
import FindYourThingApplication.exceptions.user.UserNotFoundException;
import FindYourThingApplication.mappers.ReviewMapper;
import FindYourThingApplication.repositories.ItemRepository;
import FindYourThingApplication.repositories.ReviewRepository;
import FindYourThingApplication.repositories.UserRepository;
import FindYourThingApplication.services.providers.ItemProvider;
import FindYourThingApplication.services.providers.ReviewProvider;
import FindYourThingApplication.services.providers.UserProvider;
import FindYourThingApplication.services.validation.ReviewValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService
{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

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

        founder.setReviewCount(founder.getReviewCount() +1);
        founder.setTotalGradeSum(founder.getTotalGradeSum() + request.getGrade());

        Review review = reviewMapper.mapToEntity(request, reviewer, founder, item);
        Review saved = reviewRepository.save(review);
        return reviewMapper.mapToDTO(saved);
    }

    public AvgGradeResponse getUserAverageGrade(Integer userId)
    {
        User user = userProvider.getUserFromId(userId);
        Double average = user.getAvgGrade();

        return new AvgGradeResponse(
                user.getId(),
                average
        );
    }

    @Transactional
    public void deleteReview(Integer reviewId, Integer reviewerId)
    {
        Review review = reviewProvider.getReviewFromIdAndReviewerId(reviewId, reviewerId);
        reviewRepository.delete(review);
    }

    @Transactional
    public ReviewResponse editReview(Integer reviewerId, EditReviewRequest request)
    {
        Review review = reviewProvider.getReviewWithDetails(request.getReviewId());

        if(!review.getReviewer().getId().equals(reviewerId))
            throw new AccessDeniedException("You cannot edit this review");

        Integer oldGrade = review.getGrade();
        Integer newGrade = request.getGrade();

        if(!oldGrade.equals(newGrade))
        {
            User founder = review.getFounder();
            founder.setTotalGradeSum(founder.getTotalGradeSum() - oldGrade + newGrade);

            reviewMapper.updateEntity(request, review);
        }
        return reviewMapper.mapToDTO(review);
    }
}
