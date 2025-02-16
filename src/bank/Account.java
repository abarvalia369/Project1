package project1;

/**
 •	Account class for RU Bank project.
 •	Represents a bank account with an account number, holder profile, and balance.
 •	Implements deposit and withdrawal operations and overrides equals, toString, and compareTo.
 •
 •	@author YourName
 */
public class Account implements Comparable<Account> {
    private AccountNumber number;
    private Profile       holder;
    private double       balance;

    public Account(AccountNumber number, Profile holder, double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }
    public AccountNumber getNumber() {
        return number;
    }
    public Profile getHolder() {
        return holder;
    }
    public double getBalance() {
        return balance;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        return this.number.equals(other.number);
    }
    @Override
    public String toString() {
// Format: Account#[200017410] Holder[John Doe 2/19/2000] Balance[$600.00] Branch[BRIDGEWATER]
        java.text.DecimalFormat df = new java.text.DecimalFormat(”$0.00”);
        return “Account#[” + number.toString() + “] Holder[” + holder.toString() + “] Balance[”
        + df.format(balance) + “] Branch[” + number.getBranch().toString() + “]”;
    }
    @Override
    public int compareTo(Account other) {
        return this.number.toString().compareTo(other.number.toString());
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }
    public void deposit(double amount) {
        this.balance += amount;
    }
}
