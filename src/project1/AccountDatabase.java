package project1;

import util.Date;
import util.List;
import java.io.File;
import java.io.IOException;


/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author arpeet barvalia, jonathan john
 */


public class AccountDatabase extends List<Account>{
    /* public static final int NOT_FOUND = -1;
     public static final int GROW = 4;

     private Account [] accounts;
     private int size; */
    private Archive archive; //a linked list of closed account

    /**
     * Initializes an AccountDataBase object with an array of Accounts, size(number of accounts) = 0, and an Archive
     *
     */
    public AccountDatabase() {
        /*this.accounts = new Account[4];
        this.size = 0;
         */
        this.archive = new Archive();
    }
/*
    public int getSize(){
        return size;
    }

    public void setSize(int newSize){
        this.size = newSize;
    }

    public Account[] getAccounts(){
        return accounts;
    }

 */
    /**
     * A search method that traverses accounts in search for specfic account. 
     *
     * @param account Account that is being searched for in array accounts
     * @return returns an index int value of where in the array the parameter account is, return the index or -1 not found.
     */

    /*
    private int find(Account account) {
        for(int i = 0; i < accounts.length; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].equals(account)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    }

     */
    /**
     * A search method that traverses accounts in search for specfic account by reference of accountNumber String
     *
     * @param accountNumber String that is being searched for in array accounts
     * @return returns an index int value of where in the array the parameter account is, return the index or -1 not found.
     */
    /*
    private int find(String accountNumber) {
        for(int i = 0; i < accounts.length; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].getNumber().equals(accountNumber)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.

     */

    /**
     * A search method that traverses accounts in search for specfic account by reference of AccountNumber accountNumber 
     *
     * @param accountNumber AccountNumber that is being searched for in array accounts
     * @return returns an index int value of where in the array the parameter account is, return the index or -1 not found.
     */
    /*
    private int find(AccountNumber accountNumber){
        for(int i = 0; i < accounts.length; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].getNumber().equals(accountNumber)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    }
     */
    /**
     * A method for increasing the capacity of the array accounts by 4 or GROW
     */
    /*
    private void grow() {
        int newCap = this.accounts.length + GROW; 
        Account[] temp = new Account[newCap];

        for (int i = 0; i < accounts.length; i++) { 
            temp[i] = this.accounts[i];
        }

        this.accounts = temp; 
    } //increase the array capacity by 4

     */

    /**
     * A search method that traverses accounts in search for specfic account, returning it's status
     *
     * @param account Account that is being searched for in array accounts
     * @return returns a boolean on whether or not the account can be found within the array accounts. 
     */
    /*
    public boolean contains(Account account) {
        if (this.find(account) != NOT_FOUND){
            return true;
        } 
        return false; 
    } //check before add/remove

     */

    /**
     * A search method that traverses accounts in search for specfic account by reference of AccountNumber accountNumber, returning it's status
     *
     * @param accountNumber AccountNumber that is being searched for in array accounts
     * @return returns a boolean on whether or not the account can be found within the array accounts. 
     */
    /*
    public boolean contains(AccountNumber accountNumber){
        if (this.find(accountNumber) != NOT_FOUND){
            return true; 
        }
        return false;
    }

     */

    /**
     * A search method that traverses accounts in search for specfic account by reference of String ccountNumber, returning it's status
     *
     * @param accountNumber String that is being searched for in array accounts
     * @return returns a boolean on whether or not the account can be found within the array accounts. 
     */
    /*
    public boolean contains(String accountNumber){
        if (this.find(accountNumber) != NOT_FOUND){
            return true; 
        }
        return false;
    }

     */

    /**
     * A method for adding an Account to the Database
     *
     * @param account Account that is being added to database
     */
    /*
    public void add(Account account) {
        if(this.contains(account)){
            return;
        }
        for(int i = 0; i < this.accounts.length; i++){
            if(this.accounts[i] == null){
                this.accounts[i] = account; 
                this.setSize(this.size + 1);
                return;
            }
        }
        this.grow();
        for(int j = 0; j < this.accounts.length; j++){
            if(this.accounts[j] == null){
                this.setSize(this.size + 1);
                this.accounts[j] = account; 
                return;
            }
        }

    }

     */

    /**
     * A method for removing the Account and replacing it with the last Account in the dataBase
     *
     * @param account Account that is being removed
     */
    /*
    public void remove(Account account) {
        if (this.contains(account)) {
            this.archive.add(this.accounts[this.find(account)], new Date());
            this.accounts[this.find(account)] = this.accounts[this.size - 1];
            this.accounts[this.size - 1] = null;
            this.setSize(this.size -1);
        }
        else{
            return;
        }
    }

     */

    /**
     * A method for subtracting an ammount ot the balance of an Account
     *
     * @param number AccountNumber of the account that is being withdrawn from
     * @param amount The Ammount of money client wants to withdraw
     * @return returns a boolean on whether or not the amount could be with drawn
     */
    /*
    public boolean withdraw(AccountNumber number, double amount) {
        if(this.find(number) == NOT_FOUND){
            return false; 
        }
        int index = this.find(number);
        double newBalance = this.accounts[index].getBalance() - amount; 
        this.accounts[index].setBalance(newBalance);
        if(newBalance >= 2000.00){
            return true; 
        }
        else if(newBalance > 0.00){
            this.accounts[index].getNumber().setAccountType(AccountType.RegularSavings);
            return true;
        }
        else{
            return false;
        }

    }

     */
        
    /**
     * A method for adding an ammount to the balance of an Account
     *
     * @param number AccountNumber of the account that is being deposited to
     * @param amount The amount of money client wants to deposit
     */
    /*
    public void deposit(AccountNumber number, double amount) {
        int index = find(number);
        if (index != NOT_FOUND) {
            double newBalance = this.accounts[index].getBalance() + amount; 
            this.accounts[index].setBalance(newBalance);
        }
    }
     */

    /**
     * A method for printing the current archived accounts
     */
    public void printArchive() {
        this.archive.print();
    }

    public void printStatements() {

    } //print account statements

    public void loadAccounts(File file) throws IOException {

    }

    public void processActivities(File file) throws IOException {

    }


}
