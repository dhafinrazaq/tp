package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalSchedules.ALICE_GETWELL;
import static seedu.address.testutil.TypicalSchedules.ALICE_MACHOMAN;
import static seedu.address.testutil.TypicalSchedules.BENSON_GETWELL;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_PAID;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_UNPAID;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ScheduleBuilder;
import seedu.address.testutil.TypicalSessions;

public class ScheduleTest {
    @Test
    public void isIdenticalSchedule() {
        // same object -> returns true
        assertTrue(ALICE_GETWELL.isIdentical(ALICE_GETWELL));

        // null -> returns false
        assertFalse(ALICE_GETWELL.isIdentical(null));

        // different payment status, everything else the same -> returns true
        Schedule unpaidSchedule = new ScheduleBuilder(ALICE_GETWELL)
                .withPaymentStatus(PAYMENT_UNPAID).build();
        Schedule paidSchedule = new ScheduleBuilder(ALICE_GETWELL)
                .withPaymentStatus(PAYMENT_PAID).build();
        assertTrue(unpaidSchedule.isIdentical(paidSchedule));

        // different client -> returns false
        assertFalse(ALICE_GETWELL.isIdentical(BENSON_GETWELL));

        // different session -> returns false
        assertFalse(ALICE_GETWELL.isIdentical(ALICE_MACHOMAN));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Schedule aliceGetwellCopy = new ScheduleBuilder(ALICE_GETWELL).build();
        assertTrue(ALICE_GETWELL.equals(aliceGetwellCopy));

        // same object -> returns true
        assertTrue(ALICE_GETWELL.equals(ALICE_GETWELL));

        // null -> returns false
        assertFalse(ALICE_GETWELL.equals(null));

        // different client -> returns false
        assertFalse(ALICE_GETWELL.equals(BENSON_GETWELL));

        // different session -> returns false
        assertFalse(ALICE_GETWELL.equals(ALICE_MACHOMAN));

        // different payment status -> returns false
        Schedule paidAliceGetwell = new ScheduleBuilder(ALICE_GETWELL)
                .withPaymentStatus(PAYMENT_PAID).build();
        Schedule unpaidAliceGetwell = new ScheduleBuilder(ALICE_GETWELL)
                .withPaymentStatus(PAYMENT_UNPAID).build();
        assertFalse(paidAliceGetwell.equals(unpaidAliceGetwell));

        // test getter
        Schedule schedule = new ScheduleBuilder().withSession(TypicalSessions.MACHOMAN).build();
        assertEquals(schedule.getInterval(), TypicalSessions.MACHOMAN.getInterval());
        assertEquals(schedule.getExerciseType(), TypicalSessions.MACHOMAN.getExerciseType());
    }

    @Test
    public void isPaid() {
        Schedule paidSchedule = new ScheduleBuilder()
                .withPaymentStatus(PAYMENT_PAID).build();
        assertTrue(paidSchedule.isPaid() == true);

        Schedule unpaidSchedule = new ScheduleBuilder()
                .withPaymentStatus(PAYMENT_UNPAID).build();
        assertTrue(unpaidSchedule.isPaid() == false);
    }
}
