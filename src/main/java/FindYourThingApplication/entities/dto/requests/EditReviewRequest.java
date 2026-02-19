package FindYourThingApplication.entities.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditReviewRequest
{
    @NotNull(message = "Review ID cannot be null")
    @Positive(message = "Review ID must be positive")
    private Integer reviewId;
    @NotNull(message = "Item ID cannot be null")
    @Positive(message = "Item ID must be positive")
    private Integer itemId;
    @NotNull(message = "Grade must not be null")
    @Min(value = 1, message = "Grade must be from 1 to 5")
    @Max(value = 5, message = "Grade must be from 1 to 5")
    private Integer grade;
    @NotNull(message = "Founder ID must not be null")
    @Positive(message = "Founder ID must be positive")
    private Integer founderId;
}
