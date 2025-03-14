package mighty.youtube.central.controller;



import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/central/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService =userService;

    }

    @PostMapping("/register")
    public void registerUser(@RequestBody AppUser user){
            userService.registerUSer(user);
    }
}
