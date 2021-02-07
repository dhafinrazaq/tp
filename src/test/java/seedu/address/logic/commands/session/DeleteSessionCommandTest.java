package seedu.address.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.showSessionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalSessions.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.session.Session;

/**
 * Contains integration tests (interaction with the Model and Schedule) and unit tests for
 * {@code DeleteSessionCommand}.
 */
public class DeleteSessionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Session sessionToDelete = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);

        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSession(sessionToDelete);

        assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSessionList().size() + 1);
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(outOfBoundIndex);

        assertCommandFailure(deleteSessionCommand, model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSessionAtIndex(model, INDEX_FIRST_SESSION);

        Session sessionToDelete = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);

        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSession(sessionToDelete);
        showNoSession(expectedModel);

        assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSessionAtIndex(model, INDEX_FIRST_SESSION);

        Index outOfBoundIndex = INDEX_SECOND_SESSION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getSessionList().size());

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(outOfBoundIndex);

        assertCommandFailure(deleteSessionCommand, model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteSessionCommand deleteFirstCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);
        DeleteSessionCommand deleteSecondCommand = new DeleteSessionCommand(INDEX_SECOND_SESSION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteSessionCommand deleteFirstCommandCopy = new DeleteSessionCommand(INDEX_FIRST_SESSION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Session -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSession(Model model) {
        model.updateFilteredSessionList(p -> false);

        assertTrue(model.getFilteredSessionList().isEmpty());
    }
}

