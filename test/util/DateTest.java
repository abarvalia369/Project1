package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @Test
    public void testInvalidZeroDay() {
        Date date = new Date(2023, 3, 0);
        assertFalse(date.isValid());
    }

    @Test
    public void testValidDate() {
        Date date = new Date(2003, 5, 22);
        assertTrue(date.isValid());
    }

    @Test
    public void testInvalidMonth() {
        Date date = new Date(2023, 13, 4);
        assertFalse(date.isValid());
    }

    @Test
    public void testInvalidDayInMonth() {
        Date date = new Date(2023, 9, 31);
        assertFalse(date.isValid());
    }

    @Test
    public void testValidLeapYear() {
        Date date = new Date(2024, 2, 29);
        assertTrue(date.isValid());
    }

    @Test
    public void testInvalidLeapYear() {
        Date date = new Date(2021, 2, 29);
        assertFalse(date.isValid());
    }

    @Test
    public void testFutureYearInvalid() {
        Date date = new Date(3005, 2, 29);
        assertFalse(date.isValid());
    }

    @Test
    public void testValidCurrentDate() {
        Date date = new Date(); // Assuming default constructor sets to current date
        assertTrue(date.isValid());
    }
}