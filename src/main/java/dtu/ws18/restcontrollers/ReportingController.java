package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@RestController
public class ReportingController {

    private RabbitTemplate rabbitTemplate;
    public static CompletableFuture<ArrayList<DTUPayTransaction>> reportFuture;

    @Autowired
    public ReportingController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @RequestMapping(value = "/reports/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<DTUPayTransaction>> getTransactionReportByCpr(@PathVariable @NotNull String accountId) {

        Event requestTransactions = new Event(EventType.REQUEST_TRANSACTIONS, accountId, RabbitMQValues.PAYMENT_SERVICE_ROUTING_KEY);

        reportFuture = new CompletableFuture<>();

        this.rabbitTemplate.convertAndSend(
                RabbitMQValues.TOPIC_EXCHANGE_NAME,
                RabbitMQValues.PAYMENT_SERVICE_ROUTING_KEY,
                requestTransactions);

        ArrayList<DTUPayTransaction> response = reportFuture.join();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
