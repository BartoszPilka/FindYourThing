package FindYourThingApplication.entities;

import FindYourThingApplication.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "sender_id", nullable = false)
    private Integer senderId;
    @Column(name = "receiver_id", nullable = false)
    private Integer receiverId;
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(name = "listing_id", nullable = false)
    private Integer listingId;
    @Column(name = "status", nullable = false)
    private PaymentStatus status;
    @Column(name = "provider", nullable = false, length = 50)
    private String provider;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "paid_at", nullable = true)
    private Instant paidAt;
}
