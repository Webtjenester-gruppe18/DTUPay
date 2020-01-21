package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventSender;
import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.Token;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IEventSender eventSender;
    static CompletableFuture<Event> tokenFuture;
    static CompletableFuture<ArrayList<Token>> tokenListFuture;
    private ObjectMapper mapper;

    @Autowired
    public TokenController(IEventSender eventSender) {
        this.eventSender = eventSender;
        this.mapper = new ObjectMapper();
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
    public ResponseEntity<Object> createTokensByCpr(@PathVariable @NotNull String cpr) throws Exception {

        Event tokenRequest = new Event(EventType.REQUEST_FOR_NEW_TOKENS, cpr, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY);
        tokenFuture = new CompletableFuture<>();
        eventSender.sendEvent(tokenRequest);

        Event event = tokenFuture.join();

        if (event.getType().equals(EventType.TOKEN_GENERATION_RESPONSE_SUCCESS)) {
            return new ResponseEntity<>("Tokens is generated.", HttpStatus.OK);
        }

        String error = mapper.convertValue(event.getObject(), String.class);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
