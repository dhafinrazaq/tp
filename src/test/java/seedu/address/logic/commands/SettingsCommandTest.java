package seedu.address.logic.commands;

import static seedu.address.logic.commands.SettingsCommand.SHOWING_SETTINGS_MESSAGE;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;


public class SettingsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_SETTINGS_MESSAGE, false, true, false);
        assertCommandSuccess(new SettingsCommand(), model, expectedCommandResult, expectedModel);
    }
}
