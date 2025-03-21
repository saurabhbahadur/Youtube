package mighty.youtube.central.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "videos")
public class Video {
    @Id
    String id; // This id will get generated inside firebase
    String name;
    String description;
    LocalDateTime uploadDateTime;
    LocalDateTime updatedAt;
    String videoLink;
    String thumbnailLink;
    @OneToMany
    List<Tag> tags;
}