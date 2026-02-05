package FindYourThingApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_owner_id", nullable = false)
    private User listingOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_claimant_id", nullable = false)
    private User itemClaimant;

    //useful connection
    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL)
    private List<Message> messages;
}
