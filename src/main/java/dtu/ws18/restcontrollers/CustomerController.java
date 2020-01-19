package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
@RequestMapping("/customers")
public class CustomerController {
    private RabbitTemplate rabbitTemplate;
    public static CompletableFuture<EventType> tokenFuture;
    public static CompletableFuture<ArrayList<Token>> tokenListFuture;


    @Autowired
    public CustomerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.GET)
    public Customer getCustomerByCpr(@PathVariable @NotNull String cpr) {
        return new Customer();
    }

    @RequestMapping(value = "/reports/{cpr}", method = RequestMethod.GET)
    public ArrayList<CustomerReportTransaction> getTransactionReportByCpr(@PathVariable @NotNull String cpr) {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Token>> getTokensByCpr(@PathVariable @NotNull String cpr) {
        Event tokenRequest = new Event(EventType.RETRIEVE_TOKENS, cpr);
        tokenListFuture = new CompletableFuture<>();
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY, tokenRequest);
        ArrayList<Token> response = tokenListFuture.join();
        if (response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.POST)
    public EventType createTokensByCpr(@PathVariable @NotNull String cpr) throws InterruptedException {

        Event tokenRequest = new Event(EventType.REQUEST_FOR_NEW_TOKENS, cpr);
        tokenFuture = new CompletableFuture<>();
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY, tokenRequest);
        return tokenFuture.join();
    }

}
