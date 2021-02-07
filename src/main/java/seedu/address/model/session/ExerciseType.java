package seedu.address.model.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Session's exercise type in FitEgo.
 * Guarantees: immutable; is valid as declared in {@link #isValidExerciseType(String)}.
 */
public class ExerciseType {
    public static final String MESSAGE_CONSTRAINTS = "Exercise types can take any values, and it should not be blank";

    /*
     * The first character of the exercise type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Constructs an {@code Exercise Type}.
     *
     * @param value A valid value.
     */
    public ExerciseType(String value) {
        requireNonNull(value);
        checkArgument(isValidExerciseType(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid exercise type.
     */
    public static boolean isValidExerciseType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExerciseType // instanceof handles nulls
                && value.equals(((ExerciseType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
