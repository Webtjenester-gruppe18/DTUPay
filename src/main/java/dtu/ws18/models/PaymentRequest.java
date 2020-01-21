package dtu.ws18.models;

/**
 * @author Emil Glimø Vinkel - s175107
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest implements Serializable {
    String fromAccountNumber;
    String cpr;
    String toAccountNumber;
    BigDecimal amount;
    String description;
    Token token;
}
