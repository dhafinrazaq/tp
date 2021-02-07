package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_UPDATED_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_WEIGHT;
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
import seedu.address.model.schedule.PaymentStatus;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Weight;
import seedu.address.model.session.Session;

/**
 * Edits the details of an existing Client in the address book.
 */
public class EditScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": schedules a client with another session. \n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_CLIENT_INDEX + "CLIENT_INDEX (must be a positive integer) "
            + PREFIX_SESSION_INDEX + "SESSION_INDEX (must be a positive integer) "
            + "[" + PREFIX_UPDATED_SESSION_INDEX + "UPDATED_SESSION_INDEX] "
            + "[" + PREFIX_PAYMENT_STATUS + "PAYMENT_STATUS (paid or unpaid)] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_WEIGHT + "WEIGHT[kg/lb]]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_INDEX + "1 "
            + PREFIX_SESSION_INDEX + "1 "
            + PREFIX_UPDATED_SESSION_INDEX + "1 "
            + PREFIX_PAYMENT_STATUS + "paid "
            + PREFIX_REMARK + "Did 5 pushups";

    public static final String MESSAGE_EDIT_SCHEDULE_SUCCESS = "Schedule Edited: \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This Schedule already exists in FitEgo";
    public static final String MESSAGE_SCHEDULE_NOT_FOUND = "No schedule is associated with this client and session";

    private final Index sessionIndex;
    private final Index clientIndex;

    private final EditScheduleDescriptor editScheduleDescriptor;

    /**
     * @param clientIndex of the Client in the filtered client list to edit
     * @param sessionIndex of the Session in the filtered session list to edit
     * @param editScheduleDescriptor details to edit the schedule with
     */
    public EditScheduleCommand(Index clientIndex, Index sessionIndex, EditScheduleDescriptor editScheduleDescriptor) {
        requireNonNull(clientIndex);
        requireNonNull(sessionIndex);
        requireNonNull(editScheduleDescriptor);

        this.clientIndex = clientIndex;
        this.sessionIndex = sessionIndex;
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

        if (sessionIndex.getZeroBased() >= lastShownSessionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        // Get the Schedule associated with the Client and Session
        Client client = lastShownClientList.get(clientIndex.getZeroBased());
        Session session = lastShownSessionList.get(sessionIndex.getZeroBased());
        Schedule scheduleToEdit;

        if (model.hasAnyScheduleAssociatedWithClientAndSession(client, session)) {
            scheduleToEdit = model.findScheduleByClientAndSession(client, session);
        } else {
            throw new CommandException(MESSAGE_SCHEDULE_NOT_FOUND);
        }

        Schedule editedSchedule = createEditedSchedule(scheduleToEdit, editScheduleDescriptor,
                lastShownSessionList);

        if (!scheduleToEdit.isIdentical(editedSchedule) && model.hasSchedule(editedSchedule)) {
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
        assert lastShownSessionList != null;

        Client client = scheduleToEdit.getClient();
        Session session = scheduleToEdit.getSession();
        Optional<Index> updatedOptionalSessionIndex = editScheduleDescriptor.getUpdatedSessionIndex();

        try {
            if (updatedOptionalSessionIndex.isPresent()) {
                int updatedSessionIndex = updatedOptionalSessionIndex.get().getZeroBased();

                if (updatedSessionIndex >= lastShownSessionList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
                }
                session = lastShownSessionList.get(updatedSessionIndex);
            }
        } catch (NoSuchElementException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        // get new PaymentStatus else return previous PaymentStatus
        PaymentStatus updatedPayment = editScheduleDescriptor
                .getPaymentStatus().orElse(scheduleToEdit.getPaymentStatus());

        // get new remark else return previous remark
        Remark remark = editScheduleDescriptor.getRemark().orElse(scheduleToEdit.getRemark());

        // get new weight else return previous weight
        Weight weight = editScheduleDescriptor.getWeight().orElse(scheduleToEdit.getWeight());

        return new Schedule(client, session, updatedPayment, remark, weight);
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
        private Index sessionIndex;
        private Index updateSessionIndex;
        private PaymentStatus paymentStatus;
        private Remark remark;
        private Weight weight;

        public EditScheduleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditScheduleDescriptor(EditScheduleDescriptor toCopy) {
            setClientIndex(toCopy.clientIndex);
            setSessionIndex(toCopy.sessionIndex);
            setUpdatedSessionIndex(toCopy.updateSessionIndex);
            setUpdatedPayment(toCopy.paymentStatus);
            setUpdatedRemark(toCopy.remark);
            setUpdatedWeight(toCopy.weight);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(updateSessionIndex, paymentStatus, remark, weight);
        }

        public void setClientIndex(Index clientIndex) {
            this.clientIndex = clientIndex;
        }

        public Optional<Index> getClientIndex() {
            return Optional.ofNullable(clientIndex);
        }

        public void setSessionIndex(Index sessionIndex) {
            this.sessionIndex = sessionIndex;
        }

        public Optional<Index> getSessionIndex() {
            return Optional.ofNullable(sessionIndex);
        }

        public void setUpdatedSessionIndex(Index updateSessionIndex) {
            this.updateSessionIndex = updateSessionIndex;
        }

        public Optional<Index> getUpdatedSessionIndex() {
            return Optional.ofNullable(updateSessionIndex);
        }

        public Optional<PaymentStatus> getPaymentStatus() {
            return Optional.ofNullable(paymentStatus);
        }

        public void setUpdatedPayment(PaymentStatus updatedPayment) {
            this.paymentStatus = updatedPayment;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setUpdatedRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(weight);
        }

        public void setUpdatedWeight(Weight weight) {
            this.weight = weight;
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
                    && getSessionIndex().equals(e.getSessionIndex())
                    && getUpdatedSessionIndex().equals(e.getUpdatedSessionIndex())
                    && getPaymentStatus().equals(e.getPaymentStatus())
                    && getRemark().equals(e.getRemark());
        }
    }
}

