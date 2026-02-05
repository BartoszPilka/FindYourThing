package FindYourThingApplication.entities;

import FindYourThingApplication.entities.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Column(name = "nickname", nullable = false, length = 50, unique = true)
    private String nickname;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
