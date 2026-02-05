package FindYourThingApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "message_room_id", nullable = false)
    private Integer messageRoomId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;
    @Column(name = "is_read", nullable = false)
    private boolean isRead;
    @Column(name = "sender_id", nullable = false)
    private Integer senderId;
}
