package mighty.youtube.central.service;


import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.dto.UserCredentialsDTO;
import mighty.youtube.central.dto.UserFrontendDTO;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.models.Channel;
import mighty.youtube.central.repository.AppUserRepo;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {



    AppUserRepo appUserRepo;
    RabbitMqService rabbitMqService;


    @Autowired
    public UserService(AppUserRepo appUserRepo, RabbitMqService rabbitMqService) {
        this.appUserRepo = appUserRepo;
        this.rabbitMqService = rabbitMqService;
    }

    public AppUser getUserByEmail(String email) {
        return appUserRepo.findByEmail(email);
    }

    public AppUser getUserById(UUID userId) {
        return appUserRepo.findById(userId).orElse(null);
    }

    public String loginUser(UserCredentialsDTO credentials) {
        String email = credentials.getEmail();
        AppUser user = this.getUserByEmail(email);

        if (user.getPassword().equals(credentials.getPassword())) {

            String cred = user.getEmail() + ":" + user.getPassword();
            return cred;
        }

        return "Incorrect Password";

    }

    public void registerUSer(AppUser user) {

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        appUserRepo.save(user);

        NotificationMessage message = new NotificationMessage();
        message.setEmail(user.getEmail());
        message.setType("user_registration");
        message.setName((user.getName()));
        rabbitMqService.insertMessageToQueue(message);
    }

    public List<UserFrontendDTO> getAllUsers() {

        List<AppUser> users = appUserRepo.findAll();

        List<UserFrontendDTO> userFrontendDTOList = new ArrayList<>();

        for (AppUser user : users){
            UserFrontendDTO userFrontendDTO = new UserFrontendDTO();
            userFrontendDTO.setId(user.getId());
            userFrontendDTO.setName(user.getName());
            userFrontendDTO.setEmail(user.getEmail());
            userFrontendDTO.setDob(user.getDob());
            userFrontendDTO.setGender(user.getGender());
            userFrontendDTO.setCountry(user.getCountry());

            userFrontendDTOList.add(userFrontendDTO);

        }

        return userFrontendDTOList;

    }

    public void deleteUserById(UUID Id){

        appUserRepo.deleteById(Id);
    }


}
