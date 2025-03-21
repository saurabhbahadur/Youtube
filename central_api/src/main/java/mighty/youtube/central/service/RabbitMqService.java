package mighty.youtube.central.service;


import mighty.youtube.central.dto.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public final String exchangeName = "notification-exchange";
    public final String routingKey = "notification-123";

    public void insertMessageToQueue(NotificationMessage message){
     rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
    }
}
