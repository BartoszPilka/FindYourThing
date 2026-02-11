package FindYourThingApplication.controllers;

import FindYourThingApplication.entities.dto.requests.UserRequest;
import FindYourThingApplication.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest request)
    {
        Integer userId;
        try
        {
            userId = userService.createUser(request);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/changePassword")              //authorization token todo
    public ResponseEntity<String> changePassword(@RequestParam Integer userId, @RequestParam String password)
    {
        try
        {
            userService.changePassword(userId, password);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Password changed successfully");
    }
}
