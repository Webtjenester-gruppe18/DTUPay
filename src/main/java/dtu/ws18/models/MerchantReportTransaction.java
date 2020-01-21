package dtu.ws18.models;

/**
 * @author Emil Glimø Vinkel - s175107
 */

import java.math.BigDecimal;

public class MerchantReportTransaction extends ReportTransaction {

    public MerchantReportTransaction(BigDecimal amount, String description, long time, Token token) {
        super(amount, description, time, token);
    }
}
