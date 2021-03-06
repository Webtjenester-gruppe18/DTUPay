package dtu.ws18.models;

/**
 * @author Emil Glimø Vinkel - s175107
 */

public enum EventType {
    //Retrieve customer profile
    RETRIEVE_CUSTOMER,
    CREATE_CUSTOMER,
    DELETE_CUSTOMER,
    RETRIEVE_CUSTOMER_RESPONSE_SUCCESS,
    RETRIEVE_CUSTOMER_RESPONSE_FAILED,
    DELETE_CUSTOMER_RESPONSE,
    CREATE_CUSTOMER_RESPONSE,
    //Retrieve merchant profile
    RETRIEVE_MERCHANT,
    CREATE_MERCHANT,
    DELETE_MERCHANT,
    RETRIEVE_MERCHANT_RESPONSE_SUCCESS,
    RETRIEVE_MERCHANT_RESPONSE_FAILED,
    DELETE_MERCHANT_RESPONSE,
    CREATE_MERCHANT_RESPONSE,
    //Reporting
    REQUEST_TRANSACTIONS,
    REQUEST_TRANSACTIONS_SUCCEED,
    TOKEN_VALIDATION_REQUEST,
    //Payment
    REFUND_REQUEST,
    REFUND_FAILED,
    REFUND_SUCCEED,
    MONEY_TRANSFER_FAILED,
    MONEY_TRANSFER_SUCCEED,
    //Token Retrieval
    RETRIEVE_TOKENS,
    RETRIEVE_TOKENS_RESPONSE,
    //Token generation related
    REQUEST_FOR_NEW_TOKENS,
    TOKEN_GENERATION_RESPONSE_SUCCESS,
    TOKEN_GENERATION_RESPONSE_FAILED,
    TOKEN_VALIDATION_FAILED


}
