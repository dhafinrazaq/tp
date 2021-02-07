package seedu.address.model.session;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Session}'s {@code interval} contains datetime given (inclusive of interval's start and end time).
 */
public class IntervalContainsDatetimePredicate implements Predicate<Session> {
    private final LocalDateTime datetime;

    public IntervalContainsDatetimePredicate(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean test(Session session) {
        Interval sessionInterval = session.getInterval();
        return !datetime.isBefore(sessionInterval.getStart()) && !datetime.isAfter(sessionInterval.getEnd());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IntervalContainsDatetimePredicate // instanceof handles nulls
                && datetime.equals(((IntervalContainsDatetimePredicate) other).datetime)); // state check
    }
}
