package dtu.ws18.messagingutils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dtu.ws18.models.Event;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
Boilerplate from @Author Hubert Baumeister demoproject
Replaced old code, to allow cucumber tests to work properly
@Service
public class Listener {
    private ObjectMapper objectMapper;
    public Listener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @RabbitListener(queues = {RabbitMQValues.DTU_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) { }
*/

public class EventReceiverImpl {
    private IEventReceiver eventReceiver;

    public EventReceiverImpl(IEventReceiver eventReceiver) {
        this.eventReceiver = eventReceiver;

    }
    public void listen() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueBind(RabbitMQValues.DTU_SERVICE_QUEUE_NAME, RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY); //Change Queuename and routing key
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Event event = new Gson().fromJson(message, Event.class);
            try {
                eventReceiver.receiveEvent(event);
            } catch (Exception e) {
                throw new Error(e);
            }
        };
        channel.basicConsume(RabbitMQValues.DTU_SERVICE_QUEUE_NAME, true, deliverCallback, consumerTag -> {   //Change Queue name
        });
    }
}
