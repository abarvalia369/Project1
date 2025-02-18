package project1;

/**
 * An Enum class that defines the branch locations with the city names
 * as well as zip code, branch code, and county.
 *
 * @author arpeet barvalia, jonathan john
 */
public enum Branch {

    Edison( "100", "Middlesex", "08817" ),
    Bridgewater( "200", "Somerset", "08807" ),
    Princeton( "300", "Mercer", "08542" ),
    Piscataway( "400", "Middlesex", "08854" ),
    Warren( "500", "Somerset", "07057" );

    private final String branchCode;
    private final String     county;
    private final String        zip;

    /**
     * Initializes a Branch Object with the following 3 parameters.
     *
     * @param branchCode the branch code for the new branch object.
     * @param county the county for the new branch object.
     * @param zip the zip balance for the new branch object.
     */
    Branch( String branchCode, String county, String zip ){
        this.branchCode = branchCode;
        this.county = county;
        this.zip = zip;
    }

    public String getBranchCode(){
        return branchCode;
    }
    public String getCounty(){
        return county;
    }
    public String getZip(){
        return zip;
    }

    public String getBranchName(){
        return this.name();
    }

}