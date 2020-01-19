package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/merchants")
public class MerchantController {
    private RabbitTemplate rabbitTemplate;
    public static CompletableFuture<String> merchantDeleteFuture;
    public static CompletableFuture<Merchant> merchantGetFuture;
    public static CompletableFuture<String> merchantPostFuture;


    @Autowired
    public MerchantController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @RequestMapping(value = "/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<Merchant> getMerchantByCpr(@PathVariable @NotNull String cpr) {
        Event merchantRequest = new Event(EventType.RETRIEVE_MERCHANT, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, merchantRequest);
        Merchant merchant = merchantGetFuture.join();
        if (merchant != null) {
            return new ResponseEntity<>(merchant, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<String> PostMerchant(@RequestBody Merchant merchant) {
        Event merchantRequest = new Event(EventType.CREATE_MERCHANT, merchant);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, merchantRequest);
        String response = merchantPostFuture.join();
        if (response.equals("Created")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{cpr}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMerchantByCpr(@PathVariable @NotNull String cpr) {
        Event merchantRequest = new Event(EventType.DELETE_MERCHANT, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, merchantRequest);
        String response = merchantDeleteFuture.join();
        if (response.equals("Deleted")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}


