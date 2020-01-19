package dtu.ws18.restcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.PaymentRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private RabbitTemplate rabbitTemplate;
    public static String completed;
    private int counter;


    @Autowired
    public PaymentController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public String performPayment(@RequestBody PaymentRequest paymentRequest) throws InterruptedException {
        Event event = new Event(EventType.TOKEN_VALIDATION_REQUEST, paymentRequest);

        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY, event);
  /*      while (completed==null){
        counter++;
        }
        System.out.println(counter);*/
        return completed;
    }

    @RequestMapping(value = "/refunds", method = RequestMethod.POST)
    public String performRefund(@RequestBody DTUPayTransaction p) {
        return p.getDescription();
    }
}