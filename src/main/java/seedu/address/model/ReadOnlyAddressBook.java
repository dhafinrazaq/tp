package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the Clients list.
     * This list will not contain any duplicate Clients.
     */
    ObservableList<Client> getClientList();
    ObservableList<Session> getSessionList();
    ObservableList<Schedule> getScheduleList();
}
