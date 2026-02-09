package FindYourThingApplication.services.providers;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemProvider
{
    private final ItemRepository itemRepository;

    public ItemProvider(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemFromId(Integer itemId)
    {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("This item doesn't exist")
        );
    }
}

