package FindYourThingApplication.services;

import FindYourThingApplication.entities.Review;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService
{
    public List<Review> getUserReviews(){return new ArrayList<>();
    }
    public Integer addReview(){return 0;}
    public BigDecimal getUserAverageGrade(){return BigDecimal.ONE;}
    public void deleteReview(){}
    public void editReview(){}
}
