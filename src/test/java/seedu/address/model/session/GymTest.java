package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GymTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gym(null));
    }

    @Test
    public void constructor_invalidGym_throwsIllegalArgumentException() {
        String invalidGym = "";
        assertThrows(IllegalArgumentException.class, () -> new Gym(invalidGym));
    }

    @Test
    public void isValidGym() {
        // null Gym
        assertThrows(NullPointerException.class, () -> Gym.isValidGym(null));

        // invalid Gyms
        assertFalse(Gym.isValidGym("")); // empty string
        assertFalse(Gym.isValidGym(" ")); // spaces only

        // valid Gyms
        assertTrue(Gym.isValidGym("MyGym"));
        assertTrue(Gym.isValidGym("-")); // one character
        assertTrue(Gym.isValidGym("My Super Duper Dupe Gym")); // multispace gym
    }
}
