package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.IEventSender;
import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.Customer;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    static CompletableFuture<String> customerDeleteFuture;
    static CompletableFuture<Customer> customerGetFuture;
    static CompletableFuture<String> customerPostFuture;
    private IEventSender eventSender;

    public CustomerController(IEventSender eventSender) {
        this.eventSender = eventSender;
    }

    @RequestMapping(value = "/{cpr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerByCpr(@PathVariable @NotNull String cpr) throws Exception {
        Event customerRequest = new Event(EventType.RETRIEVE_CUSTOMER, cpr, RabbitMQValues.USER_SERVICE_ROUTING_KEY);
        this.eventSender.sendEvent(customerRequest);
        customerGetFuture = new CompletableFuture<>();
        Customer customer = customerGetFuture.join();
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer with that cpr not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> PostCustomer(@RequestBody Customer customer) throws Exception {
        Event customerRequest = new Event(EventType.CREATE_CUSTOMER, customer, RabbitMQValues.USER_SERVICE_ROUTING_KEY);
        this.eventSender.sendEvent(customerRequest);
        customerPostFuture = new CompletableFuture<>();
        String response = customerPostFuture.join();
        if (response.equals("Created")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{cpr}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomerByCpr(@PathVariable @NotNull String cpr) throws Exception {
        Event customerRequest = new Event(EventType.DELETE_CUSTOMER, cpr, RabbitMQValues.USER_SERVICE_ROUTING_KEY);
        this.eventSender.sendEvent(customerRequest);
        customerDeleteFuture = new CompletableFuture<>();
        String response = customerDeleteFuture.join();
        if (response.equals("Deleted")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}








