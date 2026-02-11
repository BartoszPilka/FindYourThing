package FindYourThingApplication.repositories;

import FindYourThingApplication.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>
{
    List<Item> findByFounderId(Integer founderId);
    List<Item> findByOwnerId(Integer ownerId);
}
