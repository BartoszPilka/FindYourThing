package FindYourThingApplication.mappers;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.RegisterRequest;
import FindYourThingApplication.entities.dto.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "listings", ignore = true)
    @Mapping(target = "reviewsReceived", ignore = true)
    @Mapping(target = "itemsOwned", ignore = true)
    User mapToEntity(RegisterRequest request);


    UserResponse mapToDTO(User user);
}
