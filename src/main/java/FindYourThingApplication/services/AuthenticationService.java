package FindYourThingApplication.services;

import FindYourThingApplication.auth.JwtService;
import FindYourThingApplication.configuration.SecurityConfig;
import FindYourThingApplication.entities.User;
import FindYourThingApplication.entities.dto.requests.LoginRequest;
import FindYourThingApplication.entities.dto.requests.RegisterRequest;
import FindYourThingApplication.entities.dto.responses.AuthenticationResponse;
import FindYourThingApplication.entities.enums.Role;
import FindYourThingApplication.entities.enums.UserStatus;
import FindYourThingApplication.exceptions.user.UserNotFoundException;
import FindYourThingApplication.mappers.UserMapper;
import FindYourThingApplication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final UserMapper userMapper;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request)
    {
        User user = userMapper.mapToEntity(request);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User saved = userRepository.save(user);

        return new AuthenticationResponse(
                jwtService.generateToken(saved)
        );
    }

    public AuthenticationResponse login(@Valid LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException());

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
