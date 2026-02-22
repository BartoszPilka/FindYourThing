package FindYourThingApplication.entities;

import FindYourThingApplication.entities.enums.Role;
import FindYourThingApplication.entities.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails
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

    //security
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    //helpful relation mapping
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Listing> listings = new ArrayList<>();

    @OneToMany(mappedBy = "founder")
    private List<Review> reviewsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Item> itemsOwned = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
