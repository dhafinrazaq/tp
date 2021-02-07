package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final MainWindow mainWindow;
    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(MainWindow mainWindow, CommandExecutor commandExecutor) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.commandExecutor = commandExecutor;
        bindAutoComplete();
        //bindPastCommands(); Feature currently under maintenance.

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Binds Autocomplete to Command Text Field.
     * Autocomplete will not appear in the following scenario
     *    1. When TextField is blank
     *    2. When the list.size() = 1 & TextField is exactly list.get(0)
     * The reason for point 2 is that the AutoComplete will "consume" the enter, thus the instruction is not executed,
     *    or, it will take the focus off "help" window.
     * It is also recommended not to have command that are substring of another command.
     *    Eg. "help" and "helptask"
     */
    private void bindAutoComplete() {
        TextFields.bindAutoCompletion(commandTextField,
            t -> {
                if (t.getUserText().isBlank()) {
                    return null;
                }

                List<String> list = AddressBookParser.getCommandList()
                        .stream()
                        .filter(x -> x.startsWith(t.getUserText()))
                        .sorted()
                        .collect(Collectors.toList());

                if (list.size() == 1 && list.get(0).equalsIgnoreCase(t.getUserText())) {
                    return null;
                } else {
                    return list;
                }
            });
    }

    /*
     * Scroll Past Commands using Key.UP and Key.DOWN
     * This feature is currently under maintenance. Will be releasing it after checks are completed.

    private void bindPastCommands() {
        commandTextField.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
            if (k.getCode() == KeyCode.UP) {
                List<String> pastCommandList = mainWindow.getPastCommandList();
                int cursor = mainWindow.getPastCommandListCursor() - 1;

                if (cursor >= 0) { //Allow to scroll up if within range
                    commandTextField.setText(pastCommandList.get(cursor));
                    mainWindow.setPastCommandListCursor(cursor);
                }
            } else if (k.getCode() == KeyCode.DOWN) {
                List<String> pastCommandList = mainWindow.getPastCommandList();
                int cursor = mainWindow.getPastCommandListCursor() + 1;

                if (cursor < (pastCommandList).size()) { //Allow to scroll down if within range
                    commandTextField.setText(pastCommandList.get(cursor));
                    mainWindow.setPastCommandListCursor(cursor);
                } else if (cursor == (pastCommandList).size()) {
                    commandTextField.setText("");
                    mainWindow.setPastCommandListCursor(cursor);
                }
            }
        });
    }
     */

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
