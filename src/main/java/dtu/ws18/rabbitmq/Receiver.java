package dtu.ws18.rabbitmq;

import org.springframework.stereotype.Component;
/* <<< from https://github.com/spring-guides/gs-messaging-rabbitmq>>>*/

@Component
public class Receiver {

    public void receiveMessage(String message) {
        System.out.println(message);

    }


}
