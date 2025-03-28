package mighty.youtube.central.service;


import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.dto.UserCredentialsDTO;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.repository.AppUserRepo;
import mighty.youtube.central.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    JwtUtil jwtUtil;

    AppUserRepo appUserRepo;
    RabbitMqService rabbitMqService;



    @Autowired
    public UserService(AppUserRepo appUserRepo , RabbitMqService rabbitMqService){
        this.appUserRepo = appUserRepo;
        this.rabbitMqService = rabbitMqService;
    }

    public AppUser getUserByEmail(String email){
        return appUserRepo.findByEmail(email);
    }

    public AppUser getUserById(UUID userId){
        return appUserRepo.findById(userId).orElse(null);
    }

    public String loginUser(UserCredentialsDTO credentials){
        String email = credentials.getEmail();
        AppUser user = this.getUserByEmail(email);

        if(user.getPassword().equals(credentials.getPassword())) {

            String cred = user.getEmail() + ":" + user.getPassword();
            return jwtUtil.generateToken(cred);
        }
        
        return "Incorrect Password";

    }

    public void registerUSer(AppUser user){

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
            appUserRepo.save(user);

        NotificationMessage message = new NotificationMessage();
        message.setEmail(user.getEmail());
        message.setType("user_registration");
        message.setName((user.getName()));
        rabbitMqService.insertMessageToQueue(message);
    }


}
