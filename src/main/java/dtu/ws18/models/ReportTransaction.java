package dtu.ws18.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Emil Glimø Vinkel - s175107
 */

@Getter
@Setter
@NoArgsConstructor
public abstract class ReportTransaction implements Serializable {

    private BigDecimal amount;
    private String description;
    private long time;
    private Token token;

    public ReportTransaction(BigDecimal amount, String description, long time, Token token) {
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.token = token;
    }

    public BigDecimal getAmount() {
        return amount;
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
