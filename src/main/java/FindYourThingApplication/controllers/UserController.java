package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.enums.UserStatus;
import FindYourThingApplication.entities.dto.UserRequest;
import FindYourThingApplication.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public Integer addUser(@RequestBody UserRequest request)
    {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
        return user.getId();
    }
}
