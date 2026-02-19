package FindYourThingApplication.entities.dto.requests;

import FindYourThingApplication.entities.enums.ListingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateListingRequest
{
    private ListingType listingType;

    private Double latitude;
    private Double longitude;

    private String description;

    private List<String> itemImagesUrl;
}
