package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.Customer;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @RequestMapping(value = "/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerByCpr(@PathVariable @NotNull String cpr) {
        Event customerRequest = new Event(EventType.RETRIEVE_CUSTOMER, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, customerRequest);
        Customer customer = customerGetFuture.join();
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer with that cpr not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> PostCustomer(@RequestBody Customer customer) {
        Event customerRequest = new Event(EventType.CREATE_CUSTOMER, customer);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, customerRequest);
        String response = customerPostFuture.join();
        if (response.equals("Created")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{cpr}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomerByCpr(@PathVariable @NotNull String cpr) {
        Event customerRequest = new Event(EventType.DELETE_CUSTOMER, cpr);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.USER_SERVICE_ROUTING_KEY, customerRequest);
        String response = customerDeleteFuture.join();
        if (response.equals("Deleted")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}








