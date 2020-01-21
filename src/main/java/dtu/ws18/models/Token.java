package dtu.ws18.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Emil Glimø Vinkel - s175107
 */

@Getter
@Setter
public class Token implements Serializable {

    private String value;
    private String customerCpr;
    private boolean hasBeenUsed;

    public Token() {
        this.value = UUID.randomUUID().toString();
        this.hasBeenUsed = false;
    }

    public Token(String customerCpr) {
        this.customerCpr = customerCpr;
        this.value = UUID.randomUUID().toString();
        this.hasBeenUsed = false;
    }

}
