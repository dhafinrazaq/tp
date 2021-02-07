package seedu.address.logic;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;
import seedu.address.model.util.WeightUnit;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of Clients */
    ObservableList<Client> getFilteredClientList();

    /** Returns an unmodifiable view of the filtered list of Sessions */
    ObservableList<Session> getFilteredSessionList();

    /** Updates the predicate on the filtered list of Session */
    void updateFilteredSessionList(Predicate<Session> predicate);

    /** Returns the list of Clients associated to a Session*/
    List<Client> getAssociatedClientList(Session session);

    /** Returns the list of Sessions associated to a Client*/
    List<Session> getAssociatedSessionList(Client client);

    /** Returns the list of Schedules associated to a Client*/
    List<Schedule> getAssociatedScheduleList(Client client);

    /** Returns the list of Schedules associated to a Session*/
    List<Schedule> getAssociatedScheduleList(Session session);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' preferred weight unit.
     */
    WeightUnit getPreferredWeightUnit();

    /**
     * Set the user prefs' preferred weight unit.
     */
    void setPreferredWeightUnit(WeightUnit weightUnit);
}
