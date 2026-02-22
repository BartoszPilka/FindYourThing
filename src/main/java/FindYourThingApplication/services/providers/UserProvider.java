package FindYourThingApplication.services.providers;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.exceptions.user.UserNotFoundException;
import FindYourThingApplication.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserProvider implements UserDetailsService
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

    //byUsername in the definition actually means BY EMAIL!!!!
    //so for using this method we have to pass email, not nickname
    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new
        );
    }
}