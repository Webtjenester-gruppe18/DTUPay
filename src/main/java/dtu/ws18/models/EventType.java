package dtu.ws18.models;

public enum EventType {
    PAYMENT_REQUEST,
    REFUND_REQUEST,
    REFUND_REQUEST_RESPONSE,


    //Retrieve customer profile
    RETRIEVE_CUSTOMER,
    CREATE_CUSTOMER,
    DELETE_CUSTOMER,
    RETRIEVE_CUSTOMER_RESPONSE,
    DELETE_CUSTOMER_RESPONSE,
    CREATE_CUSTOMER_RESPONSE,
    //Retrieve merchant profile
    RETRIEVE_MERCHANT,
    CREATE_MERCHANT,
    DELETE_MERCHANT,
    RETRIEVE_MERCHANT_RESPONSE,
    DELETE_MERCHANT_RESPONSE,
    CREATE_MERCHANT_RESPONSE,

    //Reporting
    RETRIEVE_CUSTOMER_REPORTS,
    RETRIEVE_MERCHANT_REPORTS,

    TOKEN_VALIDATION_REQUEST,
    TOKEN_VALIDATION_FAILED,
    MONEY_TRANSFER_FAILED,
    MONEY_TRANSFER_SUCCEED,
    //Token Retrieval
    RETRIEVE_TOKENS,
    RETRIEVE_TOKENS_FAILED,
    RETRIEVE_TOKENS_SUCCEED,
    //Token generation related
    REQUEST_FOR_NEW_TOKENS,
    TOKEN_GENERATION_FAILED,
    TOKEN_GENERATION_SUCCEED,

    REQUEST_TRANSACTIONS,
    REQUEST_TRANSACTIONS_SUCCEED
}
