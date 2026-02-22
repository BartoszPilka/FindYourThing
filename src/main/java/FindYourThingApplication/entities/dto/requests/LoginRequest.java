package FindYourThingApplication.entities.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest
{
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 5, max = 100, message = "Email must have 5-100 characters")
    @Email(message = "Email must have the correct format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
