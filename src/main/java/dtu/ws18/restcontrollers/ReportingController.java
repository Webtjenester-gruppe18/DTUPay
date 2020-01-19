package dtu.ws18.restcontrollers;

import dtu.ws18.models.CustomerReportTransaction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
public class ReportingController {

    @RequestMapping(value = "/reports/{cpr}", method = RequestMethod.GET)
    public ArrayList<CustomerReportTransaction> getTransactionReportByCpr(@PathVariable @NotNull String cpr) {
        return new ArrayList<>();
    }
}
