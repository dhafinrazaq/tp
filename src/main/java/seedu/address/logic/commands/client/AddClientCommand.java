package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.client.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.client.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.client.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.client.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.client.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Adds a Client to the address book.
 */
public class AddClientCommand extends Command {

    public static final String COMMAND_WORD = "cadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Client to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "injured-thigh "
            + PREFIX_TAG + "allergy-dairy ";

    public static final String MESSAGE_SUCCESS = "New Client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "A client with the same email already exists in FitEgo.";

    private final Client toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Client}
     */
    public AddClientCommand(Client client) {
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.addClient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddClientCommand // instanceof handles nulls
                && toAdd.equals(((AddClientCommand) other).toAdd));
    }
}
