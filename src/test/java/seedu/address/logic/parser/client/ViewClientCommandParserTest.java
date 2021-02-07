package seedu.address.logic.parser.client;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.client.ViewClientCommand;

public class ViewClientCommandParserTest {

    private ViewClientCommandParser parser = new ViewClientCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "1", new ViewClientCommand(INDEX_FIRST_CLIENT));
    }

    @Test
    public void parse_invalidArgsChar_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNum_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewClientCommand.MESSAGE_USAGE));
    }
}
