package project1;

import util.Date;
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
    public void run() {
        System.out.println("Transaction Manager is running.");
        Scanner scanner = new Scanner(System.in);
        AccountDatabase accountDatabase = new AccountDatabase();
        while (true) {
            String command = scanner.nextLine();
            String[] line = command.split("\\s+");
            String commandType = line[0];
            handleCommand(commandType, line, accountDatabase);
        }
    }



    private void handleCommand(String commandType, String[] line, AccountDatabase accountDatabase) {
        switch (commandType) {
            case "O":
                processOpen(accountDatabase, line);
                break;
            case "C":
                processClose(accountDatabase, line);
                break;
            case "D":
                processDeposit(accountDatabase, line);
                //database.deposit(accountDatabase, line);
                break;
            case "W":
                processWithdraw(accountDatabase, line);
                //database.withdraw(accountDatabase, line);
                break;
            case "P":
                System.out.println("P command is deprecated");
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
                System.exit(0);
                break;
            case "A":
                break;
            default:
                System.out.println( "Invalid command." );
        }

    }

        /**   originalrun()
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
                    deposit(tokens);
                    break;
                case "W":
                    withdraw(tokens);
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
*/

    /**
     * Processes "O" command
     *
     */
    private void processOpen(AccountDatabase accountDatabase, String[] tokens) {
        if (tokens.length < 7) {
            System.out.println("Missing data needed to open an account.");
            return;
        }

        // tokens[0] is the command "O", so we start with tokens[1]
        String accountType = tokens[1];
        String branchinput = tokens[2];
        String fname = tokens[3];
        String lname = tokens[4];
        String dobinput = tokens[5];
        String depositinput = tokens[6];

        AccountType type = null;
        Branch branch = null;

        // Parse the date of birth
        String[] dateParts = dobinput.split("/");
        int year, month, day;
        try {
            month = Integer.parseInt(dateParts[0]); // MM
            day = Integer.parseInt(dateParts[1]);   // DD
            year = Integer.parseInt(dateParts[2]);    // YYYY
        } catch (NumberFormatException e) {
            System.out.println("DOB invalid: " + dobinput);
            return;
        }
        Date dob = new Date(year, month, day);
        if (!dob.isValid()) {
            System.out.println("DOB invalid: " + dobinput);
            return;
        }

        // Determine account type
        switch (accountType.toLowerCase()) {
            case "checking":
                type = AccountType.Checking;
                break;
            case "regularsavings":
                type = AccountType.RegularSavings;
                break;
            case "moneymarketsavings":
                type = AccountType.MoneyMarketSavings;
                break;
            case "college":
                type = AccountType.CollegeChecking;
                break;
            case "certificate":
                type = AccountType.CD;
                break;
            default:
                System.out.println(accountType + " is an invalid account type.");
                return;
        }

        // Determine branch
        switch (branchinput.toLowerCase()) {
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
            default:
                System.out.println(branchinput + " is an invalid branch.");
                return;
        }

        // Parse initial deposit
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
        Account account = database.createAccount(type, branch, holder, initialDeposit); //new Account(new AccountNumber(branch, type), holder, initialDeposit);

        // Check if account already exists
        if (accountDatabase.contains(account)) {
            System.out.println(holder + " already has a " + type + " account.");
            return;
        }

        // Add account to the database
        accountDatabase.add(account);
        System.out.println(type + " account " + account.getNumber() + " has been opened.");
    }

    /**
     * Processes "C" command
     */

    /**
    private void processClose(AccountDatabase accountDatabase, String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Missing account details for closing.");
            return;
        }

        // tokens[0] is the command "C", so we start with tokens[1]
        String identifier = tokens[1];
        if (Character.isDigit(identifier.charAt(0))) { // Close by account number
            AccountNumber accountNumber = new AccountNumber(identifier);
            accountDatabase.remove(accountNumber);
        } else { // Close by profile: expects first name, last name, and date of birth
            if (tokens.length < 4) {
                System.out.println("Missing account details for closing.");
                return;
            }
            String firstName = tokens[1];
            String lastName = tokens[2];
            String dobStr = tokens[3];
            Profile profile = new Profile(firstName, lastName, new Date(dobStr));
            accountDatabase.remove(profile);
        }
    }
    */

    /**
    private void processClose(AccountDatabase accountDatabase, String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Missing account details for closing.");
            return;
        }
        // tokens[0] is the command "C", so we start with tokens[1]
        String identifier = tokens[1];
        // If identifier starts with a digit, we treat it as an account number string
        if (Character.isDigit(identifier.charAt(0))) {
            boolean found = false;
            // Search through the account database for an account with a matching number
            for (int i = 0; i < accountDatabase.getSize(); i++) {
                Account account = accountDatabase.getAccount(i);
                if (account.getNumber().equals(identifier)) {
                    accountDatabase.remove(account);
                    System.out.println("Account " + identifier + " has been closed.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Account " + identifier + " not found.");
            }
        } else { // Otherwise, close by profile details: first name, last name, and date of birth
            if (tokens.length < 4) {
                System.out.println("Missing account details for closing.");
                return;
            }
            String firstName = tokens[1];
            String lastName = tokens[2];
            String dobStr = tokens[3];

            // Parse the date string (assumes format MM/DD/YYYY)
            String[] dateParts = dobStr.split("/");
            if (dateParts.length != 3) {
                System.out.println("DOB invalid: " + dobStr);
                return;
            }
            int month, day, year;
            try {
                month = Integer.parseInt(dateParts[0]);
                day = Integer.parseInt(dateParts[1]);
                year = Integer.parseInt(dateParts[2]);
            } catch (NumberFormatException e) {
                System.out.println("DOB invalid: " + dobStr);
                return;
            }
            Date dob = new Date(year, month, day);
            if (!dob.isValid()) {
                System.out.println("DOB invalid: " + dobStr);
                return;
            }

            Profile profile = new Profile(firstName, lastName, dob);
            boolean foundAny = false;
            // Iterate over all accounts and remove those matching the given profile.
            // Adjust loop index if removal shifts the array.
            for (int i = 0; i < accountDatabase.getSize(); i++) {
                Account account = accountDatabase.getAccount(i);
                if (account.getHolder().equals(profile)) {
                    accountDatabase.remove(account);
                    System.out.println("Account " + account.getNumber() + " for " + profile + " has been closed.");
                    foundAny = true;
                    i--; // account removed, so shift the index
                }
            }
            if (!foundAny) {
                System.out.println("No accounts found for " + profile + ".");
            }
        }
    }
    */

    private void processClose(AccountDatabase accountDatabase, String[] line) {
        if (line.length == 2) {
            //accountDatabase.remove(line[1]);
        }
        else {
            String [] date = line[3].split("/");
            int month = Integer.parseInt(date[0]);
            int day = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            Date targetDate = new Date(year, month, day);
            Profile targetProfile = new Profile (line[1], line[2], targetDate);
            //accountDatabase.remove(targetProfile);
        }
    }

    private void processDeposit(AccountDatabase accountDatabase, String[] line) {
        // Expect line[1] = accountNumberString, line[2] = depositAmount
        if (line.length < 3) {
            System.out.println("Missing data needed to deposit.");
            return;
        }
        String accountNumberStr = line[1];
        String amountStr        = line[2];

        // 1) Build an AccountNumber from the string
        AccountNumber acctNum = new AccountNumber(accountNumberStr);

        // 2) Parse the deposit amount
        double depositAmount;
        try {
            depositAmount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid deposit amount: " + amountStr);
            return;
        }

        // 3) Call deposit in the database
        //    deposit(AccountNumber, double)
        accountDatabase.deposit(acctNum, depositAmount);
        System.out.println("Deposit of $" + depositAmount + " made to account " + accountNumberStr + ".");
    }

    private void processWithdraw(AccountDatabase accountDatabase, String[] line) {
        // Expect line[1] = accountNumberString, line[2] = withdrawalAmount
        if (line.length < 3) {
            System.out.println("Missing data needed to withdraw.");
            return;
        }
        String accountNumberStr = line[1];
        String amountStr        = line[2];

        // 1) Build an AccountNumber
        AccountNumber acctNum = new AccountNumber(accountNumberStr);

        // 2) Parse the withdrawal amount
        double withdrawAmount;
        try {
            withdrawAmount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid withdrawal amount: " + amountStr);
            return;
        }

        // 3) Call withdraw in the database
        boolean success = accountDatabase.withdraw(acctNum, withdrawAmount);
        if (!success) {
            System.out.println("Withdrawal cannot be completed for account " + accountNumberStr + ".");
        } else {
            System.out.println("Withdrawal of $" + withdrawAmount + " made from account " + accountNumberStr + ".");
        }
    }


}




