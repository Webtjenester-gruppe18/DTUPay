package dtu.ws18.controllers;

import dtu.ws18.models.DTUPayTransaction;
import dtu.ws18.models.Payment;
import dtu.ws18.rabbitmq.RabbitMQValues;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class PaymentController {
    private RabbitTemplate rabbitTemplate;
    private CompletableFuture<String> future;
    private static final String queueName = "response-queue";


    @Autowired
    public PaymentController(RabbitTemplate rabbitTemplate){
    this.rabbitTemplate= rabbitTemplate;

    }
    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public String performPayment(@RequestBody Payment p) {
        this.rabbitTemplate.convertAndSend(RabbitMQValues.topicExchangeName,"pay","Amount: " + p.getAmount());
        return p.getDescription();
    }
    @RequestMapping(value = "/refunds", method = RequestMethod.POST)
    public String performRefund(@RequestBody DTUPayTransaction p) {
        return p.getDescription();
    }
}