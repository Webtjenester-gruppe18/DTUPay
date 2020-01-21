package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
import dtu.ws18.models.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author emil_s175107
 */
public class EndPointReceiver implements IEventReceiver {

    private ObjectMapper objectMapper;

    public EndPointReceiver()  {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        String response;
        switch (event.getType()) {
            case TOKEN_GENERATION_RESPONSE:
                TokenController.tokenFuture.complete(event.getType());
                break;
            case RETRIEVE_TOKENS_RESPONSE:
                ArrayList<Token> tokens = objectMapper.convertValue(event.getObject(), ArrayList.class);
                TokenController.tokenListFuture.complete(tokens);
                break;
            case CREATE_CUSTOMER_RESPONSE:
                response = objectMapper.convertValue(event.getObject(), String.class);
                CustomerController.customerPostFuture.complete(response);
                break;
            case DELETE_CUSTOMER_RESPONSE:
                response = objectMapper.convertValue(event.getObject(), String.class);
                CustomerController.customerDeleteFuture.complete(response);
                break;
            case RETRIEVE_CUSTOMER_RESPONSE:
                Customer customer = objectMapper.convertValue(event.getObject(), Customer.class);
                CustomerController.customerGetFuture.complete(customer);
                break;
            case CREATE_MERCHANT_RESPONSE:
                response = objectMapper.convertValue(event.getObject(), String.class);
                MerchantController.merchantPostFuture.complete(response);
                break;
            case DELETE_MERCHANT_RESPONSE:
                response = objectMapper.convertValue(event.getObject(), String.class);
                MerchantController.merchantDeleteFuture.complete(response);
                break;
            case RETRIEVE_MERCHANT_RESPONSE:
                Merchant merchant = objectMapper.convertValue(event.getObject(), Merchant.class);
                MerchantController.merchantGetFuture.complete(merchant);
                break;
            case REQUEST_TRANSACTIONS_SUCCEED:
                ArrayList<DTUPayTransaction> transactions = objectMapper.convertValue(event.getObject(), ArrayList.class);
                ReportingController.reportFuture.complete(transactions);
                break;
            case MONEY_TRANSFER_SUCCEED:
                PaymentController.paymentFuture.complete(event);
                break;
            case REFUND_FAILED:
                System.out.println("Jeg er inde...");
                PaymentController.refundFuture.complete(event);
                break;
            case REFUND_SUCCEED:
                System.out.println("Jeg er UDE...");
                PaymentController.refundFuture.complete(event);
                break;
        }

    }

}
