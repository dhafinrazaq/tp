package seedu.address.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_EXERCISE_TYPE;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_GYM;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.session.Interval.DATE_TIME_FORMATTER;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.session.IntervalContainsDatetimePredicate;
import seedu.address.model.session.Session;
import seedu.address.testutil.EditSessionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class SessionCommandTestUtil {

    public static final String VALID_GYM_GETWELL = "Getwell gym";
    public static final String VALID_GYM_MACHOMAN = "Machoman gym";
    public static final String VALID_GYM_ULTRAMAN = "Ultraman gym";

    public static final String VALID_EXERCISE_TYPE_GETWELL = "Endurance";
    public static final String VALID_EXERCISE_TYPE_MACHOMAN = "Bodybuilder";
    public static final String VALID_EXERCISE_TYPE_ULTRAMAN = "Pilates";

    public static final String VALID_START_TIME_GETWELL = "29/09/2020 1300";
    public static final String VALID_START_TIME_MACHOMAN = "29/09/2020 1600";
    public static final String VALID_START_TIME_MACHOMAN_NOW = LocalDateTime.now().format(
            DATE_TIME_FORMATTER);
    public static final String VALID_START_TIME_MACHOMAN_TOMORROW = LocalDateTime.now().plusDays(1).format(
            DATE_TIME_FORMATTER);
    public static final String VALID_START_TIME_MACHOMAN_PLUS2MONTHS = LocalDateTime.now().plusMonths(2).format(
            DATE_TIME_FORMATTER);
    public static final String VALID_START_TIME_MACHOMAN_MINUS1WEEK = LocalDateTime.now().minusWeeks(1).format(
            DATE_TIME_FORMATTER);
    public static final String VALID_START_TIME_ULTRAMAN = "30/09/2020 1800";

    public static final String VALID_DURATION_GETWELL = "120";
    public static final String VALID_DURATION_MACHOMAN = "150";
    public static final String VALID_DURATION_ULTRAMAN = "170";

    public static final String GYM_DESC_GETWELL = " " + PREFIX_GYM + VALID_GYM_GETWELL;
    public static final String GYM_DESC_MACHOMAN = " " + PREFIX_GYM + VALID_GYM_MACHOMAN;
    public static final String EXERCISE_TYPE_DESC_GETWELL = " " + PREFIX_EXERCISE_TYPE + VALID_EXERCISE_TYPE_GETWELL;
    public static final String EXERCISE_TYPE_DESC_MACHOMAN = " " + PREFIX_EXERCISE_TYPE + VALID_EXERCISE_TYPE_MACHOMAN;
    public static final String START_TIME_DESC_GETWELL = " " + PREFIX_START_TIME + VALID_START_TIME_GETWELL;
    public static final String START_TIME_DESC_MACHOMAN = " " + PREFIX_START_TIME + VALID_START_TIME_MACHOMAN;
    public static final String DURATION_DESC_GETWELL = " " + PREFIX_DURATION + VALID_DURATION_GETWELL;
    public static final String DURATION_DESC_MACHOMAN = " " + PREFIX_DURATION + VALID_DURATION_MACHOMAN;

    public static final String INVALID_GYM_DESC = " " + PREFIX_GYM; // empty string not allowed for gyms
    public static final String INVALID_EXERCISE_TYPE_DESC =
            " " + PREFIX_EXERCISE_TYPE; // empty string not allowed for gyms
    public static final String INVALID_START_TIME_DESC =
            " " + PREFIX_START_TIME + "29/09/2020"; // unsupported date time format
    public static final String INVALID_DURATION_DESC = " " + PREFIX_DURATION + "-123"; // a not allowed in duration

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditSessionDescriptor DESC_GETWELL = new EditSessionDescriptorBuilder()
            .withGym(VALID_GYM_GETWELL)
            .withExerciseType(VALID_EXERCISE_TYPE_GETWELL)
            .withInterval(VALID_START_TIME_GETWELL,
                    VALID_DURATION_GETWELL)
            .build();
    public static final EditSessionDescriptor DESC_MACHOMAN = new EditSessionDescriptorBuilder()
            .withGym(VALID_GYM_MACHOMAN)
            .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN)
            .withInterval(VALID_START_TIME_MACHOMAN,
                    VALID_DURATION_MACHOMAN)
            .build();

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered Session list and selected Session in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Session> expectedFilteredList = new ArrayList<>(actualModel.getFilteredSessionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredSessionList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the Session at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showSessionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredSessionList().size());

        Session session = model.getFilteredSessionList().get(targetIndex.getZeroBased());
        final LocalDateTime start = session.getStartTime();
        model.updateFilteredSessionList(new IntervalContainsDatetimePredicate(start));

        assertEquals(1, model.getFilteredSessionList().size());
    }
}
