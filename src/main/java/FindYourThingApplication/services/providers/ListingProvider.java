package FindYourThingApplication.services.providers;

import FindYourThingApplication.entities.Listing;
import FindYourThingApplication.repositories.ListingRepository;
import org.springframework.stereotype.Service;

@Service
public class ListingProvider
{
    private final ListingRepository listingRepository;

    public ListingProvider(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing getListingFromId(Integer listingId)
    {
        return listingRepository.findById(listingId).orElseThrow(
                () -> new RuntimeException("This listing doesn't exist")
        );
    }
}
