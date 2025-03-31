package mighty.youtube.central.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.ChannelFrontendDTO;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.dto.VideoDetailsDTO;
import mighty.youtube.central.service.ChannelService;
import mighty.youtube.central.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/getAllChannels")
    public ResponseEntity<List<ChannelFrontendDTO>> getAllChannels(){
        List<ChannelFrontendDTO> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }

    @DeleteMapping("/deleteChannelById/{ChannelId}")
    public void deleteChannelById(@PathVariable UUID ChannelId){
        channelService.deleteChannelById(ChannelId);
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
