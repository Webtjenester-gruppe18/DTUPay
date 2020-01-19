package dtu.ws18.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.models.*;
import dtu.ws18.restcontrollers.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class Listener {
    private ObjectMapper objectMapper;

    public Listener(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {RabbitMQValues.DTU_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {


        if (event.getType().equals(EventType.MONEY_TRANSFER_SUCCEED)||event.getType().equals(EventType.MONEY_TRANSFER_FAILED)) {
            PaymentController.paymentFuture.complete(event);
        } else if (event.getType().equals(EventType.TOKEN_GENERATION_SUCCEED) || event.getType().equals(EventType.TOKEN_GENERATION_FAILED)) {
            TokenController.tokenFuture.complete(event.getType());
        } else if (event.getType().equals(EventType.RETRIEVE_TOKENS_SUCCEED) || event.getType().equals(EventType.RETRIEVE_TOKENS_FAILED)) {
            ArrayList<Token> tokens = objectMapper.convertValue(event.getObject(), ArrayList.class);
            TokenController.tokenListFuture.complete(tokens);
        } else if (event.getType().equals(EventType.REQUEST_TRANSACTIONS_SUCCEED)) {
            ArrayList<DTUPayTransaction> transactions = objectMapper.convertValue(event.getObject(), ArrayList.class);
            ReportingController.reportFuture.complete(transactions);
        } else if (event.getType().equals(EventType.CREATE_CUSTOMER_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            CustomerController.customerPostFuture.complete(response);
        } else if (event.getType().equals(EventType.DELETE_CUSTOMER_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            CustomerController.customerDeleteFuture.complete(response);
        } else if (event.getType().equals(EventType.RETRIEVE_CUSTOMER_RESPONSE)) {
            Customer response = objectMapper.convertValue(event.getObject(), Customer.class);
            CustomerController.customerGetFuture.complete(response);
        } else if (event.getType().equals(EventType.CREATE_MERCHANT_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            MerchantController.merchantPostFuture.complete(response);
        } else if (event.getType().equals(EventType.DELETE_MERCHANT_RESPONSE)) {
            String response = objectMapper.convertValue(event.getObject(), String.class);
            MerchantController.merchantDeleteFuture.complete(response);
        } else if (event.getType().equals(EventType.RETRIEVE_MERCHANT_RESPONSE)) {
            Merchant response = objectMapper.convertValue(event.getObject(), Merchant.class);
            MerchantController.merchantGetFuture.complete(response);


        }

    }

}
