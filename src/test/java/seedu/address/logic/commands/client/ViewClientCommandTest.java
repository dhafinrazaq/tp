package seedu.address.logic.commands.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;

public class ViewClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client clientToView = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        ViewClientCommand viewClientCommand = new ViewClientCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(ViewClientCommand.MESSAGE_VIEW_CLIENT_SUCCESS, clientToView);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewClientCommand, model, expectedMessage, expectedModel);
        assertEquals(clientToView, viewClientCommand.getClient());
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Client clientToView = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        ViewClientCommand viewClientCommand = new ViewClientCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(ViewClientCommand.MESSAGE_VIEW_CLIENT_SUCCESS, clientToView);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClientList(x -> x.isIdentical(clientToView));

        assertCommandSuccess(viewClientCommand, model, expectedMessage, expectedModel);
        assertEquals(clientToView, viewClientCommand.getClient());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        ViewClientCommand viewClientCommand = new ViewClientCommand(outOfBoundIndex);

        assertCommandFailure(viewClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Index outOfBoundIndex = INDEX_SECOND_CLIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        ViewClientCommand viewClientCommand = new ViewClientCommand(outOfBoundIndex);

        assertCommandFailure(viewClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewClientCommand viewFirstCommand = new ViewClientCommand(INDEX_FIRST_CLIENT);
        ViewClientCommand viewSecondCommand = new ViewClientCommand(INDEX_SECOND_CLIENT);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewClientCommand viewFirstCommandCopy = new ViewClientCommand(INDEX_FIRST_CLIENT);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different Client -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
