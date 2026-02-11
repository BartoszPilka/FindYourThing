package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.Listing;
import FindYourThingApplication.entities.dto.requests.CreateListingRequest;
import FindYourThingApplication.entities.dto.requests.EditListingRequest;
import FindYourThingApplication.services.ListingService;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController
{
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createListing(@RequestBody CreateListingRequest request, @RequestParam Integer authorId)
    {
        Integer id;
        try
        {
            id = listingService.createListing(request, authorId);
        }

        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteListing(@RequestParam Integer listingId, @RequestParam Integer userId)
    {
        try
        {
            listingService.deleteListing(listingId, userId);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Listing deleted successfully");
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editListing(@RequestParam Integer listingId, @RequestBody EditListingRequest request, @RequestParam Integer authorId)
    {
        try
        {
            listingService.editListing(listingId, request, authorId);
        }
        catch(IOException e)
        {
            return ResponseEntity.badRequest().body("Error occurred while uploading an image");
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Listing edited successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserListings(@RequestParam Integer userId)
    {
        List<Listing> listings;
        try
        {
            listings = listingService.getUserListings(userId);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(listings);
    }
}
