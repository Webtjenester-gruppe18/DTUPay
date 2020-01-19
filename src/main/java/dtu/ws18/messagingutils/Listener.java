package dtu.ws18.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.Token;
import dtu.ws18.restcontrollers.CustomerController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class Listener {
    private ObjectMapper objectMapper;

    public Listener(ObjectMapper objectMapper){

        this.objectMapper = objectMapper;
    }
    @RabbitListener(queues = {RabbitMQValues.DTU_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {

        System.out.println(event.getType());

        if (event.getType().equals(EventType.MONEY_TRANSFER_SUCCEED)) {

            // return 200 to the rest caller

        }
        else if (event.getType().equals(EventType.MONEY_TRANSFER_FAILED)) {

            // return failure to the rest caller

        }
        else if (event.getType().equals(EventType.TOKEN_GENERATION_SUCCEED)||event.getType().equals(EventType.TOKEN_GENERATION_FAILED)) {
            CustomerController.tokenFuture.complete(event.getType());

        }
        else if (event.getType().equals(EventType.RETRIEVE_TOKENS_SUCCEED)||event.getType().equals(EventType.RETRIEVE_TOKENS_FAILED)) {
            ArrayList<Token> tokens = objectMapper.convertValue(event.getObject(),ArrayList.class);
            CustomerController.tokenListFuture.complete(tokens);
        }

    }

}
