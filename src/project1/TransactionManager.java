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

            StringTokenizer token = new StringTokenizer( commandLine );
            String command = token.nextToken();

            switch (command){
                case "O":
                    processOpen(tokens);
                    break;
                case "C":
                    processClose(tokens);
                    break;
                case "D":
                    processDeposit(tokens);
                    break;
                case "W":
                    processWithdraw(tokens);
                    break;
                case "P":
                    database.print();

                case "PA":

                case "PB":

                case "PH":

                case "PT":

                case "Q":


            }


        }

    }



}




