package project1;


public class AccountDatabase {
    public static final int NOT_FOUND = -1;
    public static final int GROW = 4;

    private Account [] accounts;
    private int size;
    private Archive archive; //a linked list of closed account

    public AccountDatabase() {
        this.accounts = new Account[0];
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
        for(int i = 0; i < size; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].equals(account)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.

    private int find(String accountNumber) {
        for(int i = 0; i < size; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].getNumber().isEqual(accountNumber)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.

    private int find(AccountNumber accountNumber){
        for(int i = 0; i < size; i++){
            if(this.accounts[i] != null){
                if(this.accounts[i].getNumber().isEqual(accountNumber)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    }

    private void grow() {
        int newSize = this.getSize() + GROW; 
        Account[] temp = new Account[newSize];

        for (int i = 0; i < this.size; i++) { 
            temp[i] = this.accounts[i];
        }

        this.accounts = temp; 
        this.setSize(newSize); 
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
    } 

    public boolean contains(String accountNumber){
        if (this.find(accountNumber) != NOT_FOUND){
            return true; 
        }
    } 


    public void add(Account account) {
        if(this.contains(account)){
            return;
        }
        for(int i = 0; i < this.size; i++){
            if(this.accounts[i] == null{
                this.accounts[i] = account; 
                return;
            }
        }
        this.grow();
        for(int j = 0; j < this.size; j++){
            if(this.accounts[j] == null{
                this.accounts[j] = account; 
                return;
            }
        }



    } //add to end of array

    public void remove(Account account) {
        if (this.contains(account)) {
            this.archive.add(this.accounts[this.find(account)]);
            this.accounts[this.find(account)] = this.accounts[this.size - SIZEARRAYOFFSET];
            this.accounts[this.size - SIZEARRAYOFFSET] = null;
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
            this.accounts[index].setAccountType(AccountType.RegularSavings)
            return true;
        }
        else{
            return false;
        }

    }

    public void deposit(AccountNumber number, double amount) {
        int index = find(number);
        if (index != NOT_FOUND) {
            accounts[index].setBalance()
        }
    }

    public void printArchive() //print closed accounts

    public void printByBranch() {}

    public void printByHolder() {}

    public void printByType() {}

}