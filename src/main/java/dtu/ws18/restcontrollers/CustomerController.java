package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private RabbitTemplate rabbitTemplate;
    public static CompletableFuture<EventType> future;
    @RabbitListener(queues = {RabbitMQValues.DTU_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {

        System.out.println(event.getType());
        CustomerController.future.complete(event.getType());
    /*    if (event.getType().equals(EventType.MONEY_TRANSFER_SUCCEED)) {

            // return 200 to the rest caller

        }
        else if (event.getType().equals(EventType.MONEY_TRANSFER_FAILED)) {

            // return failure to the rest caller

        }
        else if (event.getType().equals(EventType.TOKEN_VALIDATION_FAILED)) {

            // return failure to the rest caller

        }*/

    }
    @Autowired
    public void CustomerController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;

    }
    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable @NotNull String cpr) {
        return new Customer();
    }

    @RequestMapping(value = "/reports/{cpr}", method = RequestMethod.GET)
    public ArrayList<CustomerReportTransaction> getTransactionReport(@PathVariable @NotNull String cpr) {
        return new ArrayList<>();
    }
    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.GET)
    public ArrayList<Token> getTokens(@PathVariable @NotNull String cpr) {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/tokens/{cpr}", method = RequestMethod.POST)
    public EventType requestForNewTokens(@PathVariable @NotNull String cpr) {

        Event tokenRequest = new Event(EventType.REQUEST_FOR_NEW_TOKENS, cpr);
        future = new CompletableFuture<>();
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY, tokenRequest);
        return future.join();
    }

}
