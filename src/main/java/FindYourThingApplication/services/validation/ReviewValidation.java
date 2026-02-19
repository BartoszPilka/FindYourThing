package FindYourThingApplication.services.validation;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.exceptions.review.DuplicatedReviewException;
import FindYourThingApplication.exceptions.review.FounderMismatchException;
import FindYourThingApplication.exceptions.review.OwnerMismatchException;
import FindYourThingApplication.exceptions.review.SelfReviewException;
import FindYourThingApplication.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewValidation
{
    private final ReviewRepository reviewRepository;

    public void validateCreateReview(Item item, User founder, User reviewer)
    {
        if(reviewer.getId().equals(founder.getId()))
            throw new SelfReviewException();
        if(!reviewer.getId().equals(item.getOwner().getId()))
            throw new OwnerMismatchException();
        if(!item.getFounder().getId().equals(founder.getId()))
            throw new FounderMismatchException();
        if(reviewRepository.existsByReviewerIdAndItemId(reviewer.getId(), item.getId()))
            throw new DuplicatedReviewException();
    }
}
