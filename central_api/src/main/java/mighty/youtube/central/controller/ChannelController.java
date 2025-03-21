package mighty.youtube.central.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/central/channel")
@Slf4j
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @PostMapping("/create")
    public void createChannel(@RequestBody CreateChannelRequestBody channelDetails){
        log.info("create chnnel controller "+ channelDetails );
        channelService.createChannel(channelDetails);
    }
}
