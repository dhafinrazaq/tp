package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class IntervalTest {
    private static final LocalDateTime VALID_START_TIME_A = LocalDateTime.now();
    private static final LocalDateTime VALID_START_TIME_B = LocalDateTime.of(
            2017, 2, 13, 15, 56);
    private static final DateTimeFormatter SIMPLE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EE dd MMM YYYY");


    @Test
    public void constructor_nullStartTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Interval(null, 1));
    }

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        LocalDateTime validStartTime = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> new Interval(validStartTime, -1));
    }

    @Test
    public void getFormattedDateTime() {
        Interval validInterval = new Interval(VALID_START_TIME_B, 60);
        assertEquals("Mon 13 Feb 2017", validInterval.getFormattedStartDateTime(SIMPLE_DATE_TIME_FORMATTER));
    }

    @Test
    public void getFormattedTime12hrPattern() {
        Interval validInterval = new Interval(VALID_START_TIME_B, 60);
        assertEquals("03:56 PM - 04:56 PM", validInterval.getTime12hrPattern());
    }

    @Test
    public void isPastMidnight() {
        // end is around 0100hrs on the day after start.
        Interval validInterval = new Interval(VALID_START_TIME_B, 540);
        assertTrue(validInterval.isPastMidnight());

        // end is on the same day as start, but later.
        Interval validInterval2 = new Interval(VALID_START_TIME_B, 60);
        assertFalse(validInterval2.isPastMidnight());

        // end is at 0000hrs on the day after start.
        Interval validInterval3 = new Interval(VALID_START_TIME_B, 484);
        assertTrue(validInterval3.isPastMidnight());

        // end's date of month is lower than start's date of month. Compare Feb 13 2017 and Mar 8 2017.
        Interval validInterval4 = new Interval(VALID_START_TIME_B, 33120);
        assertTrue(validInterval4.isPastMidnight());

        // end's date of month is equal to start's date of month. Compare Feb 13 2017 and Mar 12 2017.
        Interval validInterval5 = new Interval(VALID_START_TIME_B, 40320);
        assertTrue(validInterval5.isPastMidnight());

        // end is exactly 1 year after start. Compare Feb 13 2017 and Feb 12 2018.
        Interval validInterval6 = new Interval(VALID_START_TIME_B, 525600);
        assertTrue(validInterval6.isPastMidnight());
    }

    @Test
    public void getAdjustedStartDateTime() {
        // end is around 0100hrs on the day after start.
        Interval validInterval = new Interval(VALID_START_TIME_B, 540);
        assertEquals("03:56 PM - Tue 14 Feb 2017 12:56 AM", validInterval.getAdjustedStartDateTime());

        // end is on the same day as start, but later.
        Interval validInterval2 = new Interval(VALID_START_TIME_B, 60);
        assertEquals("03:56 PM - 04:56 PM", validInterval2.getAdjustedStartDateTime());

        // end is at 0000hrs on the day after start.
        Interval validInterval3 = new Interval(VALID_START_TIME_B, 484);
        assertEquals("03:56 PM - Tue 14 Feb 2017 12:00 AM", validInterval3.getAdjustedStartDateTime());

        // end is exactly 1 year after start. Compare Feb 13 2017 and Feb 12 2018.
        Interval validInterval4 = new Interval(VALID_START_TIME_B, 525600);
        assertEquals("03:56 PM - Tue 13 Feb 2018 03:56 PM", validInterval4.getAdjustedStartDateTime());
    }

    @Test
    public void isValidInterval() {
        // invalid Intervals
        assertFalse(Interval.isValidInterval(0));
        assertFalse(Interval.isValidInterval(-1));

        // valid Intervals
        assertTrue(Interval.isValidInterval(20));
    }

    @Test
    public void isOverlap() {
        LocalDateTime earlierTime = LocalDateTime.of(2020, 9, 20, 16, 0);
        LocalDateTime laterTime = LocalDateTime.of(2020, 9, 20, 17, 0);

        // A ---------- A
        //    B --- B
        assertTrue(Interval.isOverlap(new Interval(earlierTime, 120), new Interval(laterTime, 45)));
        assertTrue(Interval.isOverlap(new Interval(laterTime, 45), new Interval(earlierTime, 120)));

        // A ---------- A
        // B --- B
        assertTrue(Interval.isOverlap(new Interval(earlierTime, 59), new Interval(earlierTime, 40)));
        assertTrue(Interval.isOverlap(new Interval(earlierTime, 59), new Interval(earlierTime, 60)));

        // A ---------- A
        //      B ----- B
        assertTrue(Interval.isOverlap(new Interval(earlierTime, 120), new Interval(laterTime, 60)));
        assertTrue(Interval.isOverlap(new Interval(laterTime, 60), new Interval(earlierTime, 120)));

        // A --------- A
        //      B --------- B
        assertTrue(Interval.isOverlap(new Interval(earlierTime, 61), new Interval(laterTime, 100)));
        assertTrue(Interval.isOverlap(new Interval(laterTime, 100), new Interval(earlierTime, 61)));

        // A --------- A
        //             B ---------- B
        assertFalse(Interval.isOverlap(new Interval(earlierTime, 60), new Interval(laterTime, 120)));

        // A --------- A
        //                B ---------- B
        assertFalse(Interval.isOverlap(new Interval(earlierTime, 59), new Interval(laterTime, 120)));
    }

    @Test
    public void hashcode() {
        // same values -> returns same hashcode
        assertEquals(new Interval(VALID_START_TIME_A, 60).hashCode(),
                new Interval(VALID_START_TIME_A, 60).hashCode());

        // different startTime value -> returns different hashcode
        assertNotEquals(new Interval(VALID_START_TIME_A, 60).hashCode(),
                new Interval(VALID_START_TIME_B, 60).hashCode());

        // different duration value -> returns different hashcode
        assertNotEquals(new Interval(VALID_START_TIME_A, 60).hashCode(),
                new Interval(VALID_START_TIME_A, 40).hashCode());
    }
}

