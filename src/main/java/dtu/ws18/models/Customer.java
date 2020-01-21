package dtu.ws18.models;

/**
 * @author Emil Glimø Vinkel - s175107
 */

public class Customer extends DTUPayUser {

    public Customer() {
    }

    public Customer(String accountId, String firstName, String lastName, String cprNumber) {
        super(accountId, firstName, lastName, cprNumber);
    }
}
