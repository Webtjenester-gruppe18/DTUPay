package dtu.ws18;

import dtu.ws18.controllers.PaymentController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DtupayApplication {
    private static final String queueName = "response-queue";
    Thread thread = new Thread();

    @RabbitListener(queues = {queueName})
    public void receiveMessageFromPaymentQueue(String message) {
        if(message.equals("Completed")){
            System.out.println(message);
            PaymentController.completed = message;
          //  PaymentController.future.complete(message);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(DtupayApplication.class, args);
    }
}
