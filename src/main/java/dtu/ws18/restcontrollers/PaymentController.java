package dtu.ws18.restendpointcontrollers;

import dtu.ws18.messagingutils.RabbitMQValues;
import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Event;
import dtu.ws18.models.EventType;
import dtu.ws18.models.Payment;
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
    public String performPayment(@RequestBody Payment payment) throws InterruptedException {
        Event event = new Event(EventType.PAYMENT_REQUEST, payment);

        this.rabbitTemplate.convertAndSend("dtupay-eventsExchange", RabbitMQValues.PAYMENT_SERVICE_ROUTING_KEY,event);
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