package FindYourThingApplication.services.providers;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.exceptions.user.UserNotFoundException;
import FindYourThingApplication.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProvider
{
    private final UserRepository userRepository;

    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromId(Integer userId)
    {
        return userRepository.findById(userId).orElseThrow(
                UserNotFoundException::new
        );
    }

    public User getUserFromEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new
        );
    }
}