package seedu.address.model.session;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.logic.parser.session.SessionParserUtil;
import seedu.address.model.CheckExisting;

/**
 * Represents a training Session in FitEgo.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Session implements CheckExisting<Session>, Comparable<Session> {
    // Identity fields
    private final Gym gym;
    private final Interval interval;

    // Data fields
    private final ExerciseType exerciseType;

    /**
     * Every field must be present and not null.
     */
    public Session(Gym gym, ExerciseType exerciseType, Interval interval) {
        requireAllNonNull(gym, exerciseType, interval);
        this.exerciseType = exerciseType;
        this.interval = interval;
        this.gym = gym;
    }

    //Getters / Setters

    public Gym getGym() {
        return gym;
    }

    public Interval getInterval() {
        return interval;
    }

    public LocalDateTime getStartTime() {
        return interval.getStart();
    }

    public LocalDateTime getEndTime() {
        return interval.getEnd();
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    /**
     * Returns true if both Sessions have overlapping intervals.
     * Two sessions are defined as overlapping if its Intervals are overlapping {@see Interval#isOverlap}.
     * This defines a different notion of equality between two Sessions compared to {@code equals}.
     */
    @Override
    public boolean isIdentical(Session otherSession) {
        if (otherSession == this) {
            return true;
        }

        if (otherSession == null) {
            return false;
        }

        return Interval.isOverlap(this.getInterval(), otherSession.getInterval());
    }

    /**
     * Returns true if both Session have the same identity and data fields.
     * This defines a stronger notion of equality between two Sessions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        return otherSession.getGym().equals(getGym())
                && otherSession.getInterval().equals(getInterval())
                && otherSession.getExerciseType().equals(getExerciseType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(gym, interval, exerciseType);
    }

    @Override
    public int compareTo(Session session) {
        return this.getStartTime().compareTo(session.getStartTime());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Start: ")
                .append(SessionParserUtil.parseDateTimeToString(getStartTime()))
                .append(" End: ")
                .append(SessionParserUtil.parseDateTimeToString(getEndTime()))
                .append(" Gym: ")
                .append(gym)
                .append(" Exercise Type: ")
                .append(exerciseType);
        return builder.toString();
    }
}
