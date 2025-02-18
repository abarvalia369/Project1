package project1;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A user interface class to process the transactions entered on the terminal. An instance of this class
 * can process a single command line or multiple command lines at a time, including the empty lines.
 *
 * @author arpeet barvalia, jonathan john
 */
public class TransactionManager {
    private AccountDatabase database;

    /**
     * Initalizes transaction manager
     */
    public TransactionManager(){
        this.database = new AccountDatabase();
    }

    /**
     * Runs transaction manager
     */
    public void run(){
        System.out.println( "Transaction Manager is running." );
        Scanner scanner = new Scanner(System.in);

        while(true){
            if (!scanner.hasNextLine()) break;
            String commandLine = scanner.nextLine();
            if(commandLine.isEmpty()){
                continue;
            }

            StringTokenizer tokens = new StringTokenizer( commandLine );
            String command = tokens.nextToken();

            switch (command){
                case "O":
                    processOpen(tokens);
                    break;
                case "C":
                    processClose(tokens);
                    break;
                case "D":
                    deposit(tokens,tokens);
                    break;
                case "W":
                    withdraw(tokens, tokens);
                    break;
                case "P":
                    database.printOrder();
                    break;
                case "PA":
                    database.printArchive();
                    break;
                case "PB":
                    database.printByBranch();
                    break;
                case "PH":
                    database.printByHolder();
                    break;
                case "PT":
                    database.printByType();
                    break;
                case "Q":
                    System.out.println("Transaction Manager is terminated.");
                    scanner.close();
                    return;
                default:
                    System.out.println( "Invalid command." );
            }
        }
    }

    /**
     * Processes "O" command
     *
     */
    private void processOpen(StringTokenizer token){
        if(token.countTokens() <5){
            System.out.println("Missing data needed to open an account.");
            return;
        }
        String accountType = token.nextToken();
        String branchinput = token.nextToken();
        String fname = token.nextToken();
        String lname = token.nextToken();
        String dobinput = token.nextToken();
        String depositinput = token.nextToken();

        AccountType type = null;
        Branch branch = null;

        String[] dateParts = dobinput.split("/");
        int year;
        int month;
        int day;
        try {
            month = Integer.parseInt(dateParts[0]); // MM
            day = Integer.parseInt(dateParts[1]);   // DD
            year = Integer.parseInt(dateParts[2]);  // YYYY
        } catch (NumberFormatException e) {
            System.out.println("DOB invalid: " + dobinput);
            return;
        }
        Date dob = new Date(year, month, day);
        if (!dob.isValid()) {
            System.out.println("DOB invalid: " + dobinput);
            return;
        }

        double deposit;

        //account type---------------------------------------------------------------------------------
        switch(accountType.toLowerCase()){
            case "checking":
                type = AccountType.Checking;
                break;
            case "regularsavings":
                type = AccountType.RegularSavings;
                break;
            case "moneymarketsavings":
                type = AccountType.MoneyMarketSavings;
                break;
        }
        if(type == null){
            System.out.println(accountType = " is a invalid account type.");
            return;
        }
        //branch---------------------------------------------------------------------------------
        switch (branchinput.toLowerCase()){
            case "edison":
                branch = Branch.Edison;
                break;
            case "bridgewater":
                branch = Branch.Bridgewater;
                break;
            case "princeton":
                branch = Branch.Princeton;
                break;
            case "piscataway":
                branch = Branch.Piscataway;
                break;
            case "warren":
                branch = Branch.Warren;
                break;
        }
        if(branch == null){
            System.out.println(branch + " is an invalid branch.");
            return;
        }

        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(depositinput);
        } catch (NumberFormatException e) {
            System.out.println("For input string: \"" + depositinput + "\" - not a valid amount.");
            return;
        }

        if (initialDeposit <= 0 || (type == AccountType.MoneyMarketSavings && initialDeposit < 2000)) {
            System.out.println("Invalid initial deposit amount.");
            return;
        }

        // Create Profile and Account
        Profile holder = new Profile(fname, lname, dob);
        Account account = new Account(new AccountNumber(branch, type), holder, initialDeposit);

        // Check if account already exists
        if (database.contains(account)) {
            System.out.println(holder + " already has a " + type + " account.");
            return;
        }

        // Add account to the database
        database.add(account);
        System.out.println(type + " account " + account.getNumber() + " has been opened.");





    }

    /**
     * Processes "C" command
     */
    private void processClose(StringTokenizer token) {
        if (!token.hasMoreTokens()) {
            System.out.println("Missing account details for closing.");
            return;
        }

        String identifier = token.nextToken();
        if (Character.isDigit(identifier.charAt(0))) { // Close by account number
            AccountNumber accountNumber = new AccountNumber(identifier);
            database.remove(accountNumber);
        } else { // Close by profile
            String firstName = identifier;
            String lastName = token.nextToken();
            String dobStr = token.nextToken();
            Profile profile = new Profile(firstName, lastName, new Date(dobStr));
            database.remove(profile);
        }
    }




}




