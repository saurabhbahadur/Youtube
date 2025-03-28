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
            }else if(message.getType().equals(NotificationType.new_video.toString())){
                log.info("New video mail called");
                commonUserService.notifyNewVideoUploadedToSubscriber(message);
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }


    }

}
