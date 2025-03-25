<h1 align="center" > Notification-API </h1>
<p align="center"> Mail-Service</p>

***



# 📄 Documentation

## 🔧 Setup Spring Boot Project

1. **Generate a Spring Boot project** using [Spring Initializer](https://start.spring.io/).
2. **Add the following dependencies** to your `pom.xml`:

```xml
    <dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
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
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

<h1 align="center">Common Controller</h1>


## 🎮 Controller
### Common Controller (`UserController`)

```java
package mighty.youtube.notification.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.notification.dto.NotificationMessage;
import mighty.youtube.notification.enums.NotificationType;
import mighty.youtube.notification.service.CommonUserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    CommonUserService commonUserService;

    @RabbitListener(queues = "notification-queue")
    public void consumeMessage(@Payload NotificationMessage message) throws Exception {
        log.info( "Inside notification api" + message.toString());

        try{
            if(message.getType().equals(NotificationType.user_registration.toString())){
                log.info("reg mil called");
                commonUserService.sendUserRegistrationEmail(message);
            }else if(message.getType().equals(NotificationType.create_channel.toString())) {
                log.info("create channel email called");
                commonUserService.sendCreateChannelNotification(message);
            }else if(message.getType().equals(NotificationType.subscriber_added.toString())){
                log.info("subscriber added email called");
                commonUserService.sendSubscriberAddedEmail(message);
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }


    }

}

```

## 🔧 Service
### Common User Service (`CommonUserService`)

```java
package mighty.youtube.notification.service;


import jakarta.mail.internet.MimeMessage;
import mighty.youtube.notification.dto.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class CommonUserService {

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailService mailService;

    @Value("${youtube.platform.name}")
    String platformName;

    public void sendUserRegistrationEmail(NotificationMessage notificationMessage) throws Exception{
        Context context = new Context();
        context.setVariable("userName",notificationMessage.getName() );
        context.setVariable("platformName" , platformName );

        String htmlEmailContent = templateEngine.process("user-registration-email" ,context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(notificationMessage.getEmail());
        mimeMessageHelper.setSubject("Welcome to YouTube");
        mimeMessageHelper.setText(htmlEmailContent,true);

        mailService.sendEmail(mimeMessage);

    }

    public void sendCreateChannelNotification(NotificationMessage notificationMessage) throws Exception{

        Context context = new Context();
        context.setVariable("userName", notificationMessage.getName());
        context.setVariable("platformName", platformName);
        String htmlEmailContent = templateEngine.process("create-channel-email", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(notificationMessage.getEmail());
        mimeMessageHelper.setSubject("Your Channel is Live!");
        mimeMessageHelper.setText(htmlEmailContent, true);
        mailService.sendEmail(mimeMessage);
    }

    public void sendSubscriberAddedEmail(NotificationMessage message) throws Exception{
        Context context = new Context();
        context.setVariable("channelName", message.getName());
        context.setVariable("platformName" , platformName);

        String htmlTemplate =  templateEngine.process("subscriber-added" , context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setText(htmlTemplate,true);
        mimeMessageHelper.setTo(message.getEmail());
        mimeMessageHelper.setSubject("New Subscriber Added!");

        mailService.sendEmail(mimeMessage);
    }



}

```

### Mail Service (`MailService`)

```java
package mighty.youtube.notification.service;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(MimeMessage message){
        javaMailSender.send(message);
    }
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





















***
<h3 align="center">Connect with me:</h3>
<p align="center">
<a href="https://twitter.com/saurabhbahadur" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/twitter.svg" alt="saurabhbahadur" height="30" width="40" /></a>
<a href="https://linkedin.com/in/saurabhbahadur" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="saurabhbahadur" height="30" width="40" /></a>
<a href="https://fb.com/singhsaurabhbahadur" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/facebook.svg" alt="singhsaurabhbahadur" height="30" width="40" /></a>
<a href="https://instagram.com/saurabhbahadur_" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/instagram.svg" alt="saurabhbahadur_" height="30" width="40" /></a>
<a href="https://www.youtube.com/c/mighty saur" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/youtube.svg" alt="mighty saur" height="30" width="40" /></a>
<a href="https://www.hackerrank.com/saurabhbahadur" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/hackerrank.svg" alt="saurabhbahadur" height="30" width="40" /></a>
<a href="https://discord.gg/aQR27Bg7de" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/discord.svg" alt="aQR27Bg7de" height="30" width="40" /></a>
</p>
