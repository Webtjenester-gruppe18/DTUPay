package dtu.ws18;

import dtu.ws18.messagingutils.EventReceiverImpl;
import dtu.ws18.restcontrollers.EndPointReceiver;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableRabbit
@SpringBootApplication
public class DtupayApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DtupayApplication.class, args);
        new DtupayApplication().startUp();
    }

    private void startUp()  {
        try {
            EndPointReceiver endPointReceiver = new EndPointReceiver();
            new EventReceiverImpl(endPointReceiver).listen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
