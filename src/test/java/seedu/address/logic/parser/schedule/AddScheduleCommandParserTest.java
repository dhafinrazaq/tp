package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.AddScheduleCommand;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_validArgs_returnsAddScheduleCommand() {
        assertParseSuccess(parser, " c/1 s/1", new AddScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION));
    }

    @Test
    public void parse_missingClientIndex_throwsParseException() {
        assertParseFailure(parser, " 1 s/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddScheduleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " s/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSessionIndex_throwsParseException() {
        assertParseFailure(parser, " c/1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddScheduleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " c/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddScheduleCommand.MESSAGE_USAGE));
    }
}
