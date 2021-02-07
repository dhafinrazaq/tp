package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Supplier;

import javafx.scene.layout.Pane;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Settings should be shown to the user. */
    private final boolean showSettings;

    /** The application should exit. */
    private final boolean exit;

    /** A Scene to display on the Main GUI. (if required) */
    private Supplier<? extends Pane> pane;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showSettings, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showSettings = showSettings;
        this.exit = exit;
        this.pane = null;
    }

    /**
     * Constructs a {@code CommandResult} with a stored Runnable function to run during execution
     * @param feedbackToUser
     * @param pane a Pane to display on the MAIN GUI.
     */
    public CommandResult(String feedbackToUser, Supplier<? extends Pane> pane) {
        this(feedbackToUser);
        this.pane = pane;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowSettings() {
        return showSettings;
    }

    public boolean hasFunctionToRun() {
        return !(pane == null);
    }

    public Supplier<? extends Pane> getPane() {
        return this.pane;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && showSettings == otherCommandResult.showSettings
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showSettings, exit);
    }

}
