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
            if(accounts[i] != null){
                if(accounts[i].equals(account)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.

    private int find(String accountNumber) {
        for(int i = 0; i < size; i++){
            if(accounts[i] != null){
                if(accounts[i].getNumber().equals(accountNumber)){
                return i; 
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.

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


    public void add(Account account) {
        if(this.contains(account)){
            return;
        }


    } //add to end of array

    public void remove(Account account) {

    }//replace it with the last item

    public boolean withdraw(AccountNumber number, double amount) {

    }

    public void deposit(AccountNumber number, double amount) {

    }

    public void printArchive() //print closed accounts

    public void printByBranch() {}

    public void printByHolder() {}

    public void printByType() {}

}