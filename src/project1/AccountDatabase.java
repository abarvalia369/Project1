package project1;

import util.Date;
import util.List;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author arpeet barvalia, jonathan john
 */

public class AccountDatabase extends List<Account>{
     public static final int NOT_FOUND = -1;
     public static final int GROW = 4;
     /*
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

    /**
     * A method for subtracting an ammount ot the balance of an Account
     *
     * @param number AccountNumber of the account that is being withdrawn from
     * @param amount The Amount of money client wants to withdraw
     * @return returns a boolean on whether the amount could be with drawn
     */
    public boolean withdraw(AccountNumber number, double amount) {
        if(this.indexOf(number) == NOT_FOUND){
            return false; 
        }
        int index = this.indexOf(number);
        double newBalance = this.get(index).getBalance() - amount;
        this.get(index).setBalance(newBalance);
        if(newBalance >= 2000.00){
            return true; 
        }
        else if(newBalance > 0.00){
            this.get(index).getNumber().setAccountType(AccountType.RegularSavings);
            return true;
        }
        else{
            return false;
        }

    }


        
    /**
     * A method for adding an ammount to the balance of an Account
     *
     * @param number AccountNumber of the account that is being deposited to
     * @param amount The amount of money client wants to deposit
     */

    public void deposit(AccountNumber number, double amount) {
        int index = indexOf(number);
        if (index != NOT_FOUND) {
            double newBalance = this.get(index).getBalance() + amount;
            this.get(index).setBalance(newBalance);
        }
    }


    /**
     * A method for printing the current archived accounts
     */
    public void printArchive() {
        this.archive.print();
    }

    public void printStatements() {
        if (this.isEmpty()) {
            System.out.println("Account Database is empty.");
            return;
        }

        /*
        for (int i = 0; i < this.size(); i++) {
            Account account = this.get(i);
            System.out.println("=== Account Statement ===");
            System.out.println(account);
            System.out.println("Recent Transactions:");
            for (Activity activity : account.getActivities()) {
                System.out.println(activity);
            }
            System.out.printf("Monthly Interest: %.2f\n", account.interest());
            System.out.printf("Monthly Fee: %.2f\n", account.fee());
            System.out.printf("Updated Balance: %.2f\n\n",
                    account.getBalance() + account.interest() - account.fee());
        }

         */

    } //print account statements

    public void loadAccounts(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] tokens = line.split(",");
            if (tokens.length < 4) {
                System.out.println("Invalid account format: " + line);
                continue;
            }
            String name = tokens[0].trim();
            Date dob = new Date(tokens[1].trim());
            String type = tokens[2].trim();
            double balance = Double.parseDouble(tokens[3].trim());
            Account account = null;
            switch (type) {
                case "Checking":
                    account = new Checking(new Profile(name, dob), balance);
                    break;
                case "College Checking":
                    if (tokens.length < 5) {
                        System.out.println("Missing campus info: " + line);
                        continue;
                    }
                    int campusCode = Integer.parseInt(tokens[4].trim());
                    account = new CollegeChecking(new Profile(name, dob), balance, Campus.fromCode(campusCode));
                    break;
                case "Savings":
                    account = new Savings(new Profile(name, dob), balance);
                    break;
                case "Money Market":
                    account = new MoneyMarket(new Profile(name, dob), balance);
                    break;
                case "CD":
                    if (tokens.length < 6) {
                        System.out.println("Missing term or open date: " + line);
                        continue;
                    }
                    int term = Integer.parseInt(tokens[4].trim());
                    Date openDate = new Date(tokens[5].trim());
                    account = new CertificateDeposit(new Profile(name, dob), balance, term, openDate);
                    break;
                default:
                    System.out.println("Unknown account type: " + type);
                    continue;
            }
            if (!this.contains(account)) {
                this.add(account);
            } else {
                System.out.println("Account already exists: " + name);
            }
        }
        scanner.close();
    }

    public void processActivities(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] tokens = line.split(",");
            if (tokens.length < 3) {
                System.out.println("Invalid transaction format: " + line);
                continue;
            }

            char type = tokens[0].trim().charAt(0);
            int accountNumber = Integer.parseInt(tokens[1].trim());
            double amount = Double.parseDouble(tokens[2].trim());

            Account account = findAccount(accountNumber);
            if (account == null) {
                System.out.println("Account not found: " + accountNumber);
                continue;
            }

            Activity activity = new Activity(new Date(), null, type, amount, true);

            if (type == 'D') {
                account.deposit(amount);
            } else if (type == 'W') {
                if (!account.withdraw(amount)) {
                    System.out.println("Withdrawal failed for account: " + accountNumber);
                }
            } else {
                System.out.println("Invalid transaction type: " + type);
                continue;
            }
            account.addActivity(activity);
        }
        scanner.close();
    }


}
