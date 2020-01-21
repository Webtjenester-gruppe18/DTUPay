package dtu.ws18.models;

/**
 * @author Emil Glimø Vinkel - s175107
 */

import java.math.BigDecimal;

public class CustomerReportTransaction extends ReportTransaction {

    private String merchantAccountId;

    public CustomerReportTransaction(BigDecimal amount, String description, long time, Token token, String merchantAccountId) {
        super(amount, description, time, token);

        this.merchantAccountId = merchantAccountId;
    }

    public CustomerReportTransaction(DTUPayTransaction transaction, Customer customer) {
        super(transaction.getAmount(), transaction.getDescription(), transaction.getTime(), transaction.getToken());

        if (customer.getAccountId().equals(transaction.getCreditor())) {
            this.merchantAccountId = transaction.getDebtor();
        } else {
            this.merchantAccountId = transaction.getCreditor();
        }
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }
}
