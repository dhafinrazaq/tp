package seedu.address.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_MACHOMAN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import seedu.address.testutil.EditSessionDescriptorBuilder;

public class EditSessionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditSessionDescriptor descriptorWithSameValues = new EditSessionDescriptor(DESC_GETWELL);
        assertTrue(DESC_GETWELL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GETWELL.equals(DESC_GETWELL));

        // null -> returns false
        assertFalse(DESC_GETWELL.equals(null));

        // different types -> returns false
        assertFalse(DESC_GETWELL.equals(5));

        // different values -> returns false
        assertFalse(DESC_GETWELL.equals(DESC_MACHOMAN));

        // different gym -> returns false
        EditSessionDescriptor editedGetwell = new EditSessionDescriptorBuilder(DESC_GETWELL)
                .withGym(VALID_GYM_MACHOMAN).build();
        assertFalse(DESC_GETWELL.equals(editedGetwell));

        // different exercise type -> returns false
        editedGetwell = new EditSessionDescriptorBuilder(DESC_GETWELL)
                .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN).build();
        assertFalse(DESC_GETWELL.equals(editedGetwell));

        // different interval -> returns false
        editedGetwell = new EditSessionDescriptorBuilder(DESC_GETWELL)
                .withInterval(VALID_START_TIME_MACHOMAN,
                        VALID_DURATION_MACHOMAN).build();
        assertFalse(DESC_GETWELL.equals(editedGetwell));
    }
}
