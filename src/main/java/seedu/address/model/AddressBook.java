package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueList<Client> clients;
    private final UniqueList<Session> sessions;
    private final UniqueList<Schedule> schedules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueList<>();
        sessions = new UniqueList<>();
        schedules = new UniqueList<>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Clients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Client list with {@code Clients}.
     * {@code Clients} must not contain duplicate Clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setAll(clients);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setClients(newData.getClientList());
        setSessions(newData.getSessionList());
        setSchedules(newData.getScheduleList());
    }

    //// Client-level operations

    /**
     * Returns true if a Client with the same identity as {@code Client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds a Client to the address book.
     * The Client must not already exist in the address book.
     */
    public void addClient(Client p) {
        clients.add(p);
    }

    /**
     * Replaces the given Client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The Client identity of {@code editedClient} must not be the same as another existing Client in the address book.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);

        clients.set(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    //=============================== SESSION-RELATED ===========================================

    /**
     * Replaces the contents of the Session list with {@code Session}.
     * {@code Sessions} must not contain duplicate Sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setAll(sessions);
        this.sessions.sort();
    }

    //// Client-level operations

    /**
     * Returns true if a Session with the same identity as {@code Session} exists in the address book.
     */
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return sessions.contains(session);
    }

    /**
     * Adds a Session to the session list.
     * The Session must not already exist in the session list.
     */
    public void addSession(Session s) {
        sessions.add(s);
    }

    /**
     * Replaces the given Session {@code target} in the list with {@code editedSession}.
     * {@code target} must exist in the address book.
     * The Session identity of {@code editedSession} must not be the same as another existing Session.
     */
    public void setSession(Session target, Session editedSession) {
        requireNonNull(editedSession);

        sessions.set(target, editedSession);
    }

    /**
     * Removes {@code key} from this {@code SessionList}.
     * {@code key} must exist in the session list.
     */
    public void removeSession(Session key) {
        sessions.remove(key);
    }

    /**
     * Sorts {@code session} from this {@code SessionList} in ascending startDate order.
     */
    public void sortSession() {
        sessions.sort();
    }

    //=============================== SCHEDULE-RELATED ===========================================

    /**
     * Replaces the contents of the Schedule list with {@code schedules}.
     * {@code Schedule} must not contain duplicate Schedules.
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules.setAll(schedules);
    }

    /**
     * Returns true if a Schedule with the same identity as {@code Schedule} exists in the Schedule List.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return schedules.contains(schedule);
    }

    /**
     * Adds a Schedule to the schedule list.
     * The Schedule must not already exist in the Schedule List.
     */
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    /**
     * Replaces the given Schedule {@code target} in the list with {@code editedSchedule}.
     * {@code target} must exist in the address book.
     * The Schedule identity of {@code editedSchedule} must not be the same as another existing Schedule.
     */
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireNonNull(editedSchedule);

        schedules.set(target, editedSchedule);
    }

    /**
     * Removes {@code key} from this {@code ScheduleList}.
     * {@code key} must exist in the schedule list.
     */
    public void removeSchedule(Schedule key) {
        schedules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s",
                clients.asUnmodifiableObservableList().size() + " Clients",
                sessions.asUnmodifiableObservableList().size() + " Sessions",
                schedules.asUnmodifiableObservableList().size() + " Schedules");
        // TODO: refine later
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Schedule> getScheduleList() {
        return schedules.asUnmodifiableObservableList();
    }

    /**
     * Returns true if a Schedule with the same client as {@code client} exists in the Schedule List.
     */
    public boolean hasAnyScheduleAssociatedWithClient(Client client) {
        requireNonNull(client);
        return schedules.findAnyMatch(schedule -> schedule.getClient().isIdentical(client)).isPresent();
    }

    /**
     * Returns true if a Schedule with the same session as {@code session} exists in the Schedule List.
     */
    boolean hasAnyScheduleAssociatedWithSession(Session session) {
        requireNonNull(session);
        return schedules.findAnyMatch(schedule -> schedule.getSession().isIdentical(session)).isPresent();
    }

    /**
     * Returns true if a Schedule with the same client as {@code client} and session as
     * {@code session} exists in the Schedule List.
     */
    boolean hasAnyScheduleAssociatedWithClientAndSession(Client client, Session session) {
        requireAllNonNull(client, session);

        return schedules
                .findAnyMatch(schedule -> schedule.getClient().equals(client) && schedule.getSession().equals(session))
                .isPresent();
    }

    /**
     * Finds all {@code Schedule} that contains {@code session} from this {@code ScheduleList}.
     * {@code sessionKey} must exist in the schedule list.
     * @return A list of clients that are associated to {@code sessionKey} in the {@code ScheduleList}.
     */
    public List<Client> findClientBySession(Session sessionKey) {
        Stream<Schedule> schedulesContainingSession = schedules.findAllMatch(s-> s.getSession().equals(sessionKey));
        Stream<Client> clientsInSession = schedulesContainingSession.map(Schedule::getClient);
        return clientsInSession.collect(Collectors.toList());
    }

    /**
     * Finds all {@code Session} that contains {@code session} from this {@code ScheduleList}.
     * {@code clientKey} must exist in the schedule list.
     * @return A list of session that are associated to {@code clientKey} in the {@code ScheduleList}.
     */
    public List<Session> findSessionByClient(Client clientKey) {
        return findScheduleByClient(clientKey)
                .stream()
                .map(Schedule::getSession)
                .collect(Collectors.toList());
    }

    /**
     * Finds all {@code Schedule} that is associated to {@code clientKey} from this {@code ScheduleList}.
     * {@code clientKey} must exist in the schedule list.
     *
     * @return A list of schedules that are associated to {@code clientKey} in the {@code ScheduleList}.
     */
    public List<Schedule> findScheduleByClient(Client clientKey) {
        return schedules.findAllMatch(s -> s.getClient().equals(clientKey))
                .collect(Collectors.toList());
    }

    /**
     * Finds all {@code Schedule} that contains {@code session} from this {@code ScheduleList}.
     * {@code sessionKey} must exist in the schedule list.
     * @return A list of schedules that are associated to {@code sessionKey} in the {@code ScheduleList}.
     */
    public List<Schedule> findScheduleBySession(Session sessionKey) {
        Stream<Schedule> schedulesContainingSession = schedules.findAllMatch(s-> s.getSession().equals(sessionKey));
        return schedulesContainingSession.collect(Collectors.toList());
    }

    /**
     * Finds the {@code Schedule} that contains {@code client} and {@code session} from this {@code ScheduleList}.
     * {@code clientKey} and {@code sessionKey} must exist in the schedule list.
     * @return A list of schedules that are associated to {@code clientKey} and
     * {@code sessionKey} in the {@code ScheduleList}.
     */
    public Schedule findScheduleByClientAndSession(Client clientKey, Session sessionKey) {
        Optional<Schedule> schedulesContainingClientAndSession = schedules.findAnyMatch(s->
                s.getClient().equals(clientKey) && s.getSession().equals(sessionKey));
        return schedulesContainingClientAndSession.orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && clients.equals(((AddressBook) other).clients));
        // todo: add equality check for sessions and schedules
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
