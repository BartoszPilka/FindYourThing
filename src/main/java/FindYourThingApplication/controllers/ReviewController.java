package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.CreateReviewRequest;
import FindYourThingApplication.entities.dto.requests.EditReviewRequest;
import FindYourThingApplication.entities.dto.responses.AvgGradeResponse;
import FindYourThingApplication.entities.dto.responses.ReviewResponse;
import FindYourThingApplication.services.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<ReviewResponse> addReview(
            @AuthenticationPrincipal User reviewer,
            @Valid @RequestBody CreateReviewRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewer.getId(), request));
    }

    @GetMapping("/{userId}/average")
    public ResponseEntity<AvgGradeResponse> getUserAverageGrade(
            @Positive(message = "User ID must be positive") @PathVariable Integer userId
    )
    {
        return ResponseEntity.ok().body(reviewService.getUserAverageGrade(userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal User reviewer,
            @Positive(message = "Review ID must be positive") @RequestParam Integer reviewId
    )
    {
        reviewService.deleteReview(reviewId, reviewer.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<ReviewResponse> editReview(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody EditReviewRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.editReview(user.getId(), request));
    }
}
