package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;
import seedu.address.model.util.WeightUnit;

/**
 * Represents the in-memory model of the FitEgo's data (client + session + schedule).
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final FilteredList<Session> filteredSessions;
    private final FilteredList<Schedule> filteredSchedules;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredClients = new FilteredList<>(this.addressBook.getClientList());
        filteredSessions = new FilteredList<>(this.addressBook.getSessionList());
        filteredSchedules = new FilteredList<>(this.addressBook.getScheduleList());

        sortSession();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public WeightUnit getPreferredWeightUnit() {
        return userPrefs.getPreferredWeightUnit();
    }

    @Override
    public void setPreferredWeightUnit(WeightUnit preferredWeightUnit) {
        requireNonNull(preferredWeightUnit);
        userPrefs.setPreferredWeightUnit(preferredWeightUnit);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return addressBook.hasClient(client);
    }

    @Override
    public void deleteClientAssociatedSchedules(Client client) {
        requireNonNull(client);

        List<Schedule> associatedSchedules = addressBook.findScheduleByClient(client);

        for (Schedule schedule : associatedSchedules) {
            this.deleteSchedule(schedule);
        }

        logger.info(String.format("User force delete Client %s\n causing %d schedules to be deleted",
                client.toString(),
                associatedSchedules.size()));
    }

    @Override
    public void deleteClient(Client target) {
        addressBook.removeClient(target);
    }

    @Override
    public void addClient(Client client) {
        addressBook.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        addressBook.setClient(target, editedClient);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    //=========== SessionList ================================================================================

    @Override
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return addressBook.hasSession(session);
    }

    @Override
    public void deleteSessionAssociatedSchedules(Session session) {
        requireNonNull(session);

        List<Schedule> associatedSchedules = addressBook.findScheduleBySession(session);

        for (Schedule schedule : associatedSchedules) {
            this.deleteSchedule(schedule);
        }

        logger.info(String.format("User force delete Session %s\n causing %d schedules to be deleted",
                session.toString(),
                associatedSchedules.size()));
    }

    @Override
    public void deleteSession(Session session) {
        requireNonNull(session);
        addressBook.removeSession(session);
    }

    @Override
    public void addSession(Session session) {
        addressBook.addSession(session);
        sortSession();
    }

    @Override
    public void setSession(Session target, Session editedSession) {
        requireAllNonNull(target, editedSession);
        addressBook.setSession(target, editedSession);
        sortSession();
    }

    @Override
    public void sortSession() {
        addressBook.sortSession();
    }

    //=========== Filtered Session List Accessors =============================================================

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return filteredSessions;
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredSessions.setPredicate(predicate);
        sortSession();
    }

    //=========== Schedule List ===============================================================================

    @Override
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return addressBook.hasSchedule(schedule);
    }

    /**
     * Returns true if a Schedule with the same client as {@code client} exists in the Schedule List.
     */
    public boolean hasAnyScheduleAssociatedWithClient(Client client) {
        requireNonNull(client);
        return addressBook.hasAnyScheduleAssociatedWithClient(client);
    }

    /**
     * Edits every Schedule with the same client as {@code toEdit} to be associated with {@code editedClient} instead.
     */
    public void editSchedulesAssociatedWithClient(Client clientToEdit, Client editedClient) {
        requireAllNonNull(clientToEdit, editedClient);

        List<Schedule> associatedSchedules = addressBook.findScheduleByClient(clientToEdit);

        for (Schedule schedule : associatedSchedules) {
            this.setSchedule(schedule, schedule.setClient(editedClient));
        }
    }

    /**
     * Returns true if a Schedule with the same session as {@code session} exists in the Schedule List.
     */
    public boolean hasAnyScheduleAssociatedWithSession(Session session) {
        requireNonNull(session);
        return addressBook.hasAnyScheduleAssociatedWithSession(session);
    }

    /**
     * Edits every Schedule with the same session as {@code sessionToEdit} to be associated with {@code editedSession}
     * instead.
     */
    public void editSchedulesAssociatedWithSession(Session sessionToEdit, Session editedSession) {
        requireAllNonNull(sessionToEdit, editedSession);

        List<Schedule> associatedSchedules = addressBook.findScheduleBySession(sessionToEdit);

        for (Schedule schedule : associatedSchedules) {
            this.setSchedule(schedule, schedule.setSession(editedSession));
        }
    }

    /**
     * Returns true if a Schedule with the same client as {@code client} and schedule as
     * {@code session} exists in the Schedule List.
     */
    public boolean hasAnyScheduleAssociatedWithClientAndSession(Client client, Session session) {
        requireAllNonNull(client, session);
        return addressBook.hasAnyScheduleAssociatedWithClientAndSession(client, session);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        addressBook.removeSchedule(schedule);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        addressBook.addSchedule(schedule);
        updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
    }

    @Override
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireAllNonNull(target, editedSchedule);
        addressBook.setSchedule(target, editedSchedule);
    }

    //=========== Filtered Schedule List Accessors =============================================================

    @Override
    public ObservableList<Schedule> getFilteredScheduleList() {
        return filteredSchedules;
    }

    @Override
    public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
        requireNonNull(predicate);
        filteredSchedules.setPredicate(predicate);
    }

    //=========== Client-Session Association =====================================================================
    @Override
    public List<Client> findClientBySession(Session sessionKey) {
        requireNonNull(sessionKey);
        return addressBook.findClientBySession(sessionKey);
    }

    @Override
    public List<Session> findSessionByClient(Client clientKey) {
        requireNonNull(clientKey);
        return addressBook.findSessionByClient(clientKey);
    }

    @Override
    public Schedule findScheduleByClientAndSession(Client clientKey, Session sessionKey) {
        requireAllNonNull(clientKey, sessionKey);
        return addressBook.findScheduleByClientAndSession(clientKey, sessionKey);
    }

    @Override
    public List<Schedule> findScheduleBySession(Session sessionKey) {
        requireAllNonNull(sessionKey);
        return addressBook.findScheduleBySession(sessionKey);
    }

    @Override
    public List<Schedule> findScheduleByClient(Client clientKey) {
        requireAllNonNull(clientKey);
        return addressBook.findScheduleByClient(clientKey);
    }



    //=========== Util-related ===============================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredClients.equals(other.filteredClients)
                && filteredSessions.equals(other.filteredSessions)
                && filteredSchedules.equals(other.filteredSchedules);
    }
}
