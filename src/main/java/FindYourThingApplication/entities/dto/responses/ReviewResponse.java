package FindYourThingApplication.entities.dto.responses;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse
{
    private Integer id;
    private Integer founderId;
    private Integer reviewerId;
    private Integer grade;
    private Integer itemId;
}
