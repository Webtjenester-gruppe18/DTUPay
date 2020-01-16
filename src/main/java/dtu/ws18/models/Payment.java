package dtu.ws18.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class Payment implements Serializable {
    String fromAccountNumber;
    String toAccountNumber;
    BigDecimal amount;
    String description;
}
