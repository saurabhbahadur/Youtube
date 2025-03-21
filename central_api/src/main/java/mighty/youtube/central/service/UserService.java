package mighty.youtube.central.service;


import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    AppUserRepo appUserRepo;
    RabbitMqService rabbitMqService;



    @Autowired
    public UserService(AppUserRepo appUserRepo , RabbitMqService rabbitMqService){
        this.appUserRepo = appUserRepo;
        this.rabbitMqService = rabbitMqService;
    }

    public void registerUSer(AppUser user){

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
            appUserRepo.save(user);

        NotificationMessage message = new NotificationMessage();
        message.setEmail(user.getEmail());
        message.setType("user-registration");
        message.setName((user.getName()));
        rabbitMqService.insertMessageToQueue(message);
    }


}
