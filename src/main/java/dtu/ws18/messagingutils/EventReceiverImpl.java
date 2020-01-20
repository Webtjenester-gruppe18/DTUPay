package dtu.ws18.messagingutils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dtu.ws18.models.Event;
import gherkin.deps.com.google.gson.Gson;


public class EventReceiverImpl {
    private IEventReceiver eventReceiver;

    public EventReceiverImpl(IEventReceiver eventReceiver) {
        this.eventReceiver = eventReceiver;
    }

    public void listen() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueBind(RabbitMQValues.TOKEN_SERVICE_QUEUE_NAME, RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY); //Change Queuename and routing key

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Event event = new Gson().fromJson(message, Event.class);
            try {
                eventReceiver.receiveEvent(event);
            } catch (Exception e) {
                throw new Error(e);
            }
        };
        channel.basicConsume(RabbitMQValues.TOKEN_SERVICE_QUEUE_NAME, true, deliverCallback, consumerTag -> {   //Change Queue name
        });
    }
}
