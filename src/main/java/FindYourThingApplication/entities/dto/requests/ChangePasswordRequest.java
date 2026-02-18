package FindYourThingApplication.entities.dto.requests;

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
public class ChangePasswordRequest
{
    @NotBlank(message = "Password cannot be blank")
    private String currentPassword;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 8, message = "New password must have at least 8 characters")
    private String newPassword;

    @NotBlank(message = "Confirmed password cannot be blank")
    @Size(min = 8, message = "Confirmed password must have at least 8 characters")
    private String confirmNewPassword;
}
