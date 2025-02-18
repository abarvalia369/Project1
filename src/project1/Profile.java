package project1;

public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, int year, int month, int day) {
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(year, month, day);
    }

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    
    public String getFname() {
        return fname;
    }

    public Date getDob() {
        return dob;
    }

    public String getLname() {
        return lname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public static boolean isAdult(Date dob){
        if (!dob.isValid()) {
            return false;
        }
        Date today = new Date();
        Date adult = new Date(today.getYear() - 18, today.getMonth(), today.getDay());

        return dob.compareTo(adult) <= 0; 
    }

    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }



    /**
     * @param obj check if obj equals Profile
     * @return return true if two profile objects match, if not, return false. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return (this.fname.equalsIgnoreCase(profile.fname) && this.lname.equalsIgnoreCase(profile.lname) && this.dob.equals(profile.dob));
        }
        return false;
    }

    /**
     * Translates 2 Profiles to String variables, and compares them lexographically. 
     *
     * @param profile the profile object to be compared.
     * @return return 1 if this profile object is greater than "profile", return -1 if smaller;
     * return 0 if they are equal.
     */
    @Override
    public int compareTo(Profile profile) {
        String p1 = this.fname + this.lname + this.dob.toString();
        String p2 = profile.fname + profile.lname + profile.dob.toString();
        if (p1.equals(p2)){
            return 0;
        } else if(p1.compareTo(p2) > 0){
            return 1;
        }
        else{
            return -1; 
        }
    }

    
    public static void main(String[] args) {
        //Different names
        Profile p1 = new Profile("Jonathan", "John", 2004, 5, 4);
        Profile p2 = new Profile("Katlen", "Weng", 2003, 5, 22);
        System.out.println("Test Case 1 (Different Names): " + p1.compareTo(p2)); // Expected: -1 (1st)

        //Different first names
        Profile p3 = new Profile("Lebron", "James", 1984, 12, 30);
        Profile p4 = new Profile("Savannah", "James", 1984, 12, 29);
        System.out.println("Test Case 2 (Same Last Name, Different First Names): " + p3.compareTo(p4)); // Expected: -1 (2nd)

        //Different dates of birth
        Profile p5 = new Profile("Jonathan", "John", 2004, 5, 5);
        Profile p6 = new Profile("Jonathan", "John", 2004, 5, 4);
        System.out.println("Test Case 3 (Different Date): " + p5.compareTo(p6)); // Expected: 1 (1st)

        //Everything Same
        Profile p7 = new Profile("Arpeet", "Barvalia", 2004, 6, 20);
        Profile p8 = new Profile("Arpeet", "Barvalia", 2004, 6, 20);
        System.out.println("Test Case 4 (Same): " + p7.compareTo(p8)); //Expected: 0 (1st)
        System.out.println("Test Case 5 (Equals Method): " + p7.equals(p8)); //Expected: true

        //Invalid date 
        Profile p9 = new Profile("Viktor", "Wenbanyama", 2004, 2, 31); 
        System.out.println("Test Case 6 (Invalid Date): " + p9.toString()); //Expected: Invalid

        // Edge case
        Profile p10 = new Profile("Jonathann", "John", 2004, 05, 04);
        Profile p11 = new Profile("Jonathan", "John", 2004, 05, 04);
        System.out.println("Test Case 7(Edge Case): " + p10.compareTo(p11)); // Expected: 1 (2nd)

        // Edge case
        Profile p12 = new Profile("Jonathan", "John", 2004, 05, 04);
        Profile p13 = new Profile("Jonathan", "Johnn", 2004, 05, 04);
        System.out.println("Test Case 8(Edge Case): " + p12.compareTo(p13)); // Expected: -1 (3rd)

        //Comparison using differently constructed Profiles
        Date test = new Date( 2004, 05, 05);
        Profile p14 = new Profile("Jonathan", "John", test);
        Profile p15 = new Profile("Jonathan", "John", 2004, 05, 04);
        System.out.println("Test Case 9(Different Constructor): " + p14.compareTo(p15)); // Expected: 1 (3rd)

        //Are they old enough?
        Date dob1 = new Date(2004, 05, 04);
        Date dob2 = new Date(2010, 05, 04);
        System.out.println(isAdult(dob1)); 
        System.out.println(isAdult(dob2));
        

    }
}