package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.Review;
import FindYourThingApplication.entities.dto.requests.CreateReviewRequest;
import FindYourThingApplication.entities.dto.requests.EditListingRequest;
import FindYourThingApplication.entities.dto.responses.ReviewResponse;
import FindYourThingApplication.services.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController
{
    private final ReviewService reviewService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReviewResponse>> getUserReviewsReceived(
            @Positive(message = "User ID must be positive")
            @NotNull(message = "User ID must not be null")
            @PathVariable Integer userId
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getUserReviewsReceived(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewResponse> addReview(@RequestParam Integer reviewerId, @Valid @RequestBody CreateReviewRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewerId, request));
    }

//    @GetMapping("/users/average")
//    public ResponseEntity<?> getUserAverageGrade(@RequestParam Integer userId)
//    {
//
//    }
//
//    @DeleteMapping("/")
//    public ResponseEntity<?> deleteReview(@RequestParam Integer reviewId, @RequestParam Integer reviewerId)
//    {
//
//    }
//
//    @PutMapping("/users")
//    public ResponseEntity<?> editReview(@RequestParam Integer reviewId, @RequestParam Integer reviewerId, @RequestParam Integer grade)
//    {
//
//    }
}
