package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
import dtu.ws18.messagingutils.IEventSender;
import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class PaymentController {
    static CompletableFuture<Event> paymentFuture;
    static CompletableFuture<Event> refundFuture;
    private ObjectMapper objectMapper;
    private IEventSender eventSender;

    @Autowired
    public PaymentController(IEventSender eventSender) {
        this.objectMapper = new ObjectMapper();
        this.eventSender = eventSender;
    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) throws Exception {
        Event event = new Event(EventType.TOKEN_VALIDATION_REQUEST, paymentRequest, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY);
        paymentFuture = new CompletableFuture<>();
        eventSender.sendEvent(event);
        Event responseEvent = paymentFuture.join();
        String response = objectMapper.convertValue(responseEvent.getObject(), String.class);
        if (responseEvent.getType().equals(EventType.MONEY_TRANSFER_SUCCEED)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (responseEvent.getType().equals(EventType.MONEY_TRANSFER_FAILED)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/refunds", method = RequestMethod.POST)
    public ResponseEntity<String> createRefund(@RequestBody DTUPayTransaction transaction) throws Exception {

        Event requestEvent = new Event(EventType.REFUND_REQUEST, transaction, RabbitMQValues.PAYMENT_SERVICE_ROUTING_KEY);
        refundFuture = new CompletableFuture<>();
        eventSender.sendEvent(requestEvent);
        Event event = refundFuture.join();
        String response = objectMapper.convertValue(event.getObject(), String.class);

        if (event.getType().equals(EventType.REFUND_REQUEST_RESPONSE)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}