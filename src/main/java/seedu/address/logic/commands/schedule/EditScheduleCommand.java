package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_UPDATED_SESSION_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

/**
 * Edits the details of an existing Client in the address book.
 */
public class EditScheduleCommand extends Command {

    public static final String COMMAND_WORD = "editschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedules a client with another session. "
            + "Parameters: "
            + PREFIX_CLIENT_INDEX + "CLIENT "
            + PREFIX_SESSION_INDEX + "SESSION "
            + PREFIX_UPDATED_SESSION_INDEX + "UPDATED SESSION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_INDEX + "1 "
            + PREFIX_SESSION_INDEX + "1 "
            + PREFIX_UPDATED_SESSION_INDEX + "1 ";

    public static final String MESSAGE_EDIT_SCHEDULE_SUCCESS = "Editschedule : \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This Schedule overlaps with an existing Schedule";

    private final Index sessionIndex;
    private final Index clientIndex;
    private final Index updatedSessionIndex;

    private final EditScheduleDescriptor editScheduleDescriptor;

    /**
     * @param clientIndex of the Client in the filtered client list to edit
     * @param sessionIndex of the Session in the filtered session list to edit
     * @param editScheduleDescriptor details to edit the schedule with
     */
    public EditScheduleCommand(Index clientIndex, Index sessionIndex, Index updatedSessionIndex,
                               EditScheduleDescriptor editScheduleDescriptor) {
        requireNonNull(clientIndex);
        requireNonNull(sessionIndex);
        requireNonNull(editScheduleDescriptor);

        this.clientIndex = clientIndex;
        this.sessionIndex = sessionIndex;
        this.updatedSessionIndex = updatedSessionIndex;
        this.editScheduleDescriptor = new EditScheduleDescriptor(editScheduleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Session> lastShownSessionList = model.getFilteredSessionList();
        List<Client> lastShownClientList = model.getFilteredClientList();

        if (clientIndex.getZeroBased() >= lastShownClientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        if (sessionIndex.getZeroBased() >= lastShownSessionList.size()
                && updatedSessionIndex.getZeroBased() >= lastShownSessionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Client client = lastShownClientList.get(clientIndex.getZeroBased());
        Session session = lastShownSessionList.get(sessionIndex.getZeroBased());
        Schedule scheduleToEdit = new Schedule(client, session);

        Schedule editedSchedule = createEditedSchedule(scheduleToEdit, editScheduleDescriptor,
                lastShownSessionList);

        if (!scheduleToEdit.isUnique(editedSchedule) && model.hasSchedule(editedSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        if (scheduleToEdit.getSession().equals(editedSchedule.getSession())) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        //target, edited
        model.setSchedule(scheduleToEdit, editedSchedule);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        return new CommandResult(String.format(MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule));
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code scheduleToEdit}
     * edited with {@code editScheduleDescriptor}.
     */
    private static Schedule createEditedSchedule(Schedule scheduleToEdit, EditScheduleDescriptor editScheduleDescriptor,
                                                 List<Session> lastShownSessionList) throws CommandException {
        assert scheduleToEdit != null;

        Client client = scheduleToEdit.getClient();
        Session session;
        try {
            session = editScheduleDescriptor.getSessionIndex() == null
                    ? scheduleToEdit.getSession()
                    : lastShownSessionList.get(editScheduleDescriptor.getSessionIndex().get().getZeroBased());
        } catch (NoSuchElementException e) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
        return new Schedule(client, session);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditScheduleCommand)) {
            return false;
        }

        // state check
        EditScheduleCommand e = (EditScheduleCommand) other;
        return clientIndex.equals(e.clientIndex)
                && sessionIndex.equals(e.sessionIndex)
                && editScheduleDescriptor.equals(e.editScheduleDescriptor);
    }

    /**
     * Stores the details to edit the Schedule with. Each non-empty field value will replace the
     * corresponding field value of the Schedule.
     */
    public static class EditScheduleDescriptor {
        private Index clientIndex;
        private Index updateSessionIndex;

        public EditScheduleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditScheduleDescriptor(EditScheduleDescriptor toCopy) {
            setClientIndex(toCopy.clientIndex);
            setSessionIndex(toCopy.updateSessionIndex);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(clientIndex, updateSessionIndex);
        }

        public void setClientIndex(Index clientIndex) {
            this.clientIndex = clientIndex;
        }

        public Optional<Index> getClientIndex() {
            return Optional.ofNullable(clientIndex);
        }

        public void setSessionIndex(Index updateSessionIndex) {
            this.updateSessionIndex = updateSessionIndex;
        }

        public Optional<Index> getSessionIndex() {
            return Optional.ofNullable(updateSessionIndex);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditScheduleDescriptor)) {
                return false;
            }

            // state check
            EditScheduleDescriptor e = (EditScheduleDescriptor) other;

            return getClientIndex().equals(e.getClientIndex())
                    && getSessionIndex().equals(e.getSessionIndex());
        }
    }
}

