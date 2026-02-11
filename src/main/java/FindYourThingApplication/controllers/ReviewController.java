package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.Review;
import FindYourThingApplication.entities.dto.requests.CreateReviewRequest;
import FindYourThingApplication.entities.dto.requests.EditListingRequest;
import FindYourThingApplication.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController
{
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/user")                            //token
    public ResponseEntity<?> getUserReviewsReceived(@RequestParam Integer userId)
    {
        List<Review> reviews;
        try
        {
            reviews = reviewService.getUserReviewsReceived(userId);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestParam Integer reviewerId, @RequestBody CreateReviewRequest request)
    {
        Integer id;
        try
        {
            id = reviewService.createReview(reviewerId, request);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(id);
    }

    @GetMapping("/users/average")
    public ResponseEntity<?> getUserAverageGrade(@RequestParam Integer userId)
    {
        Double avg;
        try
        {
            avg = reviewService.getUserAverageGrade(userId);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(avg);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteReview(@RequestParam Integer reviewId, @RequestParam Integer reviewerId)
    {
        try
        {
            reviewService.deleteReview(reviewId, reviewerId);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Review deleted successfully");
    }

    @PutMapping("/users/average")
    public ResponseEntity<?> editReview(@RequestParam Integer reviewId, @RequestParam Integer reviewerId, @RequestParam Integer grade)
    {
        try
        {
            reviewService.editReview(reviewId, reviewerId, grade);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Review edited successfully");
    }
}
