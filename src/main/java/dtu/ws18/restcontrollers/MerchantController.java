package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
import dtu.ws18.messagingutils.IEventSender;
import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.Merchant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/merchants")
public class MerchantController implements IEventReceiver {
    private CompletableFuture<String> merchantDeleteFuture;
    private CompletableFuture<Merchant> merchantGetFuture;
    private CompletableFuture<String> merchantPostFuture;
    private ObjectMapper objectMapper;
    private IEventSender eventSender;

    public MerchantController(IEventSender eventSender) {
        this.objectMapper = new ObjectMapper();
        this.eventSender = eventSender;
    }


    @RequestMapping(value = "/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<Merchant> getMerchantByCpr(@PathVariable @NotNull String cpr) throws Exception {
        Event merchantRequest = new Event(EventType.RETRIEVE_MERCHANT, cpr, RabbitMQValues.USER_SERVICE_ROUTING_KEY);
        eventSender.sendEvent(merchantRequest);
        merchantGetFuture = new CompletableFuture<>();
        Merchant merchant = merchantGetFuture.join();
        if (merchant != null) {
            return new ResponseEntity<>(merchant, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> PostMerchant(@RequestBody Merchant merchant) throws Exception {
        Event merchantRequest = new Event(EventType.CREATE_MERCHANT, merchant, RabbitMQValues.USER_SERVICE_ROUTING_KEY);
        eventSender.sendEvent(merchantRequest);
        merchantPostFuture = new CompletableFuture<>();
        String response = merchantPostFuture.join();
        if (response.equals("Created")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{cpr}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMerchantByCpr(@PathVariable @NotNull String cpr) throws Exception {
        Event merchantRequest = new Event(EventType.DELETE_MERCHANT, cpr, RabbitMQValues.USER_SERVICE_ROUTING_KEY);
        eventSender.sendEvent(merchantRequest);
        merchantDeleteFuture = new CompletableFuture<>();
        String response = merchantDeleteFuture.join();
        if (response.equals("Deleted")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public void receiveEvent(Event event) {
        if (event.getType().equals(EventType.CREATE_MERCHANT_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            merchantPostFuture.complete(response);
        } else if (event.getType().equals(EventType.DELETE_MERCHANT_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            merchantDeleteFuture.complete(response);
        } else if (event.getType().equals(EventType.RETRIEVE_MERCHANT_RESPONSE)) {
            Merchant response = objectMapper.convertValue(event.getObject(), Merchant.class);
            merchantGetFuture.complete(response);
        }
    }
}


