package FindYourThingApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "founder_id", nullable = true)
    private User founder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    @OneToOne(mappedBy = "item")
    private Listing listing;

    //useful connection
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
