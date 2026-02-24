package FindYourThingApplication.services;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.ChangePasswordRequest;
import FindYourThingApplication.entities.dto.requests.RegisterRequest;
import FindYourThingApplication.entities.dto.responses.UserResponse;
import FindYourThingApplication.entities.enums.UserStatus;
import FindYourThingApplication.exceptions.user.DuplicatedPasswordException;
import FindYourThingApplication.exceptions.user.PasswordMismatchException;
import FindYourThingApplication.mappers.UserMapper;
import FindYourThingApplication.repositories.UserRepository;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    private final UserProvider userProvider;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse createUser(RegisterRequest request)
    {
       User user = userMapper.mapToEntity(request);
       user.setStatus(UserStatus.INACTIVE);

       User saved = userRepository.save(user);
       return userMapper.mapToDTO(saved);
    }

    @Transactional
    public void changePassword(Integer userId, ChangePasswordRequest request)
    {
        User user = userProvider.getUserFromId(userId);

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
            throw new PasswordMismatchException();

        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword()))
            throw new DuplicatedPasswordException();

        if(!request.getNewPassword().equals(request.getConfirmNewPassword()))
            throw new PasswordMismatchException();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    public List<UserResponse> getAllUsers()
    {
        return userRepository.findAll().stream()
                .map(userMapper::mapToDTO)
                .toList();
    }
}
