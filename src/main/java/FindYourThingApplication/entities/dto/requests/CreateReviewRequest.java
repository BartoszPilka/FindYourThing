package FindYourThingApplication.entities.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest
{
    @NotNull(message = "Item ID cannot be null")
    @Positive(message = "Item id must be positive")
    private Integer itemId;
    @NotNull(message = "Grade must not be null")
    @Min(value = 1, message = "Grade must be from 1 to 5")
    @Max(value = 5, message = "Grade must be from 1 to 5")
    private Integer grade;
    @NotNull(message = "Founder ID must not be null")
    @Positive(message = "Founder id must be positive")
    private Integer founderId;
}
