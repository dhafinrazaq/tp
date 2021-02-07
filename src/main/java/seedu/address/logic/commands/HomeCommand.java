package seedu.address.logic.commands;

import java.util.function.Supplier;

import javafx.scene.layout.AnchorPane;
import seedu.address.model.Model;
import seedu.address.ui.Homepage;

/**
 * Displays the homepage.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays / Returns to homepage.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SUCCESS_MESSAGE = "Homepage displayed.";

    @Override
    public CommandResult execute(Model model) {

        Supplier<AnchorPane> run = () -> Homepage.getHomePage().getRoot();

        return new CommandResult(SHOWING_SUCCESS_MESSAGE, run);
    }
}
