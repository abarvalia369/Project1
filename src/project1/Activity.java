package project1;

import util.Date;
import util.List;

public class Activity implements Comparable<Activity> {
    private Date date;
    private Branch location; //the location of the activity
    private char type; //D or W
    private double amount;
    private boolean atm; //true if this is made at an ATM (from the text file)

    public Activity(Date date, Branch location, char type, double amount, boolean atm) {
        this.date = date;
        this.location = location;
        this.type = type;
        this.amount = amount;
        this.atm = atm;
    }

    /**
     * Getter and Setter Methods for Activity
     *
     * @return variables of the constructor
     */
    public Date getDate() {return date;}
    public Branch getLocation() {return location;}
    public char getType() {return type;}
    public double getAmount() {return amount;}
    public boolean atAtm() {return atm;}


    /**
     * Compares 2 Activities
     *
     * @param other the profile object to be compared.
     * @return return 1 if this Activity object is greater than "other", return -1 if smaller;
     * return 0 if they are equal.
     */
    @Override
    public int compareTo(Activity other){
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        int amountComparison = Double.compare(other.amount, this.amount); // Larger amounts first
        if (amountComparison != 0) {
            return amountComparison;
        }
        return Character.compare(this.type, other.type); // Sort deposits before withdrawals if all else is equal
    }

    /**
     * Translates activity to a string
     *
     * @return returns String translation
     */
    @Override
    public String toString(){
        return "Activity [Date: " + date + ", Location: " + location + ", Type: " + type + ", Amount: $" + String.format("%.2f", amount) + ", ATM: " + atm + "]";
    }



}
