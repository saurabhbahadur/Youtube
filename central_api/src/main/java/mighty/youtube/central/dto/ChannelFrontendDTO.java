package mighty.youtube.central.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.models.PlayList;
import mighty.youtube.central.models.Video;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ChannelFrontendDTO {

    UUID id;
    @ManyToOne
    AppUser user; // channel owner
    String description;
    String name;
    Double watchHours;
    boolean isMonetized;
    int totalViews;
    int totalLikeCount;
    int totalSubs;
    @OneToMany // ChannelId vs UserId
    List<AppUser> subscribers;
    @OneToMany
    List<Video> videos;
    @OneToMany
    List<PlayList> playLists;
    LocalDateTime createdAt;

}
