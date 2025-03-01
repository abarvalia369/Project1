package project1;

/**
 * An Enum class that defines the name of the campus types with an additional property listed below.
 * Campuses: 1 checking, 2 regular savings, and 3 money market savings.
 *
 * @author arpeet barvalia, jonathan john
 */
public enum Campus {

    NewBrunswick("1"),
    Newark("2"),
    Camden("3");


    private String code;

    Campus( String code ){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}
