package mighty.youtube.video.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VideoDetailsDTO {

    String id; // This id will get generated inside firebase
    String name;
    String description;
    LocalDateTime uploadDateTime;
    LocalDateTime updatedAt;
    String videoLink;
    List<String> tags;

}
