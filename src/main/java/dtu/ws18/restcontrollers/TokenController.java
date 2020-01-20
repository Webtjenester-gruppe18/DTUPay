package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.Token;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@RestController
public class TokenController {
    private RabbitTemplate rabbitTemplate;
    public static CompletableFuture<EventType> tokenFuture;
    public static CompletableFuture<ArrayList<Token>> tokenListFuture;

    public TokenController(RabbitTemplate rabbitTemplate) {


        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Token>> getTokensByCpr(@PathVariable @NotNull String cpr) {
        Event tokenRequest = new Event(EventType.RETRIEVE_TOKENS, cpr, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY);
        tokenListFuture = new CompletableFuture<>();
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY, tokenRequest);
        ArrayList<Token> response = tokenListFuture.join();
        if (response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.POST)
    public EventType createTokensByCpr(@PathVariable @NotNull String cpr) throws InterruptedException {

        Event tokenRequest = new Event(EventType.REQUEST_FOR_NEW_TOKENS, cpr, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY);
        tokenFuture = new CompletableFuture<>();
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY, tokenRequest);
        return tokenFuture.join();
    }

}
