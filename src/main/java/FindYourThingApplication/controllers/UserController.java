package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.dto.requests.ChangePasswordRequest;
import FindYourThingApplication.entities.dto.responses.UserResponse;
import FindYourThingApplication.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestParam Integer userId, @Valid @RequestBody ChangePasswordRequest request)
    {
        userService.changePassword(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
}
