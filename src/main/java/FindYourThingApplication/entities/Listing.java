package FindYourThingApplication.entities;

import FindYourThingApplication.entities.enums.ListingStatus;
import FindYourThingApplication.entities.enums.ListingType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ListingType type;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) - nie mozna requeastowac
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false, unique = true)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name ="location", nullable = false, columnDefinition = "geography(Point,4326)")
    private Point location;

    @Column(name = "description", nullable = false)
    private String description;
}
