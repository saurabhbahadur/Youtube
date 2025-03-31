package mighty.youtube.central.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.dto.VideoDetailsDTO;
import mighty.youtube.central.service.ChannelService;
import mighty.youtube.central.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/central/channel")
@Slf4j
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @Autowired
    VideoService videoService;

    @PostMapping("/create")
    public void createChannel(@RequestBody CreateChannelRequestBody channelDetails){
        log.info("create channel controller "+ channelDetails );
        channelService.createChannel(channelDetails);
    }

    @PutMapping("/{channelId}/subscribe")
    public void addSubscriber(@PathVariable UUID channelId , @RequestParam UUID userId){
        channelService.addSubscriber(userId,channelId);
    }

    @PostMapping("/{channelId}/video/upload")
    public void saveVideoDetails(@RequestBody VideoDetailsDTO videoDetailsDTO,
                                 @PathVariable UUID channelID){
        videoService.saveVideoDetails(channelID,videoDetailsDTO);
    }


}
