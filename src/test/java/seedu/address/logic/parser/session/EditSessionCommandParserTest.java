package seedu.address.logic.parser.session;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.session.EditSessionCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.DURATION_DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.DURATION_DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.EXERCISE_TYPE_DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.EXERCISE_TYPE_DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.GYM_DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.GYM_DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.INVALID_EXERCISE_TYPE_DESC;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.INVALID_GYM_DESC;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.START_TIME_DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.START_TIME_DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_MACHOMAN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SESSION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.session.EditSessionCommand;
import seedu.address.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.testutil.EditSessionDescriptorBuilder;

public class EditSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSessionCommand.MESSAGE_USAGE);

    private final EditSessionCommandParser parser = new EditSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_GYM_GETWELL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_GYM_GETWELL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_GYM_GETWELL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid gym
        assertParseFailure(parser, "1" + INVALID_GYM_DESC, Gym.MESSAGE_CONSTRAINTS);

        // invalid exercise type
        assertParseFailure(parser,
                "1" + INVALID_EXERCISE_TYPE_DESC,
                ExerciseType.MESSAGE_CONSTRAINTS);

        // invalid interval
        assertParseFailure(parser, "1" + INVALID_START_TIME_DESC + INVALID_DURATION_DESC,
                Interval.MESSAGE_DATE_TIME_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1"
                        + INVALID_GYM_DESC + INVALID_EXERCISE_TYPE_DESC
                        + VALID_START_TIME_MACHOMAN + VALID_DURATION_GETWELL ,
                        Gym.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SESSION;
        String userInput = targetIndex.getOneBased() + GYM_DESC_GETWELL + EXERCISE_TYPE_DESC_GETWELL
                + START_TIME_DESC_GETWELL + DURATION_DESC_GETWELL;

        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder()
                .withGym(VALID_GYM_GETWELL)
                .withExerciseType(VALID_EXERCISE_TYPE_GETWELL)
                .withInterval(VALID_START_TIME_GETWELL,
                        VALID_DURATION_GETWELL)
                .build();
        EditSessionCommand expectedCommand = new EditSessionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SESSION;
        String userInput = targetIndex.getOneBased() + GYM_DESC_GETWELL + EXERCISE_TYPE_DESC_GETWELL;

        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder()
                .withGym(VALID_GYM_GETWELL)
                .withExerciseType(VALID_EXERCISE_TYPE_GETWELL).build();
        EditSessionCommand expectedCommand = new EditSessionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // gym
        Index targetIndex = INDEX_THIRD_SESSION;
        String userInput = targetIndex.getOneBased() + GYM_DESC_GETWELL;
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder().withGym(VALID_GYM_GETWELL).build();
        EditSessionCommand expectedCommand = new EditSessionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // exercise type
        userInput = targetIndex.getOneBased() + EXERCISE_TYPE_DESC_GETWELL;
        descriptor = new EditSessionDescriptorBuilder().withExerciseType(VALID_EXERCISE_TYPE_GETWELL).build();
        expectedCommand = new EditSessionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // interval
        userInput = targetIndex.getOneBased() + START_TIME_DESC_GETWELL + DURATION_DESC_GETWELL;
        descriptor = new EditSessionDescriptorBuilder()
                .withInterval(VALID_START_TIME_GETWELL,
                        VALID_DURATION_GETWELL).build();
        expectedCommand = new EditSessionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SESSION;
        String userInput = targetIndex.getOneBased() + GYM_DESC_GETWELL + EXERCISE_TYPE_DESC_GETWELL
                + START_TIME_DESC_GETWELL + DURATION_DESC_GETWELL + GYM_DESC_MACHOMAN
                + EXERCISE_TYPE_DESC_MACHOMAN + START_TIME_DESC_MACHOMAN + DURATION_DESC_MACHOMAN;

        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder()
                .withGym(VALID_GYM_MACHOMAN)
                .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN)
                .withInterval(VALID_START_TIME_MACHOMAN,
                        VALID_DURATION_MACHOMAN)
                .build();
        EditSessionCommand expectedCommand = new EditSessionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SESSION;
        String userInput = targetIndex.getOneBased() + INVALID_GYM_DESC + GYM_DESC_MACHOMAN;
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder().withGym(VALID_GYM_MACHOMAN).build();
        EditSessionCommand expectedCommand = new EditSessionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                + START_TIME_DESC_MACHOMAN
                + DURATION_DESC_MACHOMAN;
        descriptor = new EditSessionDescriptorBuilder()
                .withGym(VALID_GYM_MACHOMAN)
                .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN)
                .withInterval(VALID_START_TIME_MACHOMAN,
                        VALID_DURATION_MACHOMAN)
                .build();
        expectedCommand = new EditSessionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
