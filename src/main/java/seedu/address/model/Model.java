package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;
import seedu.address.model.util.WeightUnit;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;
    Predicate<Session> PREDICATE_SHOW_ALL_SESSIONS = unused -> true;
    Predicate<Schedule> PREDICATE_SHOW_ALL_SCHEDULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the user prefs' weight unit
     */
    WeightUnit getPreferredWeightUnit();

    /**
     * Sets the user prefs' weight unit
     */
    void setPreferredWeightUnit(WeightUnit preferredWeightUnit);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a Client with the same identity as {@code Client} exists in the address book.
     */
    boolean hasClient(Client client);

    /**
     * Deletes every Schedule with the same client as {@code client}.
     */
    void deleteClientAssociatedSchedules(Client client);

    /**
     * Deletes the given Client.
     * The Client must exist in the address book.
     */
    void deleteClient(Client target);

    /**
     * Adds the given Client.
     * {@code client} must not already exist in the address book.
     */
    void addClient(Client client);

    /**
     * Replaces the given Client {@code target} with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The Client identity of {@code editedClient} must not be the same as another existing Client in the address book.
     */
    void setClient(Client target, Client editedClient);

    /** Returns an unmodifiable view of the filtered Client list */
    ObservableList<Client> getFilteredClientList();

    /**
     * Updates the filter of the filtered Client list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<Client> predicate);


    /**
     * Returns true if a Session with the same identity as {@code Session} exists in the Session List.
     */
    boolean hasSession(Session session);

    /**
     * Deletes every Schedule with the same session as {@code session}.
     */
    void deleteSessionAssociatedSchedules(Session session);

    /**
     * Deletes the given Session.
     * The Session must exist in the Session List.
     */
    void deleteSession(Session session);

    /**
     * Adds the given Session.
     * {@code client} must not already exist in the Session List.
     */
    void addSession(Session session);

    /**
     * Replaces the given Session {@code target} with {@code editedSession}.
     * {@code target} must exist in the Session List.
     * The Session identity of {@code editedSession} must not be the same as another existing Session
     *     in the address book.
     */
    void setSession(Session target, Session editedSession);

    /**
     * Sorts the Session List by session's natural order.
     */
    void sortSession();

    /** Returns an unmodifiable view of the filtered Session list */
    ObservableList<Session> getFilteredSessionList();

    /**
     * Updates the filter of the filtered Session list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSessionList(Predicate<Session> predicate);

    /**
     * Returns true if a Schedule with the same identity as {@code schedule} exists in the Schedule List.
     */
    boolean hasSchedule(Schedule schedule);

    /**
     * Returns true if a Schedule with the same session as {@code session} exists in the Schedule List.
     */
    boolean hasAnyScheduleAssociatedWithSession(Session session);

    /**
     * Edits every Schedule with the same session as {@code sessionToEdit} to be associated with {@code editedSession}
     * instead.
     */
    void editSchedulesAssociatedWithSession(Session sessionToEdit, Session editedSession);

    /**
     * Returns true if a Schedule with the same client as {@code client} exists in the Schedule List.
     */
    boolean hasAnyScheduleAssociatedWithClient(Client client);

    /**
     * Edits every Schedule with the same client as {@code toEdit} to be associated with {@code editedClient} instead.
     */
    void editSchedulesAssociatedWithClient(Client toEdit, Client editedClient);

    /**
     * Returns true if a Schedule with the same client as {@code client} and session as
     * {@code session} exists in the Schedule List.
     */
    boolean hasAnyScheduleAssociatedWithClientAndSession(Client client, Session session);

    /**
     * Deletes the given Schedule.
     * The Schedule must exist in the Schedule List.
     */
    void deleteSchedule(Schedule schedule);

    /**
     * Adds the given Schedule.
     * {@code client} must not already exist in the Schedule List.
     */
    void addSchedule(Schedule schedule);

    /**
     * Replaces the given Schedule {@code target} with {@code editedSchedule}.
     * {@code target} must exist in the Schedule List.
     * The Schedule identity of {@code editedSchedule} must not be the same as another existing Schedule
     *     in the address book.
     */
    void setSchedule(Schedule target, Schedule editedSchedule);

    /** Returns an unmodifiable view of the filtered Schedule list */
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Updates the filter of the filtered Schedule list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredScheduleList(Predicate<Schedule> predicate);

    /**
     * Finds all {@code Clients} in {@code Schedule List} that are associated to {@code sessionKey}.
     * @throws NullPointerException if {@code sessionKey} is null.
     */
    List<Client> findClientBySession(Session sessionKey);

    /**
     * Finds all {@code Sessions} in {@code Schedule List} that are associated to {@code clientKey}.
     * @throws NullPointerException if {@code clientKey} is null.
     */
    List<Session> findSessionByClient(Client clientKey);

    /**
     * Finds a {@code Schedule} in {@code Schedule List} that are associated to {@code clientKey}.
     * @throws NullPointerException if {@code clientKey} is null.
     */
    List<Schedule> findScheduleByClient(Client clientKey);

    /**
     * Finds a {@code Schedule} in {@code Schedule List} that are associated to {@code sessionKey}.
     * @throws NullPointerException if {@code sessionKey} is null.
     */
    List<Schedule> findScheduleBySession(Session sessionKey);

    /**
     * Finds a {@code Schedule} in {@code Schedule List} that are associated
     * to {@code clientKey} and {@code sessionKey}.
     * @throws NullPointerException if {@code clientKey} or {@code sessionKey} is null.
     */
    Schedule findScheduleByClientAndSession(Client clientKey, Session sessionKey);
}
