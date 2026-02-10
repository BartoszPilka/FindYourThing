package FindYourThingApplication.entities.dto.requests;

import FindYourThingApplication.entities.enums.ListingStatus;
import FindYourThingApplication.entities.enums.ListingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListingRequest
{
    private ListingType listingType;

    private Double latitude;
    private Double longitude;

    private String description;

    private List<String> itemImagesUrl;
}
