package FindYourThingApplication.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "founder_id", nullable = false)
    private Integer founderId;
    @Column(name = "reviewer_id", nullable = false)
    private Integer reviewerId;
    @Column(name = "grade", nullable = false)
    @Min(1)
    @Max(5)
    private Integer grade;
}
