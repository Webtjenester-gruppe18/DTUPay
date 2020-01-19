package dtu.ws18.messagingutils;

import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class Listener {

    @RabbitListener(queues = {RabbitMQValues.DTU_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {

        System.out.println(event.getType());

        if (event.getType().equals(EventType.MONEY_TRANSFER_SUCCEED)) {

            // return 200 to the rest caller

        }
        else if (event.getType().equals(EventType.MONEY_TRANSFER_FAILED)) {

            // return failure to the rest caller

        }
        else if (event.getType().equals(EventType.TOKEN_VALIDATION_FAILED)) {

            // return failure to the rest caller

        }

    }

}
