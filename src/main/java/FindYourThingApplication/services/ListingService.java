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
import FindYourThingApplication.services.providers.ListingProvider;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
    private final ListingProvider listingProvider;

    private final GeometryFactory GF = new GeometryFactory(new PrecisionModel(), 4326);


    public ListingService(ListingRepository listingRepository, ItemRepository itemRepository, ImageRepository imageRepository, UserProvider userProvider, ListingProvider listingProvider) {
        this.listingRepository = listingRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.userProvider = userProvider;
        this.listingProvider = listingProvider;
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
        listing.setLocation(GF.createPoint(new Coordinate(request.getLongitude(), request.getLatitude())));

        listing.setItem(item);
        item.setListing(listing);

        Listing savedListing = listingRepository.save(listing);

        return savedListing.getId();
    }

    @Transactional
    public void deleteListing(Integer listingId, Integer userId)
    {
        if(listingId == null)
            throw new IllegalArgumentException("ID of a listing cannot be null");
        if(userId == null)
            throw new IllegalArgumentException("ID of an user cannot be null");

        User user = userProvider.getUserFromId(userId);
        Listing listing = listingProvider.getListingFromId(listingId);

        if(!listing.getAuthor().getId().equals(user.getId()))
            throw new RuntimeException("You cannot delete this listing");

        listingRepository.delete(listing);
    }

    @Transactional
    public Integer editListing(Integer listingId, ListingRequest request, Integer authorId)
    {
        //validateRequest() cannot be used here - we're patching the existing listing so that we validate every field differently
        if(request == null)
            throw new IllegalArgumentException("Request must not be null");
        if(authorId == null)
            throw new IllegalArgumentException("ID of an author must not be null");

        if(listingId == null)
            throw new IllegalArgumentException("ID of a listing cannot be null");

        Listing listing = listingProvider.getListingFromId(listingId);
        User author = userProvider.getUserFromId(authorId);

        if (listing.getStatus() != ListingStatus.ACTIVE)
            throw new RuntimeException("Only active listings can be edited");

        if(!listing.getAuthor().getId().equals(authorId))
            throw new RuntimeException("You cannot edit this listing");

        if(request.getListingType() != null)
        {
            if(request.getListingType() == ListingType.LOST)
            {
                listing.setType(ListingType.LOST);
                listing.getItem().setOwner(author);
                listing.getItem().setFounder(null);
            }
            else if(request.getListingType() == ListingType.FOUND)
            {
                listing.setType(ListingType.FOUND);
                listing.getItem().setOwner(null);
                listing.getItem().setFounder(author);
            }
            else
                throw new RuntimeException("Listing type must be either LOST or FOUND");
        }

        if(request.getLatitude() != null && request.getLongitude() != null)
        {
            listing.setLocation(GF.createPoint(new Coordinate(request.getLongitude(), request.getLatitude())));
        }

        if(request.getDescription() != null && !request.getDescription().isBlank())
        {
            listing.setDescription(request.getDescription());
        }

        if(request.getItemImagesUrl() != null)
        {
            listing.getItem().getImages().clear();

            for(String imageUrl : request.getItemImagesUrl())
            {
                Image image = new Image();
                image.setItem(listing.getItem());
                image.setImageUrl(imageUrl);

                listing.getItem().getImages().add(image);
            }
        }

        return listing.getId();
    }

    public List<Listing> getUserListings(Integer userId){
        if(userId == null)
            throw new IllegalArgumentException("ID of an user cannot be null");

        User user = userProvider.getUserFromId(userId);
        return user.getListings();
    }
}
