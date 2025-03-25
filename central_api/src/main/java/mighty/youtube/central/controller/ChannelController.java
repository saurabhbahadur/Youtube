package mighty.youtube.central.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/central/channel")
@Slf4j
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @PostMapping("/create")
    public void createChannel(@RequestBody CreateChannelRequestBody channelDetails){
        log.info("create channel controller "+ channelDetails );
        channelService.createChannel(channelDetails);
    }

    @PutMapping("/{channelId}/subscribe")
    public void addSubscriber(@PathVariable UUID channelId , @RequestParam UUID userId){
        channelService.addSubscriber(userId,channelId);
    }

}
