package seedu.address.logic.parser.session;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.address.logic.commands.session.SessionCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.START_TIME_DESC_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.START_TIME_DESC_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_GYM_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_MACHOMAN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.session.AddSessionCommand;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;
import seedu.address.testutil.SessionBuilder;

public class AddSessionCommandParserTest {
    private AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Session expectedSession = new SessionBuilder(MACHOMAN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GYM_DESC_MACHOMAN
                + EXERCISE_TYPE_DESC_MACHOMAN + START_TIME_DESC_MACHOMAN
                + DURATION_DESC_MACHOMAN, new AddSessionCommand(expectedSession));

        // multiple gyms - last gym accepted
        assertParseSuccess(parser, GYM_DESC_GETWELL + GYM_DESC_MACHOMAN
                + EXERCISE_TYPE_DESC_MACHOMAN + START_TIME_DESC_MACHOMAN
                + DURATION_DESC_MACHOMAN, new AddSessionCommand(expectedSession));

        // multiple exercise types - last exercise type accepted
        assertParseSuccess(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_GETWELL
                + EXERCISE_TYPE_DESC_MACHOMAN + START_TIME_DESC_MACHOMAN
                + DURATION_DESC_MACHOMAN, new AddSessionCommand(expectedSession));

        // multiple start times - last start time accepted
        assertParseSuccess(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                + START_TIME_DESC_GETWELL + START_TIME_DESC_MACHOMAN
                + DURATION_DESC_MACHOMAN, new AddSessionCommand(expectedSession));

        // multiple durations - last duration accepted
        assertParseSuccess(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                + START_TIME_DESC_MACHOMAN + DURATION_DESC_GETWELL
                + DURATION_DESC_MACHOMAN, new AddSessionCommand(expectedSession));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_GYM_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                        + START_TIME_DESC_MACHOMAN + DURATION_DESC_MACHOMAN,
                expectedMessage);

        // missing exercise type prefix
        assertParseFailure(parser, GYM_DESC_MACHOMAN + VALID_EXERCISE_TYPE_MACHOMAN
                        + START_TIME_DESC_MACHOMAN + DURATION_DESC_MACHOMAN,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                        + VALID_START_TIME_MACHOMAN + DURATION_DESC_MACHOMAN,
                expectedMessage);

        // missing duration prefix
        assertParseFailure(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                        + START_TIME_DESC_MACHOMAN + VALID_DURATION_MACHOMAN,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_GYM_MACHOMAN + VALID_EXERCISE_TYPE_MACHOMAN
                        + VALID_START_TIME_MACHOMAN + VALID_DURATION_MACHOMAN,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid gym
        assertParseFailure(parser, INVALID_GYM_DESC + EXERCISE_TYPE_DESC_MACHOMAN
                        + START_TIME_DESC_MACHOMAN + DURATION_DESC_MACHOMAN,
                Gym.MESSAGE_CONSTRAINTS);

        // invalid exercise type
        assertParseFailure(parser, GYM_DESC_MACHOMAN + INVALID_EXERCISE_TYPE_DESC
                        + START_TIME_DESC_MACHOMAN + DURATION_DESC_MACHOMAN,
                ExerciseType.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                        + INVALID_START_TIME_DESC + DURATION_DESC_MACHOMAN,
                Interval.MESSAGE_DATE_TIME_CONSTRAINTS);

        // invalid duration
        assertParseFailure(parser, GYM_DESC_MACHOMAN + EXERCISE_TYPE_DESC_MACHOMAN
                        + START_TIME_DESC_MACHOMAN + INVALID_DURATION_DESC,
                Interval.MESSAGE_DURATION_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_GYM_DESC + EXERCISE_TYPE_DESC_MACHOMAN
                        + START_TIME_DESC_MACHOMAN + INVALID_DURATION_DESC,
                Gym.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GYM_DESC_MACHOMAN
                        + EXERCISE_TYPE_DESC_MACHOMAN + START_TIME_DESC_MACHOMAN + DURATION_DESC_MACHOMAN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE));
    }
}

