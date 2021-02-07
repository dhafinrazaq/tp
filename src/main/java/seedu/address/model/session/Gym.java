package seedu.address.model.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Session's gym in FitEgo.
 * Guarantees: immutable; is valid as declared in {@link #isValidGym(String)}.
 */
public class Gym {
    public static final String MESSAGE_CONSTRAINTS = "Gyms can take any values, and it should not be blank";

    /*
     * The first character of the gym must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String VALIDATION_REGEX = "[^\\s].*";

    private final String address;

    /**
     * Constructs an {@code Gym}.
     *
     * @param address A valid address.
     */
    public Gym(String address) {
        requireNonNull(address);
        checkArgument(isValidGym(address), MESSAGE_CONSTRAINTS);
        this.address = address;
    }

    /**
     * Returns true if a given string is a valid gym.
     */
    public static boolean isValidGym(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gym // instanceof handles nulls
                && address.equals(((Gym) other).address)); // state check
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
