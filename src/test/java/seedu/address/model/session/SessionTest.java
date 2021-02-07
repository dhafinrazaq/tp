package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_MACHOMAN;
import static seedu.address.testutil.TypicalSessions.GETWELL;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SessionBuilder;

public class SessionTest {

    @Test
    public void isIdenticalSession() {
        // same object -> returns true
        assertTrue(GETWELL.isIdentical(GETWELL));

        // null -> returns false
        assertFalse(GETWELL.isIdentical(null));

        // different exerciseType -> returns true
        Session editedGetwell = new SessionBuilder(GETWELL).withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN).build();
        assertTrue(GETWELL.isIdentical(editedGetwell));

        // different gym -> returns true
        editedGetwell = new SessionBuilder(GETWELL).withGym(VALID_GYM_MACHOMAN).build();
        assertTrue(GETWELL.isIdentical(editedGetwell));

        // non-overlapping interval -> returns false
        editedGetwell = new SessionBuilder(GETWELL)
                .withInterval(VALID_START_TIME_MACHOMAN, VALID_DURATION_MACHOMAN).build();
        assertFalse(GETWELL.isIdentical(editedGetwell));

        //overlapping intervals
        editedGetwell = new SessionBuilder(GETWELL)
                .withInterval(VALID_START_TIME_GETWELL, VALID_DURATION_MACHOMAN).build();
        assertTrue(GETWELL.isIdentical(editedGetwell));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Session aliceCopy = new SessionBuilder(GETWELL).build();
        assertTrue(GETWELL.equals(aliceCopy));

        // same object -> returns true
        assertTrue(GETWELL.equals(GETWELL));

        // null -> returns false
        assertFalse(GETWELL.equals(null));

        // different type -> returns false
        assertFalse(GETWELL.equals(5));

        // different Session -> returns false
        assertFalse(GETWELL.equals(MACHOMAN));

        // different gym -> returns false
        Session editedGetwell = new SessionBuilder(GETWELL).withGym(VALID_GYM_MACHOMAN).build();
        assertFalse(GETWELL.equals(editedGetwell));

        // different exerciseType -> returns false
        editedGetwell = new SessionBuilder(GETWELL).withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN).build();
        assertFalse(GETWELL.equals(editedGetwell));

        // different interval -> returns false
        editedGetwell = new SessionBuilder(GETWELL)
                .withInterval(VALID_START_TIME_MACHOMAN, VALID_DURATION_MACHOMAN).build();
        assertFalse(GETWELL.equals(editedGetwell));
    }
}
