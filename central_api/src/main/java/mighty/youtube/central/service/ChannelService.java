package mighty.youtube.central.service;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.exceptions.UserNotFound;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.models.Channel;
import mighty.youtube.central.repository.ChannelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ChannelService {

    @Autowired
    UserService userService;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    ChannelRepo channelRepo;

    public void createChannel(CreateChannelRequestBody channelDetails){
        String email = channelDetails.getUserEmail();
        log.info("Received userEmail: " + channelDetails.getUserEmail());


        AppUser user = userService.getUserByEmail(email);

        if(user == null){
            throw new UserNotFound(String.format("User with this email %s does not exist in system", email));
        }

        Channel channel = new Channel();
        channel.setCreatedAt(LocalDateTime.now());
        channel.setUpdatedAt(LocalDateTime.now());
        channel.setMonetized(false);
        channel.setUser(user);
        channel.setDescription(channelDetails.getDescription());
        channel.setName(channelDetails.getChannelName());

        channelRepo.save(channel);

        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setName(user.getName());
        notificationMessage.setEmail(user.getEmail());
        notificationMessage.setType("create_channel");
        rabbitMqService.insertMessageToQueue(notificationMessage);


    }





}
