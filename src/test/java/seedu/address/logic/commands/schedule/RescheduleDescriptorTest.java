package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.schedule.RescheduleTestUtil.DESC_SCHA;
import static seedu.address.logic.commands.schedule.RescheduleTestUtil.DESC_SCHB;
import static seedu.address.logic.commands.schedule.RescheduleTestUtil.VALID_CLIENT_INDEX_SCHB;
import static seedu.address.logic.commands.schedule.RescheduleTestUtil.VALID_SESSION_INDEX_SCHB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.RescheduleCommand.RescheduleDescriptor;
import seedu.address.testutil.RescheduleDescriptorBuilder;

public class RescheduleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        RescheduleDescriptor descriptorWithSameValues = new RescheduleDescriptor(DESC_SCHA);
        assertTrue(DESC_SCHA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SCHA.equals(DESC_SCHA));

        // null -> returns false
        assertFalse(DESC_SCHA.equals(null));

        // different types -> returns false
        assertFalse(DESC_SCHA.equals(5));

        // different values -> returns false
        assertFalse(DESC_SCHA.equals(DESC_SCHB));

        // different client index -> returns false
        RescheduleDescriptor editedAmy = new RescheduleDescriptorBuilder(DESC_SCHA)
                .withClientIndex(Index.fromOneBased(Integer.parseInt(VALID_CLIENT_INDEX_SCHB))).build();
        assertFalse(DESC_SCHA.equals(editedAmy));

        // different session index -> returns false
        editedAmy = new RescheduleDescriptorBuilder(DESC_SCHA)
                .withSessionIndex(Index.fromOneBased(Integer.parseInt(VALID_SESSION_INDEX_SCHB))).build();
        assertFalse(DESC_SCHA.equals(editedAmy));

    }
}
