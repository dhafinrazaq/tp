package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

/**
 * A utility class to help with building Session objects.
 */
public class SessionBuilder {

    public static final String DEFAULT_GYM = "Getwell gym";
    public static final String DEFAULT_EXERCISE_TYPE = "Endurance";
    public static final LocalDateTime DEFAULT_START_TIME =
            LocalDateTime.of(2020, 9, 29, 13, 0);
    public static final int DEFAULT_DURATION = 120;

    private Gym gym;
    private ExerciseType exerciseType;
    private Interval interval;

    /**
     * Creates a {@code SessionBuilder} with the default details.
     */
    public SessionBuilder() {
        gym = new Gym(DEFAULT_GYM);
        exerciseType = new ExerciseType(DEFAULT_EXERCISE_TYPE);
        interval = new Interval(DEFAULT_START_TIME, DEFAULT_DURATION);
    }

    /**
     * Initializes the SessionBuilder with the data of {@code SessionToCopy}.
     */
    public SessionBuilder(Session sessionToCopy) {
        gym = sessionToCopy.getGym();
        exerciseType = sessionToCopy.getExerciseType();
        interval = sessionToCopy.getInterval();
    }

    /**
     * Sets the {@code Gym} of the {@code Session} that we are building.
     */
    public SessionBuilder withGym(String gym) {
        this.gym = new Gym(gym);
        return this;
    }

    /**
     * Sets the {@code ExerciseType} of the {@code Session} that we are building.
     */
    public SessionBuilder withExerciseType(String exerciseType) {
        this.exerciseType = new ExerciseType(exerciseType);
        return this;
    }

    /**
     * Sets the {@code Interval} of the {@code Session} that we are building.
     */
    public SessionBuilder withInterval(String start, String duration) {
        this.interval = new Interval(LocalDateTime.parse(start, Interval.DATE_TIME_FORMATTER),
                Integer.parseInt(duration));
        return this;
    }

    /**
     * Builds the {@code Session}.
     */
    public Session build() {
        return new Session(gym, exerciseType, interval);
    }

}
