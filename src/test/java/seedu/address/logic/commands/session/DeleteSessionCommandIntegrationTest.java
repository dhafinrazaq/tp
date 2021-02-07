package seedu.address.logic.commands.session;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SESSION;
import static seedu.address.testutil.TypicalSessions.getIntegrationAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.session.Session;

/**
 * Covers integration test between DeleteSessionCommand and Associated Schedules.
 * Since index has been unit tested in DeleteSessionCommandTest, we will focus on the interaction between the 2 classes.
 */
public class DeleteSessionCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getIntegrationAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_unfilteredListWithoutForce_success() {
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);

        String expectedMessage = DeleteSessionCommand.MESSAGE_FORCE_DELETE_SESSION_USAGE;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        SessionCommandTestUtil.assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_unfilteredListWithForce_success() {
        Session sessionToDelete = model.getFilteredSessionList().get(INDEX_THIRD_SESSION.getZeroBased());
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(INDEX_THIRD_SESSION, true);

        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSession(sessionToDelete);

        SessionCommandTestUtil.assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }

}
