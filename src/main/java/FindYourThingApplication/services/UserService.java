package FindYourThingApplication.services;

import FindYourThingApplication.entities.User;
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
            String email,
            String password,
            String nickname
    ){
        if(email == null || email.isBlank())
            throw new IllegalArgumentException("Email must not be empty");
        if(password == null || password.isBlank())
            throw new IllegalArgumentException("Password must not be empty");
        if(nickname == null || nickname.isBlank())
            throw new IllegalArgumentException("Nickname must not be empty");

        if(userRepository.existsByEmail(email))
            throw new IllegalArgumentException("User with this email already exists");
        if(password.length() < 8)
            throw new IllegalArgumentException("Your password must have at least 8 characters");
        if(userRepository.existsByNickname(nickname))
            throw new IllegalArgumentException("Your nickname must be unique");

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setStatus(UserStatus.INACTIVE);
        user.setNickname(nickname);

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
            throw new IllegalArgumentException("New password must be different from the old one");

        user.setPassword(newPassword);
    }
}
