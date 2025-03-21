package mighty.youtube.central.dto;

import lombok.Data;

@Data
public class CreateChannelRequestBody {

    String userEmail;
    String channelName;
    String description;
}
