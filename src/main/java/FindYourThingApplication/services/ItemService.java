package FindYourThingApplication.services;

import FindYourThingApplication.entities.Item;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.repositories.ItemRepository;
import FindYourThingApplication.services.providers.UserProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService
{
    private final ItemRepository itemRepository;

    private final UserProvider userProvider;

    public ItemService(ItemRepository itemRepository, UserProvider userProvider) {
        this.itemRepository = itemRepository;
        this.userProvider = userProvider;
    }

    public List<Item> getUserFoundItems(Integer userId)
    {
        if(userId == null)
            throw new IllegalArgumentException("ID of an user cannot be null");
        User user = userProvider.getUserFromId(userId);

        return itemRepository.findByFounderId(user.getId());
    }

    public List<Item> getUserOwnedItems(Integer userId)
    {
        if(userId == null)
            throw new IllegalArgumentException("ID of an user cannot be null");
        User user = userProvider.getUserFromId(userId);

        return itemRepository.findByOwnerId(user.getId());
    }
}
