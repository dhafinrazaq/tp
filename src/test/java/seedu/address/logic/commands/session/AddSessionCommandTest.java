package seedu.address.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;
import seedu.address.model.util.WeightUnit;
import seedu.address.testutil.SessionBuilder;

public class AddSessionCommandTest {

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null));
    }

    @Test
    public void execute_sessionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();

        CommandResult commandResult = new AddSessionCommand(validSession).execute(modelStub);

        assertEquals(String.format(AddSessionCommand.MESSAGE_SUCCESS, validSession), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSession), modelStub.sessionsAdded);
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() {
        Session validSession = new SessionBuilder().build();
        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession);
        ModelStub modelStub = new ModelStubWithSession(validSession);

        assertThrows(CommandException.class,
                AddSessionCommand.MESSAGE_DUPLICATE_SESSION, () -> addSessionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Session getwell = new SessionBuilder().withGym("Getwell gym").build();
        Session machoman = new SessionBuilder().withGym("Machoman gym").build();
        AddSessionCommand addGetwellCommand = new AddSessionCommand(getwell);
        AddSessionCommand addMachomanCommand = new AddSessionCommand(machoman);

        // same object -> returns true
        assertTrue(addGetwellCommand.equals(addGetwellCommand));

        // same values -> returns true
        AddSessionCommand addGetwellCommandCopy = new AddSessionCommand(getwell);
        assertTrue(addGetwellCommand.equals(addGetwellCommandCopy));

        // different types -> returns false
        assertFalse(addGetwellCommand.equals(1));

        // null -> returns false
        assertFalse(addGetwellCommand.equals(null));

        // different Session -> returns false
        assertFalse(addGetwellCommand.equals(addMachomanCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WeightUnit getPreferredWeightUnit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPreferredWeightUnit(WeightUnit preferredWeightUnit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClientAssociatedSchedules(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSessionAssociatedSchedules(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSession(Session target, Session editedSession) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Session> getFilteredSessionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSessionList(Predicate<Session> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnyScheduleAssociatedWithSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editSchedulesAssociatedWithSession(Session sessionToEdit, Session editedSession) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnyScheduleAssociatedWithClient(Client toEdit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editSchedulesAssociatedWithClient(Client toEdit, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnyScheduleAssociatedWithClientAndSession(Client client, Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSchedule(Schedule target, Schedule editedSchedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Schedule> getFilteredScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Session> findSessionByClient(Client clientKey) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Schedule> findScheduleByClient(Client clientKey) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Schedule> findScheduleBySession(Session sessionKey) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Schedule findScheduleByClientAndSession(Client clientKey, Session sessionKey) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Client> findClientBySession(Session sessionKey) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Session.
     */
    private class ModelStubWithSession extends ModelStub {
        private final Session session;

        ModelStubWithSession(Session session) {
            requireNonNull(session);
            this.session = session;
        }

        @Override
        public boolean hasSession(Session session) {
            requireNonNull(session);
            return this.session.isIdentical(session);
        }
    }

    /**
     * A Model stub that always accept the Session being added.
     */
    private class ModelStubAcceptingSessionAdded extends ModelStub {
        final ArrayList<Session> sessionsAdded = new ArrayList<>();

        @Override
        public boolean hasSession(Session session) {
            requireNonNull(session);
            return sessionsAdded.stream().anyMatch(session::isIdentical);
        }

        @Override
        public void addSession(Session session) {
            requireNonNull(session);
            sessionsAdded.add(session);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
