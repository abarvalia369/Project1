import java.util.Calendar;

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final int LONGMONTHDAYS = 31;
    public static final int SHORTMONTHDAYS = 30;
    public static final int FEBRUARYDAYS = 28;
    public static final int FEBRUARYLEAP = 29;
    public static final int MONTHFIRSTDAY = 1;

    public Date() {
        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH) + 1; 
        day = today.get(Calendar.DAY_OF_MONTH);
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(Date date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

        /**
     * Return a textual representation of the Date object.
     *
     * @return a string of the date in MM/DD/YYYY Format
     */
    @Override
    public String toString() {
        if(this.isValid()) {
            return String.format("%02d/%02d/%04d", month, day, year);
        }
        return "Invalid Date";
    }

    /**
     * Compare two Date objects based on the sum of the year, month and day values
     *
     * @param date the date object to be compared.
     * @return checks whether they are the same year, month day, at they level (if) they are not the same, returns whether our date is 
     * is the greater or less than comparison date. 
     */
    @Override
    public int compareTo(Date date) {
    if (this.year != date.year) {
        return Integer.compare(this.year, date.year);
    }
    if (this.month != date.month) {
        return Integer.compare(this.month, date.month);
    }
    return Integer.compare(this.day, date.day);
}

    /**
     * @param obj the object to be compared
     * @return return true if two date objects are equal (year, month and day match); return false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return (this.year == date.year && this.month == date.month && this.day==date.day);
        }
        return false;
    }


    // Check if the date is a valid calendar date
    public boolean isValid() {
        //Check if Leap Year according to rules
        boolean isLeapYear = false;
        if(year < 0)
            return false;
        if(year % 4 == 0) { //must be divisible by four  years
            if (year % 100 != 0) { //if divisible by four, must not be divisible by 100
                isLeapYear = true;
            } else if(year % 400 == 0) { // unless also divisible by 400 years
                    isLeapYear = true;
            }
        }
        //Check if valid month
        if(month < JANUARY || month > DECEMBER)
            return false;
        //Check if valid day according to month
        if(day < MONTHFIRSTDAY)
            return false;
        if(month == FEBRUARY) {
            if(isLeapYear) {
                if(day > FEBRUARYLEAP)
                    return false;
            }
            else {
                if(day > FEBRUARYDAYS)
                    return false;
            }
        }
        else {
            if(month == JANUARY || month == MARCH || month == MAY || month == JULY || month == AUGUST || month == OCTOBER || month == DECEMBER) {
                if(day > LONGMONTHDAYS)
                    return false;
            }
            else if(day > SHORTMONTHDAYS)
                return false;
        }
        return true;
    }


    public static void main(String[] args) {
        // The following tests validate our constructor
        Date date1 = new Date();
        System.out.println("Test 1 (Default Constructor): " + date1.toString()); // Expected: Current Date

        Date date2 = new Date(2004, 5, 4);
        System.out.println("Test 2 (Valid Date): " + date2.toString()); // Expected: 05/04/2004

        Date date3 = new Date(2023, 13, 4);
        System.out.println("Test 3 (Invalid Month): " + date3.toString()); // Expected: Invalid Date
        
        //The following tests validate our isValid() method for regular and leap years
        Date date4 = new Date(2003, 5, 22);
        System.out.println("Test 4 (Valid Date): " + date4.isValid()); // Expected: true
  
        Date date5 = new Date(2023, 9, 31);
        System.out.println("Test 5 (Invalid Day): " + date5.isValid()); // Expected: false

        Date date6 = new Date(2024, 2, 29);
        System.out.println("Test 6 (Valid Leap Year): " + date6.isValid()); // Expected: true

        Date date7 = new Date(2021, 2, 29);
        System.out.println("Test 7 (Invalid Leap Year): " + date7.isValid()); // Expected: false

        //The following tests validate our compareTo() and equals() methods
        Date date8 = new Date(2004, 5, 4);
        Date date9 = new Date(2003, 5, 22);
        System.out.println("Test 8 (Same Date): " + date2.compareTo(date8)); // Expected: 0
        System.out.println("Test 9 (Earlier Date): " + date9.compareTo(date8)); // Expected: -1
        System.out.println("Test 10 (Later Date): " + date8.compareTo(date9)); // Expected: 1
        System.out.println("Test 11 (Equal): " + date2.equals(date8)); // Expected: true
        System.out.println("Test 12 (Not Equal): " + date8.equals(date9)); // Expected: false
    }
}