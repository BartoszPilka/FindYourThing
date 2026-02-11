package FindYourThingApplication.services;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Review;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.repositories.ItemRepository;
import FindYourThingApplication.repositories.ReviewRepository;
import FindYourThingApplication.services.providers.ItemProvider;
import FindYourThingApplication.services.providers.ReviewProvider;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService
{
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    private final UserProvider userProvider;
    private final ItemProvider itemProvider;
    private  final ReviewProvider reviewProvider;


    public ReviewService(ReviewRepository reviewRepository, ItemRepository itemRepository, UserProvider userProvider, ItemProvider itemProvider, ReviewProvider reviewProvider) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
        this.userProvider = userProvider;
        this.itemProvider = itemProvider;
        this.reviewProvider = reviewProvider;
    }

    public List<Review> getUserReviews(Integer userId){
        User user = userProvider.getUserFromId(userId);
        return user.getReviewsReceived();
    }

    @Transactional
    public Integer createReview(Integer reviewerId, Integer itemId, Integer grade, Integer founderId)
    {
        if(itemId == null)
            throw new IllegalArgumentException("ID of an item cannot be null");

        if(grade == null ||grade < 1 || grade > 5)
            throw new IllegalArgumentException("Grade must be from 1 to 5");

        if(reviewerId.equals(founderId))
            throw new IllegalArgumentException("You cannot review yourself");

        Item item = itemProvider.getItemFromId(itemId);

        if (item.getFounder() == null || item.getOwner() == null) {
            throw new RuntimeException("Item must have both a founder and an owner assigned before reviewing");
        }

        if (!item.getFounder().getId().equals(founderId)) {
            throw new RuntimeException("The provided founder is not the actual finder of this item");
        }

        if (!item.getOwner().getId().equals(reviewerId)) {
            throw new RuntimeException("Only the verified owner can review this transaction");
        }

        User reviewer = userProvider.getUserFromId(reviewerId);
        if (reviewRepository.existsByReviewerIdAndItemId(reviewer.getId(), item.getId())) {
            throw new RuntimeException("You have already reviewed this item");
        }
        User founder = userProvider.getUserFromId(founderId);

        Review review = new Review();
        review.setReviewer(reviewer);
        review.setFounder(founder);
        review.setGrade(grade);
        review.setItem(item);

        reviewRepository.save(review);
        return review.getId();
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
