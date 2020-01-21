package dtu.ws18.messagingutils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dtu.ws18.models.Event;
import gherkin.deps.com.google.gson.Gson;
import lombok.SneakyThrows;
import java.io.IOException;

/*
Boilerplate from @Author Hubert Baumeister demoproject with a few modifications
Replaced old code, to allow cucumber tests to work properly
@Service
public class Listener {
    private ObjectMapper objectMapper;
    public Listener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void receiveEvent(Event event) { }
*/

public class EventReceiverImpl {
    private IEventReceiver eventReceiver;

    public EventReceiverImpl(IEventReceiver eventReceiver) {
        this.eventReceiver = eventReceiver;

    }
    @SneakyThrows
    public void listen() {
        ConnectionFactory factory = new ConnectionFactory();
        /*factory.setHost("localhost");*/ factory.setHost("rabbitmq");
        Connection connection = factory.newConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
