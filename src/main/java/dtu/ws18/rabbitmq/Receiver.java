package dtu.ws18.rabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;
/* <<< from https://github.com/spring-guides/gs-messaging-rabbitmq>>>*/

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
