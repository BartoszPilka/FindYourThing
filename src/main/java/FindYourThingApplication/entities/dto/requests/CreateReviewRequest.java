package FindYourThingApplication.entities.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewRequest
{
    private Integer itemId;
    private Integer grade;
    private Integer founderId;
}
