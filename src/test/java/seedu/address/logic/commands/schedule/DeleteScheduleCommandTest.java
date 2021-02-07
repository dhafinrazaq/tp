package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.schedule.DeleteScheduleCommand.MESSAGE_SCHEDULE_NOT_FOUND;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClients;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalSchedules.getTypicalSchedules;
import static seedu.address.testutil.TypicalSessions.getTypicalSessions;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

public class DeleteScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Returns an {@code AddressBook} with all the typical Clients and Sessions.
     */
    private static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getTypicalClients()) {
            ab.addClient(client);
        }
        for (Session session : getTypicalSessions()) {
            ab.addSession(session);
        }
        for (Schedule schedule : getTypicalSchedules()) {
            ab.addSchedule(schedule);
        }
        return ab;
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Schedule scheduleToDelete = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_CLIENT,
                INDEX_FIRST_SESSION);

        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_SUCCESS, scheduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSchedule(scheduleToDelete);

        assertCommandSuccess(deleteScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(outOfBoundIndex, INDEX_FIRST_SESSION);

        assertCommandFailure(deleteScheduleCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSessionIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSessionList().size() + 1);
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_CLIENT, outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommand, model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_scheduleNotFound_throwsCommandException() {
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FOURTH_CLIENT,
                INDEX_FIRST_SESSION);

        Client associatedClient = model.getFilteredClientList().get(INDEX_FOURTH_CLIENT.getZeroBased());
        Session associatedSession = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());


        assertCommandFailure(deleteScheduleCommand, model, String.format(MESSAGE_SCHEDULE_NOT_FOUND,
                associatedClient, associatedSession));
    }

    @Test
    public void equals() {
        DeleteScheduleCommand deleteFirstCommand = new DeleteScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION);
        DeleteScheduleCommand deleteSecondCommand = new DeleteScheduleCommand(INDEX_FIRST_CLIENT, INDEX_SECOND_SESSION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteScheduleCommand deleteFirstCommandCopy = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE,
                INDEX_FIRST_SESSION);
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
    private void showNoSchedule(Model model) {
        model.updateFilteredScheduleList(p -> false);

        assertTrue(model.getFilteredScheduleList().isEmpty());
    }
}
