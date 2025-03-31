package mighty.youtube.central.service;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.ChannelFrontendDTO;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.exceptions.ChannelNotFound;
import mighty.youtube.central.exceptions.UserNotFound;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.models.Channel;
import mighty.youtube.central.repository.ChannelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ChannelService {

    @Autowired
    UserService userService;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    ChannelRepo channelRepo;

    public void updateChannel(Channel channel){
        channelRepo.save(channel);
    }

    public Channel getChannelById(UUID channelId){
        return channelRepo.findById(channelId).orElse(null);
    }

    public void deleteChannelById(UUID Id){ channelRepo.deleteById(Id); }

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

    public List<ChannelFrontendDTO> getAllChannels(){
        List<Channel> channels = channelRepo.findAll();

        List<ChannelFrontendDTO> channelFrontendDTOList = new ArrayList<>();

        for (Channel channel : channels){

            ChannelFrontendDTO channelFrontendDTO = new ChannelFrontendDTO();

            channelFrontendDTO.setId(channel.getId());
            channelFrontendDTO.setName(channel.getName());
            channelFrontendDTO.setUser(channel.getUser());
            channelFrontendDTO.setDescription(channel.getDescription());
            channelFrontendDTO.setMonetized(channel.isMonetized());
            channelFrontendDTO.setCreatedAt(channel.getCreatedAt());
            channelFrontendDTO.setSubscribers(channel.getSubscribers());
            channelFrontendDTO.setPlayLists(channel.getPlayLists());
            channelFrontendDTO.setTotalSubs(channel.getTotalSubs());
            channelFrontendDTO.setTotalViews(channel.getTotalViews());
            channelFrontendDTO.setVideos(channel.getVideos());

            channelFrontendDTOList.add(channelFrontendDTO);

        }

        return channelFrontendDTOList;
    }

    public void addSubscriber(UUID userId , UUID channelId){

        AppUser user = userService.getUserById(userId);

        if(user == null){
            throw new UserNotFound(String.format("User with Id %s does not exist in the System." + userId.toString() ));
        }

        Channel channel = this.getChannelById(channelId);

        if(channel == null ){
            throw new ChannelNotFound(String.format("Channel with channel ID %s does not exist in the System"+ channelId.toString() ));
        }

        channel.setTotalSubs(channel.getTotalSubs() + 1);
        List<AppUser> subscribers = channel.getSubscribers();
        subscribers.add(user);

        channelRepo.save(channel);

        NotificationMessage message = new NotificationMessage();
        message.setType("subscriber_added");
        message.setEmail(channel.getUser().getEmail());
        message.setName(channel.getName());

        rabbitMqService.insertMessageToQueue(message);

    }





}
