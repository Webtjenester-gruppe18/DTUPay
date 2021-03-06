package dtu.ws18.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws18.messagingutils.IEventReceiver;
import dtu.ws18.models.*;

import java.util.ArrayList;

/**
 * @author emil_s175107
 */
public class EndPointReceiver implements IEventReceiver {

    private ObjectMapper objectMapper;

    public EndPointReceiver() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        String response;

        switch (event.getType()) {
            case TOKEN_GENERATION_RESPONSE_SUCCESS:
            case TOKEN_GENERATION_RESPONSE_FAILED:
                TokenController.tokenFuture.complete(event);
                break;
            case TOKEN_VALIDATION_FAILED:
                PaymentController.paymentFuture.complete(event);
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
            case RETRIEVE_CUSTOMER_RESPONSE_SUCCESS:
            case RETRIEVE_CUSTOMER_RESPONSE_FAILED:
                CustomerController.customerGetFuture.complete(event);
                break;
            case CREATE_MERCHANT_RESPONSE:
                response = objectMapper.convertValue(event.getObject(), String.class);
                MerchantController.merchantPostFuture.complete(response);
                break;
            case DELETE_MERCHANT_RESPONSE:
                response = objectMapper.convertValue(event.getObject(), String.class);
                MerchantController.merchantDeleteFuture.complete(response);
                break;
            case RETRIEVE_MERCHANT_RESPONSE_SUCCESS:
            case RETRIEVE_MERCHANT_RESPONSE_FAILED:
                MerchantController.merchantGetFuture.complete(event);
                break;
            case REQUEST_TRANSACTIONS_SUCCEED:
                ArrayList<DTUPayTransaction> transactions = objectMapper.convertValue(event.getObject(), ArrayList.class);
                ReportingController.reportFuture.complete(transactions);
                break;
            case MONEY_TRANSFER_SUCCEED:
            case MONEY_TRANSFER_FAILED:
                PaymentController.paymentFuture.complete(event);
                break;
            case REFUND_FAILED:
            case REFUND_SUCCEED:
                PaymentController.refundFuture.complete(event);
                break;

        }

    }

}
