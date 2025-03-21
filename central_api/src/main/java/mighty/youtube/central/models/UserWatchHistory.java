package mighty.youtube.central.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserWatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    UUID userId;
    String videoId;
    int count;
    boolean isLiked;
    LocalDateTime lastWatched;
}