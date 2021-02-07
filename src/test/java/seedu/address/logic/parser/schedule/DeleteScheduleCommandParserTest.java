package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.DeleteScheduleCommand;

public class DeleteScheduleCommandParserTest {
    private DeleteScheduleCommandParser parser = new DeleteScheduleCommandParser();

    @Test
    public void parse_validArgsClientIndexFirst_returnsDeleteCommand() {
        assertParseSuccess(parser, " c/1 s/1", new DeleteScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION));
    }

    @Test
    public void parse_validArgsSessionIndexFirst_returnsDeleteCommand() {
        assertParseSuccess(parser, " s/1 c/1", new DeleteScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION));
    }

    @Test
    public void parse_havePreamble_returnsParseException() {
        assertParseFailure(parser, "10 c/1 s/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClientIndex_throwsParseException() {
        assertParseFailure(parser, " c/word s/0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSessionIndex_throwsParseException() {
        assertParseFailure(parser, " c/0 s/word",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteScheduleCommand.MESSAGE_USAGE);

        // missing client index prefix
        assertParseFailure(parser, " 2 s/1",
                expectedMessage);

        // missing session index prefix
        assertParseFailure(parser, " c/1 2",
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " 1 2",
                expectedMessage);
    }
}
