package dtu.ws18.controllers;

import dtu.ws18.models.Customer;
import dtu.ws18.models.CustomerReportTransaction;
import dtu.ws18.models.Merchant;
import dtu.ws18.models.MerchantReportTransaction;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable @NotNull String cpr) {
        return new Customer();
    }

    @RequestMapping(value = "/reports/{cpr}", method = RequestMethod.GET)
    public ArrayList<CustomerReportTransaction> getTransactionReport(@PathVariable @NotNull String cpr) {
        return new ArrayList<>();
    }

}
