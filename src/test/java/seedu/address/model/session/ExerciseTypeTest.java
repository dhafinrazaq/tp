package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExerciseTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExerciseType(null));
    }

    @Test
    public void constructor_invalidExerciseType_throwsIllegalArgumentException() {
        String invalidExerciseType = "";
        assertThrows(IllegalArgumentException.class, () -> new ExerciseType(invalidExerciseType));
    }

    @Test
    public void isValidExerciseType() {
        // null ExerciseType
        assertThrows(NullPointerException.class, () -> ExerciseType.isValidExerciseType(null));

        // invalid ExerciseTypes
        assertFalse(ExerciseType.isValidExerciseType("")); // empty string
        assertFalse(ExerciseType.isValidExerciseType(" ")); // spaces only

        // valid ExerciseTypes
        assertTrue(ExerciseType.isValidExerciseType("MyExerciseType"));
        assertTrue(ExerciseType.isValidExerciseType("-")); // one character
        assertTrue(ExerciseType.isValidExerciseType("My Super Duper Dupe ExerciseType")); // multispace ExerciseType
    }
}

