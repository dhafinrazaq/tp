package seedu.address.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_EXERCISE_TYPE;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_GYM;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_START_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

public class EditSessionCommand extends Command {

    public static final String COMMAND_WORD = "sedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the session identified "
            + "by the index number used in the displayed Session List.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GYM + "GYM] "
            + "[" + PREFIX_EXERCISE_TYPE + "EXERCISE_TYPE] "
            + "[" + PREFIX_START_TIME + "START_TIME "
            + PREFIX_DURATION + "DURATION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GYM + "Machoman "
            + PREFIX_START_TIME + "29/09/2020 1600 "
            + PREFIX_DURATION + "120 ";

    public static final String MESSAGE_EDIT_SESSION_SUCCESS = "Edited session: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists in FitEgo.";
    public static final String MESSAGE_OVERLAPPING_SESSION = "This session overlaps with another "
            + "session in the FitEgo.";

    private final Index index;
    private final EditSessionDescriptor editSessionDescriptor;

    /**
     * @param index of the Session in the filtered session list to edit
     * @param editSessionDescriptor details to edit the session with
     */
    public EditSessionCommand(Index index, EditSessionDescriptor editSessionDescriptor) {
        requireNonNull(index);
        requireNonNull(editSessionDescriptor);

        this.index = index;
        this.editSessionDescriptor = new EditSessionDescriptor(editSessionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Session> lastShownSessionList = model.getFilteredSessionList();
        List<Session> sessionList = model.getAddressBook().getSessionList();

        if (index.getZeroBased() >= lastShownSessionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToEdit = lastShownSessionList.get(index.getZeroBased());
        Session editedSession = createEditedSession(sessionToEdit, editSessionDescriptor);

        // Checks for duplicate sessions
        if (!sessionToEdit.isIdentical(editedSession) && model.hasSession(editedSession)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        // Checks for overlapping sessions
        for (Session session : sessionList) {
            if (!session.equals(sessionToEdit)) {
                if (Interval.isOverlap(session.getInterval(), editedSession.getInterval())) {
                    throw new CommandException(MESSAGE_OVERLAPPING_SESSION);
                }
            }
        }

        model.setSession(sessionToEdit, editedSession);

        if (model.hasAnyScheduleAssociatedWithSession(sessionToEdit)) {
            model.editSchedulesAssociatedWithSession(sessionToEdit, editedSession);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession));
    }

    /**
     * Creates and returns a {@code Session} with the details of {@code sessionToEdit}
     * edited with {@code editSessionDescriptor}.
     */
    private static Session createEditedSession(Session sessionToEdit, EditSessionDescriptor editSessionDescriptor) {
        assert sessionToEdit != null;

        Gym updatedGym = editSessionDescriptor.getGym().orElse(sessionToEdit.getGym());
        ExerciseType updatedExerciseType = editSessionDescriptor.getExerciseType()
                .orElse(sessionToEdit.getExerciseType());
        Interval updatedInterval = editSessionDescriptor.getInterval().orElse(sessionToEdit.getInterval());

        return new Session(updatedGym, updatedExerciseType, updatedInterval);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSessionCommand)) {
            return false;
        }

        // state check
        EditSessionCommand e = (EditSessionCommand) other;
        return index.equals(e.index)
                && editSessionDescriptor.equals(e.editSessionDescriptor);
    }

    /**
     * Stores the details to edit the Session with. Each non-empty field value will replace the
     * corresponding field value of the Session.
     */
    public static class EditSessionDescriptor {
        private Gym gym;
        private ExerciseType exerciseType;
        private Interval interval;

        public EditSessionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setGym(toCopy.gym);
            setExerciseType(toCopy.exerciseType);
            setInterval(toCopy.interval);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(gym, exerciseType, interval);
        }

        public void setGym(Gym gym) {
            this.gym = gym;
        }

        public Optional<Gym> getGym() {
            return Optional.ofNullable(gym);
        }

        public void setExerciseType(ExerciseType exerciseType) {
            this.exerciseType = exerciseType;
        }

        public Optional<ExerciseType> getExerciseType() {
            return Optional.ofNullable(exerciseType);
        }

        public void setInterval(Interval interval) {
            this.interval = interval;
        }

        public Optional<Interval> getInterval() {
            return Optional.ofNullable(interval);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSessionDescriptor)) {
                return false;
            }

            // state check
            EditSessionDescriptor e = (EditSessionDescriptor) other;

            return getGym().equals(e.getGym())
                    && getExerciseType().equals(e.getExerciseType())
                    && getInterval().equals(e.getInterval());
        }
    }
}
