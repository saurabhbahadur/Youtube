package mighty.youtube.central.service;


import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    AppUserRepo appUserRepo;

    @Autowired
    public UserService(AppUserRepo appUserRepo){
        this.appUserRepo = appUserRepo;
    }

    public void registerUSer(AppUser user){
            appUserRepo.save(user);
    }


}
