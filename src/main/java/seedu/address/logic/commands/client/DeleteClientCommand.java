package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Supplier;

import javafx.scene.layout.AnchorPane;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.ui.Homepage;

/**
 * Deletes a Client identified using it's displayed index from the address book.
 */
public class DeleteClientCommand extends Command {

    public static final String COMMAND_WORD = "cdel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Client identified by the index number used in the displayed Client List.\n"
            + "Parameters: INDEX (must be a positive integer) [f/]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CLIENT_SUCCESS = "Deleted Client: %1$s";

    public static final String MESSAGE_FORCE_DELETE_CLIENT_USAGE = COMMAND_WORD
            + ": Cannot delete the Client identified by the index number because there are schedules tied to it.\n"
            + "To force delete, pass in f/ true as an option. BEWARE, YOU WILL LOSE ALL RELATED SCHEDULES.\n"
            + "Parameters: INDEX (must be a positive integer) [f/]\n"
            + "Example: " + COMMAND_WORD + " 1 f/";

    private final Index targetIndex;
    private final boolean isForced;

    /**
     * Creates a normal mode DeleteClient to delete the Client at {@code targetIndex}
     * @param targetIndex index of to-be deleted Client
     */
    public DeleteClientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.isForced = false;
    }

    /**
     * Creates a increased privilege mode DeleteClient to force delete the Client at {@code targetIndex}
     * if {@code isForced} is true
     * @param targetIndex index of to-be deleted Client
     * @param isForced true if the ClientSession have increased privilege
     */
    public DeleteClientCommand(Index targetIndex, boolean isForced) {
        this.targetIndex = targetIndex;
        this.isForced = isForced;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToDelete = lastShownList.get(targetIndex.getZeroBased());

        // Do not delete Client unless user use the force flag
        if (model.hasAnyScheduleAssociatedWithClient(clientToDelete) && !isForced) {
            return new CommandResult(MESSAGE_FORCE_DELETE_CLIENT_USAGE);
        }

        assert isForced || !model.hasAnyScheduleAssociatedWithClient(clientToDelete);

        model.deleteClientAssociatedSchedules(clientToDelete);
        model.deleteClient(clientToDelete);

        Supplier<AnchorPane> mainWindowSupplier = () -> Homepage.getHomePage().getRoot();

        return new CommandResult(String.format(MESSAGE_DELETE_CLIENT_SUCCESS, clientToDelete), mainWindowSupplier);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteClientCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteClientCommand) other).targetIndex)); // state check
    }
}
