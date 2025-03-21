package mighty.youtube.notification.controller;


import lombok.extern.slf4j.Slf4j;
import mighty.youtube.notification.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @RabbitListener(queues = "notification-queue")
    public void consumeMessage(@Payload NotificationMessage message){
        log.info(message.toString());

    }

}
