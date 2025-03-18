package project1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AccountDatabaseTest {

    private AccountDatabase accountDatabase;
    private AccountNumber regularAccount;
    private AccountNumber moneyMarketAccount;


    @Test
    public void testDepositToAnyAccount() {
        accountDatabase = new AccountDatabase();

        // Creating test accounts
        Profile profile = new Profile("fname","lname", 2000, 05,  04);
        regularAccount = new AccountNumber("200027410");  // Regular Account
        moneyMarketAccount = new AccountNumber("200037979"); // Money Market Account

        // Adding accounts to database with initial balances
        accountDatabase.add(new Savings(regularAccount, profile,3000.00, true));
        accountDatabase.add(new MoneyMarket(moneyMarketAccount,profile,4500.00, true));

        accountDatabase.deposit(regularAccount, 500.00);
        assertEquals(3500.00, accountDatabase.get(0).getBalance());
    }

    @Test
    public void testDepositToMoneyMarketThreshold() {
        accountDatabase = new AccountDatabase();

        // Creating test accounts
        Profile profile = new Profile("fname","lname", 2000, 05,  04);
        regularAccount = new AccountNumber("200027410");  // Regular Account
        moneyMarketAccount = new AccountNumber("200037979"); // Money Market Account

        // Adding accounts to database with initial balances
        accountDatabase.add(new Savings(regularAccount, profile,3000.00, true));
        accountDatabase.add(new MoneyMarket(moneyMarketAccount,profile,4500.00, true));

        accountDatabase.deposit(moneyMarketAccount, 1000.00);
        assertEquals(5500.00, accountDatabase.get(4).getBalance(), 0.01);
    }

    // **Withdraw Tests**

    @Test
    public void testWithdrawValidAmount() {
        accountDatabase = new AccountDatabase();

        // Creating test accounts
        Profile profile = new Profile("fname","lname", 2000, 05,  04);
        regularAccount = new AccountNumber("200027410");  // Regular Account
        moneyMarketAccount = new AccountNumber("200037979"); // Money Market Account

        // Adding accounts to database with initial balances
        accountDatabase.add(new Savings(regularAccount, profile,3000.00, true));
        accountDatabase.add(new MoneyMarket(moneyMarketAccount,profile,4500.00, true));

        boolean result = accountDatabase.withdraw(regularAccount, 1000.00);
        assertTrue(result);
        assertEquals( 2000.00, accountDatabase.get(0).getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        accountDatabase = new AccountDatabase();

        // Creating test accounts
        Profile profile = new Profile("fname","lname", 2000, 05,  04);
        regularAccount = new AccountNumber("200027410");  // Regular Account
        moneyMarketAccount = new AccountNumber("200037979"); // Money Market Account

        // Adding accounts to database with initial balances
        accountDatabase.add(new Savings(regularAccount, profile,3000.00, true));
        accountDatabase.add(new MoneyMarket(moneyMarketAccount,profile,4500.00, true));

        boolean result = accountDatabase.withdraw(regularAccount, 5000.00);
        assertFalse(result);
    }

    @Test
    public void testWithdrawMoneyMarketThreshold() {
        accountDatabase = new AccountDatabase();

        // Creating test accounts
        Profile profile = new Profile("fname","lname", 2000, 05,  04);
        regularAccount = new AccountNumber("200027410");  // Regular Account
        moneyMarketAccount = new AccountNumber("200037979"); // Money Market Account

        // Adding accounts to database with initial balances
        accountDatabase.add(new Savings(regularAccount, profile,3000.00, true));
        accountDatabase.add(new MoneyMarket(moneyMarketAccount,profile,4500.00, true));


        boolean result = accountDatabase.withdraw(moneyMarketAccount, 1000.00);

        for (int i = 0; i < accountDatabase.size(); i++) {
            System.out.println("Index: " + i + ", Account: " + accountDatabase.get(i));
        }
        assertTrue(result);
        assertEquals(3500.00, accountDatabase.get(1).getBalance());
    }
}