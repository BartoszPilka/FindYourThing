package FindYourThingApplication.entities;

import FindYourThingApplication.entities.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Listing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ListingStatus status;
    @Column(name = "item_id", nullable = false)
    private Integer itemId;
    @Column(name = "author_id", nullable = false)
    private Integer authorId;
    @Column(name ="location", nullable = false, columnDefinition = "geography(Point,4326)")
    private Point location;
    @Column(name = "description", nullable = false)
    private String description;
}
