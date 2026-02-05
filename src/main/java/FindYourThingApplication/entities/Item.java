package FindYourThingApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "founder_id", nullable = true)
    private Integer founderId;
    @Column(name = "owner_id", nullable = true)
    private Integer ownerId;
    @Column(name = "listing_id", nullable = false)
    private Integer listingId;
}
