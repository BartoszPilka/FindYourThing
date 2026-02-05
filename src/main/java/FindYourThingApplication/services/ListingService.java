package FindYourThingApplication.services;

import FindYourThingApplication.entities.Listing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService
{
    public Integer createListing(){return 0;}
    public void deleteListing(){}
    public List<Listing> getUserListings(){return new ArrayList<>();
    }
}
