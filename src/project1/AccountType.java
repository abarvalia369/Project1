package project1;

/**
 * An Enum class that defines the name of the account types with an additional property listed below.
 * Account types: 01 checking, 02 regular savings, and 03 money market savings.
 *
 * @author arpeet barvalia, jonathan john
 */
public enum AccountType {

    Checking("01"),
    RegularSavings("02"),
    MoneyMarketSavings("03");

    private String code;

    AccountType( String code ){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}