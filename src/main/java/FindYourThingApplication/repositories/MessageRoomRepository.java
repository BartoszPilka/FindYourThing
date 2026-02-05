package FindYourThingApplication.repositories;

import FindYourThingApplication.entities.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Integer> {
}
