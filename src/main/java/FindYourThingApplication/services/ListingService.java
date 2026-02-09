package FindYourThingApplication.services;

import FindYourThingApplication.entities.Image;
import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Listing;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.ListingRequest;
import FindYourThingApplication.entities.enums.ListingStatus;
import FindYourThingApplication.entities.enums.ListingType;
import FindYourThingApplication.repositories.ImageRepository;
import FindYourThingApplication.repositories.ItemRepository;
import FindYourThingApplication.repositories.ListingRepository;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService
{
    private final ListingRepository listingRepository;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    private final UserProvider userProvider;

    public ListingService(ListingRepository listingRepository, ItemRepository itemRepository, ImageRepository imageRepository, UserProvider userProvider) {
        this.listingRepository = listingRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.userProvider = userProvider;
    }

    private void validateRequest(ListingRequest request, Integer authorId)
    {
        if(request == null)
            throw new IllegalArgumentException("Request must not be null");
        if(authorId == null)
            throw new IllegalArgumentException("ID of an author must not be null");
        if(request.getLatitude() == null || request.getLongitude() == null)
            throw new IllegalArgumentException("Both coordinates must not be null");
        if(request.getDescription() == null || request.getDescription().isBlank())
            throw new IllegalArgumentException("Description must not be empty");
        if(request.getListingType() == null || (!request.getListingType().equals(ListingType.FOUND) && !request.getListingType().equals(ListingType.LOST)))
            throw new IllegalArgumentException("Listing type must be LOST or FOUND");
    }

    @Transactional
    public Integer createListing(ListingRequest request, Integer authorId)
    {
        validateRequest(request, authorId);

        Listing listing = new Listing();
        User user = userProvider.getUserFromId(authorId);
        Item item = new Item();
        item.setImages(new ArrayList<>());

        if(request.getListingType().equals(ListingType.LOST))
        {
            item.setOwner(user);
            item.setFounder(null);
        }
        else if(request.getListingType().equals(ListingType.FOUND))
        {
            item.setFounder(user);
            item.setOwner(null);
        }

        if(request.getItemImagesUrl() != null && !request.getItemImagesUrl().isEmpty())
        {
            for(String imageUrl: request.getItemImagesUrl())
            {
                Image image = new Image();
                image.setItem(item);
                image.setImageUrl(imageUrl);
                item.getImages().add(image);
            }
        }

        listing.setAuthor(user);
        listing.setType(request.getListingType());
        listing.setDescription(request.getDescription());
        listing.setStatus(ListingStatus.ACTIVE);


        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
        listing.setLocation(gf.createPoint(new Coordinate(request.getLongitude(), request.getLatitude())));

        listing.setItem(item);
        item.setListing(listing);

        Listing savedListing = listingRepository.save(listing);

        return savedListing.getId();
    }

    public void deleteListing(){}
    public List<Listing> getUserListings(){return new ArrayList<>();
    }
}
