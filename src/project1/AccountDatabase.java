package project1;

public class AccountDatabase {
    public static final int NOT_FOUND = -1;
    public static final int GROW = 4;

    private Account [] accounts;
    private int size;
    private Archive archive; //a linked list of closed account

    public AccountDatabase() {
        this.accounts = new Account[4];
        this.size = 0;
        this.archive = new Archive();
    }

    public int getSize(){
        return size;
    }

    public void setSize(int newSize){
        this.size = newSize;
    }
    
    private int find(Account account) {
        for(int i = 0; i < accounts.length; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].equals(account)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.

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

    private void grow() {
        int newCap = this.accounts.length + GROW; 
        Account[] temp = new Account[newCap];

        for (int i = 0; i < accounts.length; i++) { 
            temp[i] = this.accounts[i];
        }

        this.accounts = temp; 
    } //increase the array capacity by 4


    public boolean contains(Account account) {
        if (this.find(account) != NOT_FOUND){
            return true;
        } 
        return false; 
    } //check before add/remove

    public boolean contains(AccountNumber accountNumber){
        if (this.find(accountNumber) != NOT_FOUND){
            return true; 
        }
        return false;
    } 

    public boolean contains(String accountNumber){
        if (this.find(accountNumber) != NOT_FOUND){
            return true; 
        }
        return false;
    } 


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



    } //add to end of array

    public void remove(Account account) {
        if (this.contains(account)) {
            this.archive.add(this.accounts[this.find(account)]);
            this.accounts[this.find(account)] = this.accounts[this.size - 1];
            this.accounts[this.size - 1] = null;
            this.setSize(this.size -1);
        }
        else{
            return;
        }
    }//replace it with the last item

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

    public void deposit(AccountNumber number, double amount) {
        int index = find(number);
        if (index != NOT_FOUND) {
            double newBalance = this.accounts[index].getBalance() + amount; 
            this.accounts[index].setBalance(newBalance);
        }
    }

    public void printArchive() {
        this.archive.print();
    }//print closed accounts



    public void printByBranch() {
        if(this.size == 0) {
            System.out.println("Account database is empty!");
            return;
        }
        for (int i = 1; i < size; ++i) {
            Account insert = accounts[i];
            int pointer = i - 1;
            while (pointer >= 0 && (accounts[pointer].getNumber().getBranch().getCounty() + accounts[pointer].getNumber().getBranch().getBranchName()).compareTo(insert.getNumber().getBranch().getCounty() + insert.getNumber().getBranch().getBranchName()) > 0) {
                accounts[pointer + 1] = accounts[pointer];
                pointer--;
            }
            accounts[pointer + 1] = insert;
        }
        System.out.println("*List of accounts in the account database by county location [county,city].");
        for(int k = 0; k < size; k++) {
                if (accounts[k] != null) System.out.println(this.accounts[k].toString());
        }
        System.out.println("*end of list.");
    }
    
    public void printByHolder() {
        if(this.size == 0) {
            System.out.println("Account database is empty!");
            return;
        }
        for (int i = 1; i < size; ++i) {
            Account insert = accounts[i];
            int pointer = i - 1;
            while (pointer >= 0 && (accounts[pointer].getHolder().toString() + accounts[pointer].getNumber()).compareTo(insert.getHolder().toString() + insert.getNumber()) > 0) {
                accounts[pointer + 1] = accounts[pointer];
                pointer--;
            }
            accounts[pointer + 1] = insert;
        }
        System.out.println("*List of accounts in the account database by holder and number");
        for(int k = 0; k < size; k++) {
                if (accounts[k] != null) System.out.println(this.accounts[k].toString());
        }
        System.out.println("*end of list.");
    }

    public void printByType() {
        if(this.size == 0) {
            System.out.println("Account database is empty!");
            return;
        }
        for (int i = 1; i < size; ++i) {
            Account insert = accounts[i];
            int pointer = i - 1;
            while (pointer >= 0 && (accounts[pointer].getNumber().getAccountType().getCode() + accounts[pointer].getNumber()).compareTo(insert.getNumber().getAccountType().getCode() + insert.getNumber()) > 0) {
                accounts[pointer + 1] = accounts[pointer];
                pointer--;
            }
            accounts[pointer + 1] = insert;
        }
        System.out.println("*List of accounts in the account database by account type and number.");
        for(int k = 0; k < size; k++) {
                if (this.accounts[k] != null) System.out.println(this.accounts[k].toString());
        }
        System.out.println("*end of list.");
    }

    public void printOrder() {
        if(this.size == 0) {
            System.out.println("Account database is empty!");
            return;
        }
        System.out.println("*List of accounts in the account database.");
        for(int k = 0; k < size; k++) {
                if (this.accounts[k] != null) System.out.println(this.accounts[k].toString());
        }
        System.out.println("*end of list.");
    }

    public static void main(String[] args) {
        AccountNumber acctnum1 = new AccountNumber(Branch.Bridgewater, AccountType.RegularSavings);
        AccountNumber acctnum2 = new AccountNumber(Branch.Bridgewater, AccountType.Checking);
        AccountNumber acctnum3 = new AccountNumber(Branch.Bridgewater, AccountType.MoneyMarketSavings);
        AccountNumber acctnum4 = new AccountNumber(Branch.Princeton, AccountType.RegularSavings);
        AccountNumber acctnum5 = new AccountNumber(Branch.Princeton, AccountType.Checking);
        AccountNumber acctnum6 = new AccountNumber(Branch.Princeton, AccountType.MoneyMarketSavings);
        AccountNumber acctnum7 = new AccountNumber(Branch.Warren, AccountType.Checking);


        Profile Jonathan =new Profile("Jonathan", "John", 2004, 9, 16);
        Profile Arpeet =new Profile("Arpeet", "Barvalia", 1987, 1, 15);
        Profile Nikhil =new Profile("Nikhil", "Hirpara", 2000, 2, 19);

        Account acct1 = new Account(acctnum1, Nikhil, 200);
        Account acct2 = new Account(acctnum2, Nikhil, 1000);
        Account acct3 = new Account(acctnum3, Nikhil, 2500);
        Account acct4 = new Account(acctnum4, Arpeet, 1000);
        Account acct5 = new Account(acctnum5, Arpeet, 2000);
        Account acct6 = new Account(acctnum6, Arpeet, 3000);
        Account acct7 = new Account(acctnum7, Jonathan, 1400);

        AccountDatabase acctDatabase = new AccountDatabase();

        acctDatabase.add(acct1);
        acctDatabase.add(acct2);
        acctDatabase.add(acct3);
        acctDatabase.add(acct4);
        acctDatabase.add(acct5);
        acctDatabase.add(acct6);
        acctDatabase.add(acct7);

        System.out.println(acct3.toString());
        acctDatabase.printOrder();
        acctDatabase.printByHolder();
        acctDatabase.printByBranch();
        acctDatabase.printByType();

    }

}
