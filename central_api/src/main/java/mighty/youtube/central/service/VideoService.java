package mighty.youtube.central.service;


import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.dto.VideoDetailsDTO;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.models.Channel;
import mighty.youtube.central.models.Tag;
import mighty.youtube.central.models.Video;
import mighty.youtube.central.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    ChannelService channelService;

    @Autowired
    TagService tagService;

    @Autowired
    VideoRepo videoRepo;

    @Autowired
    RabbitMqService rabbitMqService;

    public void saveVideo(Video video){
        videoRepo.save(video);
    }

    public void saveVideoDetails(UUID channelId ,
                                 VideoDetailsDTO videoDetailsDTO){


        Channel channel = channelService.getChannelById(channelId);
        Video video = new Video();
        video.setId(videoDetailsDTO.getId());
        video.setVideoLink(videoDetailsDTO.getVideoLink());
        video.setUpdatedAt(videoDetailsDTO.getUpdatedAt());
        video.setUploadDateTime(videoDetailsDTO.getUploadDateTime());

        List<String> tags = videoDetailsDTO.getTags();

        List<Tag> dbTagList = tagService.getAllTagsFromSystem(tags);

        video.setTags(dbTagList);
        this.saveVideo(video);

        channel.getVideos().add(video);
        channelService.updateChannel(channel);

        this.notifySubscibers(channel.getSubscribers());

    }

    public void notifySubscibers(List<AppUser> subscribers){
        for(int i =0; i <subscribers.size(); i++){
            AppUser subscriber = subscribers.get(i);

            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setName(subscriber.getName());
            notificationMessage.setType("new-video");
            notificationMessage.setEmail(subscriber.getEmail());
            rabbitMqService.insertMessageToQueue(notificationMessage);
        }
    }


}
