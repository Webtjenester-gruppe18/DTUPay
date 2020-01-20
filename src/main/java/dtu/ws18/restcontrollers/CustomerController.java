package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
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
public class CustomerController implements IEventReceiver {
    private CompletableFuture<String> customerDeleteFuture;
    private CompletableFuture<Customer> customerGetFuture;
    private CompletableFuture<String> customerPostFuture;
    private ObjectMapper objectMapper;
    private IEventSender eventSender;

    public CustomerController(IEventSender eventSender) {
        this.objectMapper = new ObjectMapper();
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

    @Override
    public void receiveEvent(Event event) {
        if (event.getType().equals(EventType.CREATE_CUSTOMER_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            customerPostFuture.complete(response);
        } else if (event.getType().equals(EventType.DELETE_CUSTOMER_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            customerDeleteFuture.complete(response);
        } else if (event.getType().equals(EventType.RETRIEVE_CUSTOMER_RESPONSE)) {
            Customer response = objectMapper.convertValue(event.getObject(), Customer.class);
            customerGetFuture.complete(response);
        }

    }
}








