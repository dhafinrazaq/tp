package seedu.address.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.session.EditSessionCommand.MESSAGE_EDIT_SESSION_SUCCESS;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_ULTRAMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_ULTRAMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_ULTRAMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_ULTRAMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.showSessionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalSessions.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.session.Session;
import seedu.address.testutil.EditSessionDescriptorBuilder;
import seedu.address.testutil.SessionBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditSessionCommand.
 */
public class EditSessionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Session editedSession = new SessionBuilder().build();
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(editedSession).build();
        EditSessionCommand editSessionCommand = new EditSessionCommand(INDEX_FIRST_SESSION, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSession(model.getFilteredSessionList().get(0), editedSession);

        assertCommandSuccess(editSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedFilteredList_success() {
        Index indexLastSession = Index.fromOneBased(model.getFilteredSessionList().size());
        Session lastSession = model.getFilteredSessionList().get(indexLastSession.getZeroBased());

        SessionBuilder sessionInList = new SessionBuilder(lastSession);
        Session editedSession = sessionInList
                .withGym(VALID_GYM_ULTRAMAN)
                .withExerciseType(VALID_EXERCISE_TYPE_ULTRAMAN)
                .withInterval(VALID_START_TIME_ULTRAMAN, VALID_DURATION_ULTRAMAN).build();

        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder()
                .withGym(VALID_GYM_ULTRAMAN)
                .withExerciseType(VALID_EXERCISE_TYPE_ULTRAMAN)
                .withInterval(VALID_START_TIME_ULTRAMAN, VALID_DURATION_ULTRAMAN).build();
        EditSessionCommand editSessionCommand = new EditSessionCommand(indexLastSession, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSession(lastSession, editedSession);

        assertCommandSuccess(editSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditSessionCommand editSessionCommand = new EditSessionCommand(INDEX_FIRST_SESSION,
                new EditSessionDescriptor());
        Session editedSession = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Session sessionInFilteredList = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());
        Session editedSession = new SessionBuilder(sessionInFilteredList).withGym(VALID_GYM_MACHOMAN).build();
        EditSessionCommand editSessionCommand = new EditSessionCommand(INDEX_FIRST_SESSION,
                new EditSessionDescriptorBuilder().withGym(VALID_GYM_MACHOMAN).build());

        String expectedMessage = String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSession(model.getFilteredSessionList().get(0), editedSession);

        assertCommandSuccess(editSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSessionUnfilteredList_failure() {
        Session firstSession = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(firstSession).build();
        EditSessionCommand editSessionCommand = new EditSessionCommand(INDEX_SECOND_SESSION, descriptor);

        assertCommandFailure(editSessionCommand, model, EditSessionCommand.MESSAGE_DUPLICATE_SESSION);
    }

    @Test
    public void execute_duplicateSessionFilteredList_failure() {
        showSessionAtIndex(model, INDEX_FIRST_SESSION);

        // edit Session in filtered list into a duplicate in address book
        Session sessionInList = model.getAddressBook().getSessionList().get(INDEX_SECOND_SESSION.getZeroBased());
        EditSessionCommand editSessionCommand = new EditSessionCommand(INDEX_FIRST_SESSION,
                new EditSessionDescriptorBuilder(sessionInList).build());

        assertCommandFailure(editSessionCommand, model, EditSessionCommand.MESSAGE_DUPLICATE_SESSION);
    }

    /**
     * Updating GETWELL with {@code outOfBoundIndex} should fail due to index out of bounds of typicalAddressBook
     */
    @Test
    public void execute_invalidSessionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSessionList().size() + 1);
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder().withGym(VALID_GYM_GETWELL).build();
        EditSessionCommand editSessionCommand = new EditSessionCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editSessionCommand, model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of address book
     */
    @Test
    public void execute_invalidSessionIndexFilteredList_failure() {
        showSessionAtIndex(model, INDEX_FIRST_SESSION);
        Index outOfBoundIndex = INDEX_SECOND_SESSION;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getSessionList().size());

        EditSessionCommand editSessionCommand = new EditSessionCommand(outOfBoundIndex,
                new EditSessionDescriptorBuilder().withGym(VALID_GYM_GETWELL).build());

        assertCommandFailure(editSessionCommand, model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditSessionCommand standardCommand = new EditSessionCommand(INDEX_FIRST_SESSION, DESC_GETWELL);

        // same values -> returns true
        EditSessionDescriptor copyDescriptor = new EditSessionDescriptor(DESC_GETWELL);
        EditSessionCommand commandWithSameValues = new EditSessionCommand(INDEX_FIRST_SESSION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(INDEX_SECOND_SESSION, DESC_GETWELL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(INDEX_FIRST_SESSION, DESC_MACHOMAN)));
    }

}
