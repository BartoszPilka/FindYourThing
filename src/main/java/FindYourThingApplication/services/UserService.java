package FindYourThingApplication.services;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.UserRequest;
import FindYourThingApplication.entities.enums.UserStatus;
import FindYourThingApplication.repositories.UserRepository;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final UserProvider userProvider;

    public UserService(UserRepository userRepository, UserProvider userProvider) {
        this.userRepository = userRepository;
        this.userProvider = userProvider;
    }

    @Transactional
    public Integer createUser(
            UserRequest request
    ){
        if(request.getEmail() == null || request.getEmail().isBlank())
            throw new IllegalArgumentException("Email must not be empty");
        if(request.getPassword() == null || request.getPassword().isBlank())
            throw new IllegalArgumentException("Password must not be empty");
        if(request.getNickname() == null || request.getNickname().isBlank())
            throw new IllegalArgumentException("Nickname must not be empty");

        if(userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("User with this email already exists");
        if(request.getPassword().length() < 8)
            throw new IllegalArgumentException("Your password must have at least 8 characters");
        if(userRepository.existsByNickname(request.getNickname()))
            throw new RuntimeException("Your nickname must be unique");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setStatus(UserStatus.INACTIVE);
        user.setNickname(request.getNickname());

        if(user.getStatus() == null) {
            throw new RuntimeException("STATUS IS NULL BEFORE SAVE");
        }

        userRepository.save(user);
        return user.getId();
    }


    @Transactional
    public void changePassword(Integer userId, String newPassword)
    {
        if(newPassword == null || newPassword.isBlank() || newPassword.length() < 8)
            throw new IllegalArgumentException("Your new password must have at least 8 characters");

        User user = userProvider.getUserFromId(userId);

        if(newPassword.equals(user.getPassword()))
            throw new RuntimeException("New password must be different from the old one");

        user.setPassword(newPassword);
    }
}
