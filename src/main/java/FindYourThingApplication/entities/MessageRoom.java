package FindYourThingApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message_rooms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageRoom
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "listing_id", nullable = false)
    private Integer listingId;
    @Column(name = "listing_owner_id", nullable = false)
    private Integer listingOwnerId;
    @Column(name = "item_claimant_id", nullable = false)
    private Integer itemClaimantId;
}
