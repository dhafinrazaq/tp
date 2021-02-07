package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_UPDATED_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_PAID;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_UNPAID;
import static seedu.address.testutil.TypicalSchedules.TEST_REMARK;
import static seedu.address.testutil.TypicalSchedules.TEST_WEIGHT;
import static seedu.address.testutil.TypicalSchedules.TEST_WEIGHT2;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EditScheduleDescriptorBuilder;

public class ScheduleCommandTestUtil {
    public static final String CLIENT_INDEX_DESC_A = " " + PREFIX_CLIENT_INDEX + "1";
    public static final String CLIENT_INDEX_DESC_B = " " + PREFIX_CLIENT_INDEX + "2";
    public static final String SESSION_INDEX_DESC_A = " " + PREFIX_SESSION_INDEX + "1";
    public static final String SESSION_INDEX_DESC_B = " " + PREFIX_SESSION_INDEX + "2";
    public static final String UPDATED_SESSION_INDEX_DESC_A = " " + PREFIX_UPDATED_SESSION_INDEX + "1";
    public static final String UPDATED_SESSION_INDEX_DESC_B = " " + PREFIX_UPDATED_SESSION_INDEX + "2";
    public static final String UPDATED_PAYMENT_PAID = " " + PREFIX_PAYMENT_STATUS + PAYMENT_PAID;
    public static final String UPDATED_PAYMENT_UNPAID = " " + PREFIX_PAYMENT_STATUS + PAYMENT_UNPAID;
    public static final String UPDATED_REMARK_NONEMPTY = " " + PREFIX_REMARK + TEST_REMARK;
    public static final String UPDATED_REMARK_EMPTY = " " + PREFIX_REMARK + Remark.EMPTY_REMARK;
    public static final String UPDATED_WEIGHT_VALID = " " + PREFIX_WEIGHT + TEST_WEIGHT.toString();
    public static final String UPDATED_WEIGHT_VALID2 = " " + PREFIX_WEIGHT + TEST_WEIGHT2.toString();
    public static final String UPDATED_WEIGHT_INVALID_0 = " " + PREFIX_WEIGHT + "0";
    public static final String UPDATED_WEIGHT_INVALID_1 = " " + PREFIX_WEIGHT + "0g";
    public static final String UPDATED_WEIGHT_INVALID_2 = " " + PREFIX_WEIGHT + "g";
    public static final String UPDATED_WEIGHT_INVALID_NEG = " " + PREFIX_WEIGHT + "-123";

    public static final EditScheduleDescriptor DESC_SCHA = new EditScheduleDescriptorBuilder()
            .withClientIndex(INDEX_FIRST_CLIENT)
            .withSessionIndex(INDEX_FIRST_SESSION)
            .build();

    public static final EditScheduleDescriptor DESC_SCHB = new EditScheduleDescriptorBuilder()
            .withClientIndex(INDEX_FIRST_CLIENT)
            .withSessionIndex(INDEX_SECOND_SESSION)
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
            System.out.println(ce);
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
        List<Schedule> expectedFilteredList = new ArrayList<>(actualModel.getFilteredScheduleList());
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredScheduleList());
    }
}
