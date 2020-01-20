package dtu.ws18.messagingutils;

public class RabbitMQValues {
    static final String TOPIC_EXCHANGE_NAME = "dtupay-eventsExchange";
    static final String DTU_SERVICE_QUEUE_NAME = "dtupayservice-queue";
    static final String DTU_SERVICE_ROUTING_KEY = "dtupay";
    static final String PAYMENT_SERVICE_QUEUE_NAME = "paymentservice-queue";
    public static final String PAYMENT_SERVICE_ROUTING_KEY = "payment";
    static final String TOKEN_SERVICE_QUEUE_NAME = "tokenservice-queue";
    public static final String TOKEN_SERVICE_ROUTING_KEY = "token";
    static final String USER_SERVICE_QUEUE_NAME = "userservice-queue";
    public static final String USER_SERVICE_ROUTING_KEY = "user";
    static final String REPORTING_SERVICE_QUEUE_NAME = "reportingservice-queue";
    static final String REPORTING_SERVICE_ROUTING_KEY = "reporting";

}
