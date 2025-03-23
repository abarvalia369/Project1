package project1;

import util.Date;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * A user interface class to process the transactions entered on the terminal. An instance of this class
 * can process a single command line or multiple command lines at a time, including the empty lines.
 *
 * @author arpeet barvalia, jonathan john
 */
public class TransactionManager {
    private AccountDatabase database;
    private Scanner scanner;
    /**
     * Initalizes transaction manager
     */
    public TransactionManager(){
        this.scanner = new Scanner(System.in);
        this.database = new AccountDatabase();

        File accountsFile = new File("accounts.txt");
        if (!accountsFile.exists()) {
            System.out.println("ERROR: accounts.txt not found!");
            return;
        }

        try {
            database.loadAccounts(accountsFile);
            System.out.println("Accounts in 'accounts.txt' loaded to the database.");
        } catch (IOException e) {
            System.out.println("Error loading accounts from accounts.txt.");
        }
    }

    /**
     * Runs transaction manager
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        AccountDatabase accountDatabase = new AccountDatabase();

        System.out.println("Transaction Manager is running.");

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
            handleCommand(commandType, line, database);
        }
    }



    private void handleCommand(String commandType, String[] line, AccountDatabase accountDatabase) {
            String formatted = commandType.trim();
        switch (formatted) {
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
                accountDatabase.printArchive();
                break;
            case "PB":
                accountDatabase.printByBranch();
                break;
            case "PH":
                accountDatabase.printByHolder();
                break;
            case "PT":
                accountDatabase.printByType();
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
        File file = new File("activities.txt");
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

        Date today = new Date();
        int todayYear = today.getYear();
        int todayMonth = today.getMonth();
        int todayDay = today.getDay();

        int birthYear = dob.getYear();
        int birthMonth = dob.getMonth();
        int birthDay = dob.getDay();

        int age = todayYear - birthYear;
        if (todayMonth < birthMonth || (todayMonth == birthMonth && todayDay < birthDay)) {
            age--;
        }

        if (age < 18) {
            System.out.println("Applicant must be at least 18 years old.");
            return;
        }


        // Determine account type
        switch (accountType.trim().toLowerCase()) {
            case "checking":
                type = AccountType.Checking;
                break;
            case "regularsavings", "savings":
                type = AccountType.RegularSavings;
                break;
            case "moneymarketsavings", "moneymarket":
                type = AccountType.MoneyMarketSavings;
                break;
            case "college", "collegechecking":
                type = AccountType.CollegeChecking;
                break;
            case "certificate", "certificatedeposit", "cd":
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

        if (initialDeposit <= 0 || (type == AccountType.MoneyMarketSavings && initialDeposit < 2000) || (type == AccountType.CD && initialDeposit < 1000)){
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
                if (exists(holder,type)) {
                    System.out.println(holder + " already has a " + type + " account.");
                    return;
                }
                account = database.createAccount(type, branch, holder, initialDeposit);
                break;

            case CollegeChecking:
                if (tokens.length < 8) {
                    System.out.println("Missing data tokens for opening an account.");
                    return;
                }
                if (age >= 24) {
                    System.out.println("Not eligible to open: " + dob.toString() + " over 24.");
                    return;
                }
                if (exists(holder,type)) {
                    System.out.println(holder + " already has a " + type + " account.");
                    return;
                }
                Campus campus = parseCampus(tokens[7]);
                account = database.createAccount(type, branch, holder, initialDeposit, campus);
                break;

            case CD:
                if (tokens.length < 9) {
                    System.out.println("Missing data tokens for opening an account.");
                    return;
                }
                int term = Integer.parseInt(tokens[7]);
                Date startDate = new Date(tokens[8]);
                if(term != 3 && term != 6 && term != 9 && term != 12){
                    System.out.println(term + " is not a valid term.");
                    return;
                }
                account = database.createAccount(type, branch, holder, initialDeposit, term, startDate);

                break;
        }


        // Add the account
        database.add(account);
        System.out.println(type + " account " + account.getNumber() + " has been opened.");

    }

    private boolean exists(Profile holder, AccountType type){

        for (int i = 0; i < this.database.size(); i++) {
            Account existing = this.database.get(i);

            if (existing.getHolder().equals(holder) && existing.getNumber().getAccountType() == type) {
                //System.out.println(" Duplicate found: " + holder + " already has a " + type + " account.");
                return true;
            }
        }

        return false;
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
        if (line.length < 3) {
            System.out.println("Missing data needed to deposit.");
            return;
        }

        if (line.length ==4){
            System.out.println("Missing data needed to deposit.");
            return;
        }

        if (line.length == 3) {
            closeByAcc(accountDatabase, line);
        }
        else if (line.length == 5){
            closeByProfile(accountDatabase, line);
        }

    }

    private void closeByAcc(AccountDatabase accountDatabase, String[] line){
        String [] date = line[1].split("/");
        int month = Integer.parseInt(date[0]);
        int day = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        Date closeDate = new Date(year, month, day);
        String accountNumberStr = line[2];
        int index = database.findAccount(accountNumberStr);
        if(index == -1){
            System.out.println(accountNumberStr + " does not exist");
            return;
        }
        Account acct = database.get(index);
        AccountNumber acctNum = acct.getNumber();
        double earnedInterest = 0;
        if (acct instanceof CertificateDeposit cd) {
            Date openDate = cd.getOpen(); // assuming getter exists
            int daysOpen = daysBetween(openDate, closeDate) + 1;
            if (daysOpen >= cd.getTerm() * 30) {
                double rate = cd.getRate(); // based on 3, 6, 9, 12 month terms
                earnedInterest = cd.getBalance() * (rate / 365.0) * daysOpen;
                String formatted = String.format("$%.2f", earnedInterest);
                System.out.println("Closing account " + acctNum.toString());
                System.out.println("--" + acctNum + " interest earned: " + formatted);
            } else {
                double earlyRate;
                if (daysOpen / 30.0 <= 6) earlyRate = 0.03;
                else if (daysOpen / 30.0 <= 9) earlyRate = 0.0325;
                else earlyRate = 0.035;

                earnedInterest = cd.getBalance() * (earlyRate / 365.0) * daysOpen;
                double penalty = earnedInterest * 0.10;
                penalty = roundUpToTwoDecimal(penalty);
                String formatted = String.format("$%.2f", earnedInterest);
                System.out.println("Closing account " + acctNum.toString());
                System.out.println("--interest earned: " + formatted);
                System.out.println("  [penalty] $" + penalty);
            }
        } else {
            int daysInMonth = closeDate.getDay();
            double annualRate = getAnnualRate(acct);
            earnedInterest = acct.getBalance() * (annualRate / 365.0) * daysInMonth;
            System.out.println("Closing account " + acctNum.toString());
            String formatted = String.format("$%.2f", earnedInterest);
            System.out.println("--interest earned: " +  formatted);
        }
        accountDatabase.getArchive().add(acct, closeDate);
        accountDatabase.remove(acct);
    }

    private void closeByProfile(AccountDatabase accountDatabase, String[] line){
        // Parse the date
        String[] dateParts = line[1].split("/");
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        Date closeDate = new Date(year, month, day);

        // Parse profile info
        String firstName = line[2];
        String lastName = line[3];
        String[] dobParts = line[4].split("/");
        int dobMonth = Integer.parseInt(dobParts[0]);
        int dobDay = Integer.parseInt(dobParts[1]);
        int dobYear = Integer.parseInt(dobParts[2]);
        Date dob = new Date(dobYear, dobMonth, dobDay);
        Profile profile = new Profile(firstName, lastName, dob);

        boolean found = false;
        System.out.println("Closing accounts for " + profile);

        // Loop through accounts and collect matches
        for (int i = 0; i < database.size(); i++) {
            Account acct = database.get(i);
            if (acct != null && acct.getHolder().equals(profile)) {
                found = true;
                AccountNumber acctNum = acct.getNumber();
                double earnedInterest = 0;
                if (acct instanceof CertificateDeposit cd) {
                    Date openDate = cd.getOpen(); // assuming getter exists
                    int daysOpen = daysBetween(openDate,closeDate) + 1;
                    if (daysOpen >= cd.getTerm() * 30) {
                        System.out.println(">>> Treated as matured CD");
                        double rate = cd.getRate(); // based on 3, 6, 9, 12 month terms
                        earnedInterest = cd.getBalance() * (rate / 365.0) * daysOpen;
                        String formatted = String.format("$%.2f", earnedInterest);
                        System.out.println("--" + acctNum + " interest earned: " + formatted);
                    } else {
                        double earlyRate = 0.;
                        if (daysOpen / 30.0  <= 6) earlyRate = 0.03;
                        else if (daysOpen / 30.0 <= 9) earlyRate = 0.0325;
                        else if (daysOpen / 30.0  < 12) earlyRate = 0.035;

                        earnedInterest = cd.getBalance() * (earlyRate / 365.0) * daysOpen;
                        double penalty = earnedInterest * 0.10;
                        penalty = roundUpToTwoDecimal(penalty);
                        String formatted = String.format("$%.2f", earnedInterest);
                        System.out.println("--" + acctNum + " interest earned: " + formatted);
                        System.out.println("  [penalty] $" + penalty);
                    }
                } else {
                    int daysInMonth = closeDate.getDay();
                    double annualRate = getAnnualRate(acct);
                    earnedInterest = acct.getBalance() * (annualRate / 365.0) * daysInMonth;
                    String formatted = String.format("$%.2f", earnedInterest);
                    System.out.println("--" + acctNum + " interest earned: " + formatted);
                }

                database.getArchive().add(acct, closeDate);
                database.remove(acct);
                i--;
            }
        }
        if (!found) {
            System.out.println(profile + " does not have any accounts in the database.");
        } else {
            System.out.println("All accounts for " + profile + " are closed and moved to archive.");
        }
    }

    double roundUpToTwoDecimal(double amount) {
        return Math.ceil(amount * 100.0) / 100.0;
    }

    private double getAnnualRate(Account acct){
        AccountType type = acct.getNumber().getAccountType();

        if (type == AccountType.RegularSavings && acct instanceof Savings savings) {
            return savings.loyalty() ? 0.0275 : 0.025;
        }
        else if (type == AccountType.MoneyMarketSavings && acct instanceof MoneyMarket moneyMarket) {
            return moneyMarket.loyalty() ? 0.0375 : 0.035;
        }
        else if (type == AccountType.Checking || type == AccountType.CollegeChecking) {
            return 0.015;
        }

        return 0.0; // unknown type
    }

    public int daysBetween(Date d1, Date d2) {
        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        int total1 = d1.getYear() * 365 + d1.getDay();
        for (int i = 0; i < d1.getMonth() - 1; i++) {
            total1 += daysInMonth[i];
        }

        int total2 = d2.getYear() * 365 + d2.getDay();
        for (int i = 0; i < d2.getMonth() - 1; i++) {
            total2 += daysInMonth[i];
        }

        return Math.abs(total2 - total1);
    }


    private void processDeposit(AccountDatabase accountDatabase, String[] line) {
        // Expect line[1] = accountNumberString, line[2] = depositAmount
        if (line.length < 3) {
            System.out.println("Missing data needed to deposit.");
            return;
        }
        String accountNumberStr = line[1];
        String amountStr        = line[2];

        int index = database.findAccount(accountNumberStr);
        if(index == -1){
            System.out.println(accountNumberStr + " does not exist");
            return;
        }
        Account acct = database.get(index);
        AccountNumber acctNum = acct.getNumber();

        double depositAmount;
        try {
            depositAmount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid deposit amount: " + amountStr);
            return;
        }
        if(depositAmount <= 0){
            System.out.println(depositAmount + " - deposit amount cannot be 0 or negative.");
            return;
        }
        if(!accountExists(acctNum)){
            System.out.println(acctNum + " does not exist");
            return;
        }

        System.out.println("BEFORE " + acct.getBalance());
        database.deposit(acctNum, depositAmount);
        System.out.println(depositAmount + " deposited to " + accountNumberStr + ".");
        System.out.println("AFTER " + acct.getBalance());
    }

    private boolean accountExists(AccountNumber num){

        for (int i = 0; i < this.database.size(); i++) {
            Account existing = this.database.get(i);
            //System.out.println("This is the num : " + existing.getNumber() );
            if (existing.getNumber().equals(num)) {
                return true;
            }
        }

        return false;
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
        int index = database.findAccount(accountNumberStr);
        if(index == -1){
            System.out.println(accountNumberStr + " does not exist");
            return;
        }
        Account acct = database.get(index);
        AccountNumber acctNum = acct.getNumber();

        // 2) Parse the withdrawal amount
        double withdrawAmount;
        try {
            withdrawAmount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid withdrawal amount: " + amountStr);
            return;
        }

        if(withdrawAmount <= 0){
            System.out.println(withdrawAmount + " - deposit amount cannot be 0 or negative.");
            return;
        }

        System.out.println("BEFORE " + acct.getBalance());
        boolean success = database.withdraw(acctNum, withdrawAmount);
        if (!success && (acctNum.getAccountType() == AccountType.MoneyMarketSavings) &&  acct.getBalance() < 2000) {
            System.out.println(accountNumberStr + " balance below $2,000 - withdrawing " + withdrawAmount + " - insufficient funds.");
        }else if(!success){
            System.out.println(accountNumberStr + " withdrawing " + withdrawAmount + " - insufficient funds.");
        }else if((acctNum.getAccountType() == AccountType.MoneyMarketSavings) &&  acct.getBalance() < 2000){
            System.out.println(accountNumberStr + " balance below $2,000 - " + withdrawAmount + " withdrawn from " + accountNumberStr);
        }else{
            System.out.println(withdrawAmount + " withdrawn from " + accountNumberStr);
        }
        System.out.println("AFTER " + acct.getBalance());
    }


}




