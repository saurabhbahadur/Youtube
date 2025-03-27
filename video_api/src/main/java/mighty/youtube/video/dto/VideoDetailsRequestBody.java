package mighty.youtube.video.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideoDetailsRequestBody {
    String name;
    String description;
    List<String> tags;
}
