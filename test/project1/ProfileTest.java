package project1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    @Test
    public void testPositiveDifferenName() {
        Profile p1 = new Profile("Nikhil", "Shah", 2004, 5, 4);
        Profile p2 = new Profile("Jonathan", "John", 2004, 5, 4);
        assertTrue( p1.compareTo(p2) > 0);
    }

    @Test
    public void testPositiveSameLastDifferentFirstName() {
        Profile p1 = new Profile("Nikhil", "Shah", 2004, 5, 4);
        Profile p2 = new Profile("Jonathan", "Shah", 2004, 5, 4);
        assertTrue( p1.compareTo(p2) > 0);
    }

    @Test
    public void testPositiveLaterBirthDate() {
        Profile p1 = new Profile("Jonathan", "John", 2005, 5, 4);
        Profile p2 = new Profile("Jonathan", "John", 2004, 5, 4);
        assertTrue(p1.compareTo(p2) > 0);
    }

    // **Negative Test Cases (Expected < 0)**

    @Test
    public void testNegativeDifferentName() {
        Profile p1 = new Profile("Jonathan", "John", 2004, 5, 4);
        Profile p2 = new Profile("Nikhil", "Shah", 2004, 5, 4);
        assertTrue( p1.compareTo(p2) < 0);
    }

    @Test
    public void testNegativeSameLastDifferentFirstName() {
        Profile p1 = new Profile("Jonathan", "John", 2004, 5, 4);
        Profile p2 = new Profile("Nikhil", "John", 2004, 5, 4);
        assertTrue(p1.compareTo(p2) < 0);
    }

    @Test
    public void testNegativeEarlierBirthDate() {
        Profile p1 = new Profile("Jonathan", "John", 2004, 5, 4);
        Profile p2 = new Profile("Jonathan", "John", 2005, 5, 4);
        assertTrue( p1.compareTo(p2) < 0);
    }

    // **Zero Test Case (Expected == 0)**

    @Test
    public void testZeroSameProfile() {
        Profile p1 = new Profile("Jonathan", "John", 2004, 5, 4);
        Profile p2 = new Profile("Jonathan", "John", 2004, 5, 4);
        assertEquals( 0, p1.compareTo(p2));
    }
}