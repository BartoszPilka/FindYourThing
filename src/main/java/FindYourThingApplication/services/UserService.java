package FindYourThingApplication.services;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.ChangePasswordRequest;
import FindYourThingApplication.entities.dto.requests.CreateUserRequest;
import FindYourThingApplication.entities.dto.responses.UserResponse;
import FindYourThingApplication.entities.enums.UserStatus;
import FindYourThingApplication.exceptions.user.DuplicatedEmailException;
import FindYourThingApplication.exceptions.user.DuplicatedNicknameException;
import FindYourThingApplication.exceptions.user.DuplicatedPasswordException;
import FindYourThingApplication.exceptions.user.PasswordMismatchException;
import FindYourThingApplication.mappers.UserMapper;
import FindYourThingApplication.repositories.UserRepository;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;

    private final UserProvider userProvider;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserProvider userProvider, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userProvider = userProvider;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request)
    {
       if(userRepository.existsByEmail(request.getEmail()))
           throw new DuplicatedEmailException();
       if(userRepository.existsByNickname(request.getNickname()))
           throw new DuplicatedNicknameException();

       User user = userMapper.mapToEntity(request);
       user.setStatus(UserStatus.INACTIVE);

       User saved = userRepository.save(user);
       return userMapper.mapToDTO(saved);
    }

    @Transactional
    public void changePassword(Integer userId, ChangePasswordRequest request)
    {
        User user = userProvider.getUserFromId(userId);
        if(!user.getPassword().equals(request.getCurrentPassword()))
            throw new PasswordMismatchException();

        if(user.getPassword().equals(request.getNewPassword()))
            throw new DuplicatedPasswordException();

        if(!request.getNewPassword().equals(request.getConfirmNewPassword()))
            throw new PasswordMismatchException();

        //hashing the new password

        user.setPassword(request.getNewPassword());
    }

    public List<UserResponse> getAllUsers()
    {
        return userRepository.findAll().stream()
                .map(user -> userMapper.mapToDTO(user))
                .toList();
    }
}
