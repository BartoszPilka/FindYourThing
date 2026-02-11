package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.Listing;
import FindYourThingApplication.entities.dto.requests.CreateListingRequest;
import FindYourThingApplication.entities.dto.requests.EditListingRequest;
import FindYourThingApplication.services.ItemService;
import FindYourThingApplication.services.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController
{
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/users/found")
    public ResponseEntity<?> getUserFoundItems(@RequestParam Integer userId)
    {
        List<Item> items;
        try
        {
            items = itemService.getUserFoundItems(userId);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/users/owned")
    public ResponseEntity<?> getUserOwnedItems(@RequestParam Integer userId)
    {
        List<Item> items;
        try
        {
            items = itemService.getUserOwnedItems(userId);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(items);
    }
}
