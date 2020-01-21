package dtu.ws18.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Emil Glimø Vinkel - s175107
 */

@Getter
@Setter
@NoArgsConstructor
public abstract class DTUPayUser implements Serializable {

    private String accountId;
    private String firstName;
    private String lastName;
    private String cprNumber;

    public DTUPayUser(String accountId, String firstName, String lastName, String cprNumber) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cprNumber = cprNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }
}
