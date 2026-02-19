package FindYourThingApplication.controllers.advices;

import FindYourThingApplication.ErrorResponse;
import FindYourThingApplication.exceptions.review.DuplicatedReviewException;
import FindYourThingApplication.exceptions.review.FounderMismatchException;
import FindYourThingApplication.exceptions.review.OwnerMismatchException;
import FindYourThingApplication.exceptions.review.SelfReviewException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class ReviewControllerAdvice
{
    @ExceptionHandler(DuplicatedReviewException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedReview(DuplicatedReviewException e)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(FounderMismatchException.class)
    public ResponseEntity<ErrorResponse> handleFounderMismatch(FounderMismatchException e)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(OwnerMismatchException.class)
    public ResponseEntity<ErrorResponse> handleOwnerMismatch(OwnerMismatchException e)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(SelfReviewException.class)
    public ResponseEntity<ErrorResponse> handleSelfReview(SelfReviewException e)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
