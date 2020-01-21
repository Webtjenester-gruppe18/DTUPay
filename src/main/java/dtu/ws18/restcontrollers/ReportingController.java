package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
import dtu.ws18.messagingutils.IEventSender;
import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
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

/**
 * @author Marcus August Christiansen - s175185
 */

@RestController
public class ReportingController {
    static CompletableFuture<ArrayList<DTUPayTransaction>> reportFuture;
    private IEventSender eventSender;

    @Autowired
    public ReportingController(IEventSender eventSender) {
        this.eventSender = eventSender;

    }

    @RequestMapping(value = "/reports/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<DTUPayTransaction>> getTransactionReportByCpr(@PathVariable @NotNull String accountId) throws Exception {
        Event requestTransactions = new Event(EventType.REQUEST_TRANSACTIONS, accountId, RabbitMQValues.PAYMENT_SERVICE_ROUTING_KEY);
        reportFuture = new CompletableFuture<>();
        this.eventSender.sendEvent(requestTransactions);
        ArrayList<DTUPayTransaction> response = reportFuture.join();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
