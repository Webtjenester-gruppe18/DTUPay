package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
import dtu.ws18.messagingutils.IEventSender;
import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.Token;
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
public class TokenController implements IEventReceiver {
    private IEventSender eventSender;
    private ObjectMapper objectMapper;
    private CompletableFuture<EventType> tokenFuture;
    private CompletableFuture<ArrayList<Token>> tokenListFuture;

    public TokenController(IEventSender eventSender) {
        this.eventSender = eventSender;
        this.objectMapper = new ObjectMapper();

    }


    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Token>> getTokensByCpr(@PathVariable @NotNull String cpr) throws Exception {
        Event tokenRequest = new Event(EventType.RETRIEVE_TOKENS, cpr, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY);
        tokenListFuture = new CompletableFuture<>();
        eventSender.sendEvent(tokenRequest);
        ArrayList<Token> response = tokenListFuture.join();
        if (response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.POST)
    public EventType createTokensByCpr(@PathVariable @NotNull String cpr) throws Exception {

        Event tokenRequest = new Event(EventType.REQUEST_FOR_NEW_TOKENS, cpr, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY);
        tokenFuture = new CompletableFuture<>();
        eventSender.sendEvent(tokenRequest);
        return tokenFuture.join();
    }

    @Override
    public void receiveEvent(Event event) {
        if (event.getType().equals(EventType.TOKEN_GENERATION_SUCCEED) || event.getType().equals(EventType.TOKEN_GENERATION_FAILED)) {
            tokenFuture.complete(event.getType());
        } else if (event.getType().equals(EventType.RETRIEVE_TOKENS_SUCCEED) || event.getType().equals(EventType.RETRIEVE_TOKENS_FAILED)) {
            ArrayList<Token> tokens = objectMapper.convertValue(event.getObject(), ArrayList.class);
            tokenListFuture.complete(tokens);
        }
    }
}
