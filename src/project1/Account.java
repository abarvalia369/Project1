package project1;

/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author ArpeetBarvalia, Jonathan John
 */

public class Account implements Comparable<Account> {
    private AccountNumber number;
    private Profile       holder;
    private double       balance;

    /**
     * Initializes an Account Object with the following 3 parameters.
     *
     * @param number the account number for the new account object.
     * @param holder the profile holder for the new account object.
     * @param balance the account balance for the new account object.
     */
    public Account(AccountNumber number, Profile holder, double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * A method to withdraw(subtract) money from the current account balance
     *
     * @param amount the amount to be withdrawn
     */
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    /**
     * A method to deposit(add) money to the current account balance
     *
     * @param amount the amount to be deposited
     */
    public void deposit(double amount) {
        this.balance += amount;
    }


    public AccountNumber getNumber() {
        return this.number;
    }
    public void setNumber(AccountNumber number){
        this.number = number;
    }
    public Profile getHolder() {
        return this.holder;
    }
    public void setHolder(Profile holder){
        this.holder = holder;
    }
    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * A method that returns if two accounts have the same account number
     *
     * @param obj
     * @return true if equal, false if not equal
     */
    @Override
    public boolean equals(Object obj) {
        if( obj == null || !( obj instanceof Account ) ){
            return false;
        }
        Account other = (Account) obj;
        return this.number.equals(other.number);
    }

    /**
     * A method returns a textual representation of an account in the following format.
     * Format: Account#[200017410] Holder[John Doe 2/19/2000] Balance[$600.00] Branch[BRIDGEWATER]
     *
     * @return returns all Account information in the above format
     */
    @Override
    public String toString() {
        java.text.DecimalFormat df = new java.text.DecimalFormat("$0.00");
        return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance["
        + df.format(balance) + "] Branch[" + number.getBranch().toString() + "]";
    }

    /**
     * A method that compares two account numbers to see which is greater or if they are equal.
     *
     * @param other the object to be compared.
     * @return 1 if parameter is greater than Account, -1 if parameter is less than, and 0 if parameter is equal.
     */
    @Override
    public int compareTo(Account other) {
        return this.number.toString().compareTo(other.number.toString());
    }


}
