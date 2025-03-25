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
