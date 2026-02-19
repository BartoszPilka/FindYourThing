package FindYourThingApplication.mappers;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Review;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.CreateReviewRequest;
import FindYourThingApplication.entities.dto.requests.EditReviewRequest;
import FindYourThingApplication.entities.dto.responses.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.grade", target = "grade")
    Review mapToEntity(CreateReviewRequest request, User reviewer, User founder, Item item);

    @Mapping(source = "request.id", target = "id")
    @Mapping(source = "request.grade", target = "grade")
    Review updateEntity(EditReviewRequest request, User reviewer, User founder, Item item);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "founder.id", target = "founderId")
    @Mapping(source = "reviewer.id", target = "reviewerId")
    ReviewResponse mapToDTO(Review review);
}
