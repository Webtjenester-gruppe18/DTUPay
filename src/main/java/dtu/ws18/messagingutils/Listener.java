package dtu.ws18.messagingutils;

import dtu.ws18.models.Event;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class Listener {
    @RabbitListener(queues = {RabbitMQValues.DTU_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {

        System.out.println(event.getType());
    }

}
