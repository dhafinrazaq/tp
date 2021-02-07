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
 * Deletes a Schedule identified using associated with the specified Client and Session from the address book.
 */
public class DeleteScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schdel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a schedule associated with the specified"
            + "client and session. "
            + "Parameters: "
            + PREFIX_CLIENT_INDEX + "CLIENT_INDEX (must be a positive integer)"
            + PREFIX_SESSION_INDEX + "SESSION_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_INDEX + "1 "
            + PREFIX_SESSION_INDEX + "1 ";

    public static final String MESSAGE_SUCCESS = "Schedule deleted: \n%1$s";
    public static final String MESSAGE_SCHEDULE_NOT_FOUND = "No schedule associated with client: \n%1$s "
            + "and session:\n%2$s";

    private final Index clientIndex;
    private final Index sessionIndex;

    /**
     * Creates an DeleteScheduleCommand to delete the specified {@code Schedule}.
     */
    public DeleteScheduleCommand(Index clientIndex, Index sessionIndex) {
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
        Client client = lastShownClientList.get(clientIndex.getZeroBased());

        List<Session> lastShownSessionList = model.getFilteredSessionList();
        if (sessionIndex.getZeroBased() >= lastShownSessionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }
        Session session = lastShownSessionList.get(sessionIndex.getZeroBased());

        // Delete the Schedule associated with the Client and Session if found in address book
        if (!model.hasAnyScheduleAssociatedWithClientAndSession(client, session)) {
            throw new CommandException(String.format(MESSAGE_SCHEDULE_NOT_FOUND, client, session));
        }

        Schedule scheduleToDelete = model.findScheduleByClientAndSession(client, session);
        model.deleteSchedule(scheduleToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, scheduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && clientIndex.equals(((DeleteScheduleCommand) other).clientIndex)
                && sessionIndex.equals(((DeleteScheduleCommand) other).sessionIndex));
    }
}
