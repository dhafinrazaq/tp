package seedu.address.logic.commands;

import static seedu.address.logic.commands.HomeCommand.SHOWING_SUCCESS_MESSAGE;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HomeCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_SUCCESS_MESSAGE, false, false, false);
        assertCommandSuccess(new HomeCommand(), model, expectedCommandResult, expectedModel);
    }
}
