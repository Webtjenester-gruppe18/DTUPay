package dtu.ws18.controllers;

import dtu.ws18.models.Merchant;
import dtu.ws18.models.MerchantReportTransaction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@RestController
@RequestMapping("merchants")
public class MerchantController {
    @RequestMapping(value = "/accounts/{cpr}", method = RequestMethod.GET)
    public Merchant getMerchantByCpr(@PathVariable @NotNull String cpr) {
        return new Merchant();
    }

    @RequestMapping(value = "/reports/{cpr}", method = RequestMethod.GET)
    public ArrayList<MerchantReportTransaction> getTransactionReport(@PathVariable @NotNull String cpr) {
        return new ArrayList<>();
    }

}
