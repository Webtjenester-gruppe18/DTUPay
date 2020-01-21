package dtu.ws18;

import dtu.ws18.messagingutils.EventReceiverImpl;
import dtu.ws18.messagingutils.RabbitConfiguration;
import dtu.ws18.restcontrollers.EndPointReceiver;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@EnableRabbit
@SpringBootApplication
public class DtupayApplication {
    RabbitTemplate rabbitTemplate;
    @Autowired
    DtupayApplication(RabbitTemplate rabbitTemplate){
    this.rabbitTemplate = rabbitTemplate;
    RabbitAdmin admin = new RabbitAdmin(rabbitTemplate);
    admin.initialize();
    EndPointReceiver endPointReceiver = new EndPointReceiver();
        try {
            new EventReceiverImpl(endPointReceiver).listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("Hej");
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DtupayApplication.class, args);
    }

}
