package dtu.ws18.controllers;

import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Payment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public String performPayment(@RequestBody Payment p) {
        return p.getDescription();
    }
    @RequestMapping(value = "/refunds", method = RequestMethod.POST)
    public String performRefund(@RequestBody DTUPayTransaction p) {
        return p.getDescription();
    }
}