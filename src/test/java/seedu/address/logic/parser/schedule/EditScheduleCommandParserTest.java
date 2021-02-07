package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.CLIENT_INDEX_DESC_A;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.SESSION_INDEX_DESC_A;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_PAYMENT_PAID;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_PAYMENT_UNPAID;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_REMARK_EMPTY;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_REMARK_NONEMPTY;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_SESSION_INDEX_DESC_B;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_WEIGHT_INVALID_0;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_WEIGHT_INVALID_1;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_WEIGHT_INVALID_2;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_WEIGHT_INVALID_NEG;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_WEIGHT_VALID;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.UPDATED_WEIGHT_VALID2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_PAID;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_UNPAID;
import static seedu.address.testutil.TypicalSchedules.TEST_REMARK;
import static seedu.address.testutil.TypicalSchedules.TEST_WEIGHT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.EditScheduleCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Weight;
import seedu.address.model.util.WeightUnit;
import seedu.address.testutil.EditScheduleDescriptorBuilder;

public class EditScheduleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditScheduleCommand.MESSAGE_USAGE);

    private final EditScheduleCommandParser parser = new EditScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no session index and update session index specified
        assertParseFailure(parser, " c/1", MESSAGE_INVALID_FORMAT);

        // no client index and update session index specified
        assertParseFailure(parser, " s/1", MESSAGE_INVALID_FORMAT);

        // no updated field specified
        assertParseFailure(parser, " c/1 s/1", EditScheduleCommand.MESSAGE_NOT_EDITED);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, " c/-5 s/-1 us/-3 pd/false r/", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, " c/0 s/1 us/1 pd/true r/ test" , MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " 1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " c/1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_SESSION_INDEX_DESC_B
                + UPDATED_PAYMENT_UNPAID + UPDATED_REMARK_NONEMPTY + UPDATED_WEIGHT_VALID;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedSessionIndex(INDEX_SECOND_SESSION)
                .withUpdatedPaymentStatus(PAYMENT_UNPAID)
                .withUpdatedRemark(TEST_REMARK)
                .withUpdatedWeight(TEST_WEIGHT)
                .build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A
                + UPDATED_SESSION_INDEX_DESC_B;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedSessionIndex(INDEX_SECOND_SESSION)
                .build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // updated paymentStatus
        String userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_PAYMENT_PAID;
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedPaymentStatus(PAYMENT_PAID)
                .build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // updated remark
        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_REMARK_NONEMPTY;
        descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedRemark(TEST_REMARK)
                .build();
        expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //update weight
        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_WEIGHT_VALID;
        descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedWeight(TEST_WEIGHT)
                .build();
        expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        // is paid and then updated session index
        String userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_PAYMENT_UNPAID
                + UPDATED_SESSION_INDEX_DESC_B;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedSessionIndex(INDEX_SECOND_SESSION)
                .withUpdatedPaymentStatus(PAYMENT_UNPAID)
                .build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // update remark twice, takes last
        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_REMARK_NONEMPTY
                + UPDATED_REMARK_EMPTY;

        descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedRemark(Remark.EMPTY_REMARK)
                .build();
        expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        //update weight twice, takes last
        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_WEIGHT_VALID2
                + UPDATED_WEIGHT_VALID;

        descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedWeight(TEST_WEIGHT)
                .build();
        expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_SESSION_INDEX_DESC_B;
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedSessionIndex(INDEX_SECOND_SESSION)
                .build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //        // other valid values specified
        //        userInput = targetIndex.getOneBased() + GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
        //                + START_TIME_DESC_MACHOMAN
        //                + DURATION_DESC_MACHOMAN;
        //        descriptor = new EditScheduleDescriptorBuilder()
        //                .withGym(VALID_GYM_MACHOMAN)
        //                .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN)
        //                .withInterval(VALID_START_TIME_MACHOMAN,
        //                        VALID_DURATION_MACHOMAN)
        //                .build();
        //        expectedCommand = new EditScheduleCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidWeight_thrownParseException() {
        String userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_WEIGHT_INVALID_0;
        assertParseFailure(parser, userInput, Weight.MESSAGE_INVALID_WEIGHT_STATUS);

        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_WEIGHT_INVALID_NEG;
        assertParseFailure(parser, userInput, Weight.MESSAGE_INVALID_WEIGHT_STATUS);

        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_WEIGHT_INVALID_1;
        assertParseFailure(parser, userInput, WeightUnit.MESSAGE_INVALID_UNIT_STATUS);

        userInput = CLIENT_INDEX_DESC_A + SESSION_INDEX_DESC_A + UPDATED_WEIGHT_INVALID_2;
        assertParseFailure(parser, userInput, Weight.MESSAGE_INVALID_WEIGHT_STATUS);
    }
}
