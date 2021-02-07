package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withClient("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Client} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withClient(Client client) {
        addressBook.addClient(client);
        return this;
    }

    /**
     * Adds a new {@code Session} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSession(Session session) {
        addressBook.addSession(session);
        return this;
    }

    /**
     * Adds a new {@code Schedule} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSchedule(Schedule schedule) {
        addressBook.addSchedule(schedule);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
