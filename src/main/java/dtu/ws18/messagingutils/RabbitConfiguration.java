package dtu.ws18.messagingutils;

import javassist.expr.Instanceof;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class RabbitConfiguration {
//    @Bean
//    public Declarables topicBindings() {
//        Queue payment_service_queue = new Queue(RabbitMQValues.PAYMENT_SERVICE_QUEUE_NAME, true);
//        Queue token_service_queue = new Queue(RabbitMQValues.TOKEN_SERVICE_QUEUE_NAME, true);
//        Queue dtu_service_queue = new Queue(RabbitMQValues.DTU_SERVICE_QUEUE_NAME, true);
//        Queue user_service_queue = new Queue(RabbitMQValues.USER_SERVICE_QUEUE_NAME, true);
//        Queue reporting_service_queue = new Queue(RabbitMQValues.REPORTING_SERVICE_QUEUE_NAME, true);
//        TopicExchange topicExchange = new TopicExchange(RabbitMQValues.TOPIC_EXCHANGE_NAME,true,true);
//        return new Declarables(
//                payment_service_queue,
//                token_service_queue,
//                dtu_service_queue,
//                user_service_queue,
//                reporting_service_queue,
//                topicExchange,
//                BindingBuilder
//                        .bind(payment_service_queue)
//                        .to(topicExchange).with(RabbitMQValues.PAYMENT_SERVICE_ROUTING_KEY),
//                BindingBuilder
//                        .bind(token_service_queue)
//                        .to(topicExchange).with(RabbitMQValues.TOKEN_SERVICE_ROUTING_KEY),
//                BindingBuilder
//                        .bind(dtu_service_queue)
//                        .to(topicExchange).with(RabbitMQValues.DTU_SERVICE_ROUTING_KEY),
//                BindingBuilder
//                        .bind(user_service_queue)
//                        .to(topicExchange).with(RabbitMQValues.USER_SERVICE_ROUTING_KEY),
//                BindingBuilder
//                        .bind(reporting_service_queue)
//                        .to(topicExchange).with(RabbitMQValues.REPORTING_SERVICE_ROUTING_KEY));
//    }
    
}
