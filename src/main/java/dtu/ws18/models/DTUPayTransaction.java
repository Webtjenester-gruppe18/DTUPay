package dtu.ws18.models;

import java.math.BigDecimal;
import java.util.UUID;

public class DTUPayTransaction {

    private String transactionId;
    private BigDecimal amount;
    private String creditor;
    private String debtor;
    private String description;
    private long time;
    private Token token;

    public DTUPayTransaction(BigDecimal amount, String creditor, String debtor, String description, long time, Token token) {
        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
        this.token = token;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCreditor() {
        return creditor;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getDescription() {
        return description;
    }

    public long getTime() {
        return time;
    }

    public Token getToken() {
        return token;
    }
}
