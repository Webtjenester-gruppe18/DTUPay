package dtu.ws18.configuration;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    private static final String topicExchangeName = "dtupay-eventsExchange";

    @Bean
    public Declarables topicBindings() {
        Queue pay = new Queue("pay-queue", false);
        Queue token = new Queue("token-queue", false);
        Queue response = new Queue("response-queue", false);

        TopicExchange topicExchange = new TopicExchange(topicExchangeName);

        return new Declarables(
                pay,
                token,
                response,
                topicExchange,
                BindingBuilder
                        .bind(pay)
                        .to(topicExchange).with("#.pay"),
                BindingBuilder
                        .bind(token)
                        .to(topicExchange).with("#.token"),
                BindingBuilder
                        .bind(response)
                        .to(topicExchange).with("#.response"));
    }


}
