<h1 align="center">Central-API</h1>
<p align="center">User & Channel Management</p>

***

# 📄 Documentation

## 🔧 Setup Spring Boot Project

1. **Generate a Spring Boot project** using [Spring Initializer](https://start.spring.io/).
2. **Add the following dependencies** to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
        <version>3.4.0</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

<h1 align="center">Users</h1>

## 🏗️ Create Models
### User Model (`AppUser`)

```java
package mighty.youtube.central.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private Long phoneNumber;

    private LocalDate dob;
    private String gender;
    private String country;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

## 🎮 Controller
### User Controller (`UserController`)

```java
package mighty.youtube.central.controller;

import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/central/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody AppUser user) {
        userService.registerUser(user);
    }
}
```

## 🔧 Service
### User Service (`UserService`)

```java
package mighty.youtube.central.service;

import mighty.youtube.central.dto.NotificationMessage;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final AppUserRepo appUserRepo;
    private final RabbitMqService rabbitMqService;

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

    public void registerUser(AppUser user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        appUserRepo.save(user);

        NotificationMessage message = new NotificationMessage();
        message.setEmail(user.getEmail());
        message.setType("user_registration");
        message.setName(user.getName());
        rabbitMqService.insertMessageToQueue(message);
    }
}
```

## 📂 Repository
### User Repository (`AppUserRepo`)

```java
package mighty.youtube.central.repository;

import mighty.youtube.central.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, UUID> {
    AppUser findByEmail(String email);
}
```

## 📌 DTO
### Notification Message (`NotificationMessage`)

```java
package mighty.youtube.central.dto;

import lombok.Data;

@Data
public class NotificationMessage {
    private String type;
    private String email;
    private String name;
}
```


<h1 align="center">Channel</h1>

## 🏗️ Create Models
### Channel Model (`Channel`)

```java
package mighty.youtube.central.models;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    AppUser user; // channel owner
    String description;
    String name;
    Double watchHours;
    boolean isMonetized;
    int totalViews;
    int totalLikeCount;
    int totalSubs;
    @OneToMany // ChannelId vs UserId
    List<AppUser> subscribers;
    @OneToMany
    List<Video> videos;
    @OneToMany
    List<PlayList> playLists;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
```

## 🎮 Controller
### Channel Controller (`ChannelController`)

```java

package mighty.youtube.central.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.central.dto.CreateChannelRequestBody;
import mighty.youtube.central.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/central/channel")
@Slf4j
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @PostMapping("/create")
    public void createChannel(@RequestBody CreateChannelRequestBody channelDetails){
        log.info("create channel controller "+ channelDetails );
        channelService.createChannel(channelDetails);
    }

    @PutMapping("/{channelId}/subscribe")
    public void addSubscriber(@PathVariable UUID channelId , @RequestParam UUID userId){
        channelService.addSubscriber(userId,channelId);
    }

}

```

## 🔧 Service
### Channel Service (`ChannelService`)

```java
package mighty.youtube.central.service;


import lombok.extern.slf4j.Slf4j;
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

    public Channel getChannelById(UUID channelId){
        return channelRepo.findById(channelId).orElse(null);
    }

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
```

## 📂 Repository
### Channel Repository (`ChannelRepo`)

```java
package mighty.youtube.central.repository;


import mighty.youtube.central.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChannelRepo extends JpaRepository<Channel , UUID> {
}

```

## 📌 DTO
### Create Channel  (`CreateChannelRrequestBody`)

```java
package mighty.youtube.central.dto;

import lombok.Data;

@Data
public class CreateChannelRequestBody {

    String userEmail;
    String channelName;
    String description;
}

```




---

## 📢 Connect with Me

<p align="center">
    <a href="https://twitter.com/saurabhbahadur" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/twitter.svg" height="30" width="40" />
    </a>
    <a href="https://linkedin.com/in/saurabhbahadur" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" height="30" width="40" />
    </a>
    <a href="https://fb.com/singhsaurabhbahadur" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/facebook.svg" height="30" width="40" />
    </a>
    <a href="https://instagram.com/saurabhbahadur_" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/instagram.svg" height="30" width="40" />
    </a>
    <a href="https://www.youtube.com/c/mightysaur" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/youtube.svg" height="30" width="40" />
    </a>
    <a href="https://www.hackerrank.com/saurabhbahadur" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/hackerrank.svg" height="30" width="40" />
    </a>
    <a href="https://discord.gg/aQR27Bg7de" target="blank">
        <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/discord.svg" height="30" width="40" />
    </a>
</p>

