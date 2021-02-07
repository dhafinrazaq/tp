package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_SESSION_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

/**
 * Adds a Schedule to the address book.
 */
public class AddScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule a client with a session. "
            + "Parameters: "
            + PREFIX_CLIENT_INDEX + "CLIENT_INDEX (must be a positive integer) "
            + PREFIX_SESSION_INDEX + "SESSION_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_INDEX + "1 "
            + PREFIX_SESSION_INDEX + "1 ";

    public static final String MESSAGE_SUCCESS = "New schedule added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "The schedule already exists in FitEgo";

    private final Index clientIndex;
    private final Index sessionIndex;

    /**
     * Creates an AddScheduleCommand to add the specified {@code Schedule} based on the
     * {@code clientIndex} and {@code sessionIndex}.
     */
    public AddScheduleCommand(Index clientIndex, Index sessionIndex) {
        requireAllNonNull(clientIndex, sessionIndex);

        this.clientIndex = clientIndex;
        this.sessionIndex = sessionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get the Client and Session from the filtered list
        List<Client> lastShownClientList = model.getFilteredClientList();
        if (clientIndex.getZeroBased() >= lastShownClientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }
        Client clientToSchedule = lastShownClientList.get(clientIndex.getZeroBased());

        List<Session> lastShownSessionList = model.getFilteredSessionList();
        if (sessionIndex.getZeroBased() >= lastShownSessionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }
        Session sessionToSchedule = lastShownSessionList.get(sessionIndex.getZeroBased());

        // Add Schedule associated with the Client and Session if currently no identical Schedule exist
        if (model.hasAnyScheduleAssociatedWithClientAndSession(clientToSchedule, sessionToSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        Schedule scheduleToAdd = new Schedule(clientToSchedule, sessionToSchedule);
        model.addSchedule(scheduleToAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, scheduleToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && clientIndex.equals(((AddScheduleCommand) other).clientIndex)
                && sessionIndex.equals(((AddScheduleCommand) other).sessionIndex));
    }
}
