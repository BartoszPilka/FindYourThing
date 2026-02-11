package FindYourThingApplication.entities.dto.requests;

import FindYourThingApplication.entities.enums.ListingType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CreateListingRequest
{
    private ListingType listingType;

    private Double latitude;
    private Double longitude;

    private String description;

    private List<String> itemImagesUrl;
}
