package project1;

import util.Date;

import java.io.File;
import java.io.IOException;
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
            // Stop if there's no more input
            if (!scanner.hasNextLine()) {
                break;
            }
            // Read the entire line and trim leading/trailing whitespace
            String command = scanner.nextLine().trim();

            // If the line is empty, skip it
            if (command.isEmpty()) {
                continue;
            }

            // Split the line into tokens
            String[] line = command.split("\\s+");

            // If there are no tokens, skip
            if (line.length == 0) {
                continue;
            }

            // The first token is the command
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
                processA(accountDatabase);
                break;
            case "PS":
                processPS();
                break;
            default:
                System.out.println( "Invalid command!" );
        }

    }

    private  void processA(AccountDatabase accountDatabase) {
        System.out.println("Processing \"activities.txt\"... ");
        File file = new File("/Users/arpeetbarvalia/IdeaProjects/Project 1/src/project1/activities.txt");
        try {
            accountDatabase.processActivities(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Account activities in \"activities.txt\" processed.");
    }
    private  void processPS() {
        database.printStatements();
    }

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
        //Account account = database.createAccount(type, branch, holder, initialDeposit); //new Account(new AccountNumber(branch, type), holder, initialDeposit);
        Account account = null;
        switch (type) {
            case Checking:
            case RegularSavings:
            case MoneyMarketSavings:
                // Use the 4-arg createAccount
                account = database.createAccount(type, branch, holder, initialDeposit);
                break;

            case CollegeChecking:
                if (tokens.length < 8) {
                    System.out.println("Missing campus code for college checking.");
                    return;
                }
                Campus campus = parseCampus(tokens[7]);
                account = database.createAccount(type, branch, holder, initialDeposit, campus);
                break;

            case CD:
                if (tokens.length < 9) {
                    System.out.println("Missing term/open date for certificate deposit.");
                    return;
                }
                int term = Integer.parseInt(tokens[7]);
                Date startDate = new Date(tokens[8]);
                account = database.createAccount(type, branch, holder, initialDeposit, term, startDate);
                break;
        }



        // Check if account already exists
        if (accountDatabase.contains(account)) {
            System.out.println(holder + " already has a " + type + " account.");
            return;
        }

        // Add account to the database
        accountDatabase.add(account);
        System.out.println(type + " account " + account.getNumber() + " has been opened.");
    }

    private Campus parseCampus(String string) {
        switch (string) {
            case "1": return Campus.NewBrunswick;
            case "2": return Campus.Newark;
            case "3": return Campus.Camden;
            default:  return null;
        }
    }

    /**
     * Processes "C" command
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




