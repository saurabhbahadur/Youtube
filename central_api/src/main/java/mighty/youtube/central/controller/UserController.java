package mighty.youtube.central.controller;


import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.dto.UserCredentialsDTO;
import mighty.youtube.central.dto.UserFrontendDTO;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.security.JwtUtil;
import mighty.youtube.central.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/central/user")
public class UserController {

    @Autowired
    JwtUtil jwtUtil;

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public String registerUser(@RequestBody AppUser user) {
        userService.registerUSer(user);
        String credentials = user.getEmail() + ":" + user.getPassword();
        return jwtUtil.generateToken(credentials);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserCredentialsDTO credentials) {

        String resp = userService.loginUser(credentials);
        if (resp.equals("Incorrect Password")) {
            return new ResponseEntity("Incorrect Password", HttpStatus.UNAUTHORIZED);
        } else {
            String token = jwtUtil.generateToken(resp);
            return new ResponseEntity(token, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserFrontendDTO>> getAllUsers(){
        List<UserFrontendDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("/delete/{Id}")
    public void deleteUserById(@PathVariable UUID Id){
        userService.deleteUserById(Id);
    }

}
