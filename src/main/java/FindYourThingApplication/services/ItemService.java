package FindYourThingApplication.services;

import FindYourThingApplication.entities.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService
{
    public Integer addItemToListing()
    {
        return 0;
    }
    public void deleteItem(){}
    public List<Item> getUserFoundItems()
    {
        return new ArrayList<>();
    }
    public List<Item> getUserOwnedItems()
    {
        return new ArrayList<>();
    }
}
