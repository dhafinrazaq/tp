package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.session.SessionParserUtil;
import seedu.address.testutil.SessionBuilder;

public class IntervalContainsDatetimePredicateTest {

    private LocalDateTime firstDateTime;
    private LocalDateTime secondDateTime;

    public IntervalContainsDatetimePredicateTest() {
        try {
            firstDateTime = SessionParserUtil.parseStringToDateTime("20/09/2020 1300");
            secondDateTime = firstDateTime.plusMinutes(60);
        } catch (Exception e) {
            // If you reach here, it means your string input to firstDateTime is invalid.
            throw new AssertionError("This should not be reached!");
        }
    }


    @Test
    public void equals() {
        IntervalContainsDatetimePredicate firstPredicate =
                new IntervalContainsDatetimePredicate(firstDateTime);
        IntervalContainsDatetimePredicate secondPredicate =
                new IntervalContainsDatetimePredicate(secondDateTime);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IntervalContainsDatetimePredicate firstPredicateCopy =
                new IntervalContainsDatetimePredicate(firstDateTime);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different Client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // Contains interval (start)
        IntervalContainsDatetimePredicate predicate =
                new IntervalContainsDatetimePredicate(firstDateTime);
        assertTrue(predicate.test(
                new SessionBuilder()
                        .withInterval(SessionParserUtil.parseDateTimeToString(firstDateTime), "60")
                        .build()));

        // Contains interval (end)
        predicate = new IntervalContainsDatetimePredicate(secondDateTime);
        assertTrue(predicate.test(
                new SessionBuilder()
                        .withInterval(SessionParserUtil.parseDateTimeToString(firstDateTime), "60")
                        .build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        IntervalContainsDatetimePredicate predicate = new IntervalContainsDatetimePredicate(secondDateTime);
        assertTrue(predicate.test(
                new SessionBuilder()
                        .withInterval(SessionParserUtil.parseDateTimeToString(firstDateTime), "60")
                        .build()));
    }
}

