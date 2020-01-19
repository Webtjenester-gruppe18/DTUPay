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
    public static CompletableFuture<String> customerDeleteFuture;
    public static CompletableFuture<Customer> customerGetFuture;
    public static CompletableFuture<String> customerPostFuture;


    @Autowired
    public CustomerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.GET)
    public Customer getCustomerByCpr(@PathVariable @NotNull String cpr) {
        Event customerRequest = new Event(EventType.RETRIEVE_CUSTOMER, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, customerRequest);

        return new Customer();
    }

    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.POST)
    public Customer PostCustomerByCpr(@PathVariable @NotNull String cpr) {
        Event customerRequest = new Event(EventType.DELETE_CUSTOMER, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, customerRequest);

        return new Customer();
    }

    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.DELETE)
    public Customer deleteCustomerByCpr(@PathVariable @NotNull String cpr) {
        Event customerRequest = new Event(EventType.DELETE_CUSTOMER, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, customerRequest);

        return new Customer();
    }







}
