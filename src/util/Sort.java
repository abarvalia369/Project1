package util;
import project1.*;

import project1.AccountDatabase;

public class Sort {

    //selection sort the list of accounts with different keys ("B" "H" "T" for: PB PH PT)
    //sorting a list of Account objects
    public static void ListSort(List<Account> accountList, char key) {
        int n = accountList.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (compare(accountList.get(j), accountList.get(min), key) < 0) {
                    min = j;
                }
            }
                //swapping
            if (min != i) {Account temp = accountList.get(i);
                    accountList.set(i, accountList.get(min));
                    accountList.set(min, temp);
                }
            }
        }

        //AccountDatabase into List<Account>
        public static void AccountSort(AccountDatabase Database, char key) {
            List<Account> accountList = new List<>();
            for (int i = 0; i < Database.size(); i++) {
                accountList.add(Database.get(i)); //move AccountDatabase to List<Account>
            }
            ListSort(accountList, key); //sorting
            for (int i = 0; i < Database.size(); i++) {
                Database.set(i, accountList.get(i)); // Update with sorted results BACK to AccountDatabase
            }
        }

    /**
     * A method for printing the current accounts in order of city and county
     */
    /*
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

     */

    /**
     * A method for printing the current accounts in order of holder and number
     */
    /*
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

     */
    /**
     * A method for printing the current accounts in order of type and number
     */
    /*
    public void printByType() {
        if(this.size == 0) {
            System.out.println("Account database is empty!");
            return;
        }
        for (int i = 1; i < size; ++i) {.//;;;;;;;.
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

     */
    /**
     * A method for printing the current accounts in order of Database
     */
    /*
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

     */
    /*
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

     */

    // Compares accounts based on the sorting key
    private static int compare(Account firstAcc, Account secondAcc, char key) {
        switch (key) {
            case 'B': // Sort by Branch (County → City)
                String branchA = firstAcc.getNumber().getBranch().getCounty() + firstAcc.getNumber().getBranch().getBranchName();
                String branchB = secondAcc.getNumber().getBranch().getCounty() + secondAcc.getNumber().getBranch().getBranchName();
                return branchA.compareTo(branchB);

            case 'H': // Sort by Holder (Last Name → First Name → DOB → Account Number)
                int holderCompare = firstAcc.getHolder().compareTo(secondAcc.getHolder());
                if (holderCompare != 0) return holderCompare;
                return firstAcc.getNumber().compareTo(secondAcc.getNumber()); // If same name, sort by Account Number

            case 'T': // Sort by Account Type (Then Account Number)
                int typeCompare = firstAcc.getNumber().getAccountType().getCode().compareTo(secondAcc.getNumber().getAccountType().getCode());
                if (typeCompare != 0) return typeCompare;
                return firstAcc.getNumber().compareTo(secondAcc.getNumber()); // If same type, sort by Account Number

            default:
                throw new IllegalArgumentException("Invalid sorting key: " + key);
        }
    }
}
