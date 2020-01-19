package dtu.ws18.restcontrollers;

import dtu.ws18.models.Customer;
import dtu.ws18.models.CustomerReportTransaction;
import dtu.ws18.models.Token;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private RabbitTemplate rabbitTemplate;

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

}
