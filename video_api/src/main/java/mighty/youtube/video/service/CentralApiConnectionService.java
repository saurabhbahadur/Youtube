package mighty.youtube.video.service;


import mighty.youtube.video.dto.VideoDetailsDTO;
import mighty.youtube.video.util.ApiTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class CentralApiConnectionService {

    @Autowired
    ApiTemplate apiTemplate;

    @Value("${central.api.url}")
    String centralApiUrl;

    public void saveVideoDetails(UUID channelId , VideoDetailsDTO videoDetailsDTO){

        String endPoint = "/channel/" + channelId.toString() + "/video/upload";

        Object resp = apiTemplate.makePostCall(centralApiUrl , endPoint , new HashMap<>() , videoDetailsDTO );
    }


}
