package dtu.ws18.models;

public enum EventType {
    PAYMENT_REQUEST,
    REFUND_REQUEST,
    RETRIEVE_CUSTOMER,
    RETRIEVE_CUSTOMER_REPORTS,
    RETRIEVE_MERCHANT,
    RETRIEVE_MERCHANT_REPORTS,
    TOKEN_VALIDATION_REQUEST,
    TOKEN_VALIDATION_FAILED,
    MONEY_TRANSFER_FAILED,
    MONEY_TRANSFER_SUCCEED,
    REQUEST_FOR_NEW_TOKENS
}
