package seedu.address.logic.parser.session;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.session.ViewSessionCommand.VALID_ALL_SESSIONS_PERIOD;
import static seedu.address.logic.commands.session.ViewSessionCommand.VALID_FUTURE_SESSIONS_PERIOD;
import static seedu.address.logic.commands.session.ViewSessionCommand.VALID_WEEK_SESSIONS_PERIOD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_PERIOD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.session.ViewSessionCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the SessionParserUtil, and
 * therefore should be covered by the SessionParserUtilTest.
 */
public class ViewSessionCommandParserTest {

    public static final String VALID_ALL_SESSION_PREFIX = " " + PREFIX_PERIOD + VALID_ALL_SESSIONS_PERIOD;
    public static final String VALID_WEEK_SESSION_PREFIX = " " + PREFIX_PERIOD + VALID_WEEK_SESSIONS_PERIOD;
    public static final String VALID_FUTURE_SESSION_PREFIX = " " + PREFIX_PERIOD + VALID_FUTURE_SESSIONS_PERIOD;
    public static final String INVALID_SESSION_PREFIX = " " + PREFIX_PERIOD + "TOMORROW";
    public static final String INVALID_EMPTY_SESSION_PREFIX = " " + PREFIX_PERIOD + "";
    public static final String EMPTY_PREAMBLE = " ";

    //Variable SVIEW ARGS
    public static final String VAR_PREFIX_PERIOD = " " + PREFIX_PERIOD + " ";
    public static final String VALID_PLUS_DAY_CAP_PERIOD = "+5D";
    public static final String VALID_MINUS_DAY_SMALL_PERIOD = "-2d";
    public static final String VALID_PLUS_MONTH_CAP_PERIOD = "+1M";
    public static final String VALID_MINUS_WEEK_CAP_PERIOD = "-10W";
    public static final String VALID_PLUS_YEAR_CAP_PERIOD = "+0Y";
    public static final String INVALID_NO_SYMBOL_PERIOD = "10m";
    public static final String INVALID_NO_AMOUNT_PERIOD = "-w";
    public static final String INVALID_NO_UNIT_PERIOD = "+5";
    public static final String INVALID_WRONG_UNIT_PERIOD = "+9s";

    private ViewSessionCommandParser parser = new ViewSessionCommandParser();

    @Test
    public void parse_allSessionArgs_returnsViewCommand() {
        assertParseSuccess(parser, VALID_ALL_SESSION_PREFIX, new ViewSessionCommand(VALID_ALL_SESSIONS_PERIOD));
    }

    @Test
    public void parse_weekSessionArgs_returnsViewCommand() {
        assertParseSuccess(parser, VALID_WEEK_SESSION_PREFIX, new ViewSessionCommand(VALID_WEEK_SESSIONS_PERIOD));
    }

    @Test
    public void parse_futureSessionArgs_returnsViewCommand() {
        assertParseSuccess(parser, VALID_FUTURE_SESSION_PREFIX, new ViewSessionCommand(VALID_FUTURE_SESSIONS_PERIOD));
    }

    @Test
    public void parse_variablePlusDayCapsArgs_returnViewCommand() {
        assertParseSuccess(parser, VAR_PREFIX_PERIOD + VALID_PLUS_DAY_CAP_PERIOD,
                new ViewSessionCommand(VALID_PLUS_DAY_CAP_PERIOD));
    }

    @Test
    public void parse_variableMinusDaySmallsArgs_returnViewCommand() {
        assertParseSuccess(parser, VAR_PREFIX_PERIOD + VALID_MINUS_DAY_SMALL_PERIOD,
                new ViewSessionCommand(VALID_MINUS_DAY_SMALL_PERIOD));
    }

    @Test
    public void parse_variablePlusMonthArgs_returnViewCommand() {
        assertParseSuccess(parser, VAR_PREFIX_PERIOD + VALID_PLUS_MONTH_CAP_PERIOD,
                new ViewSessionCommand(VALID_PLUS_MONTH_CAP_PERIOD));
    }

    @Test
    public void parse_variableMinusWeekArgs_returnViewCommand() {
        assertParseSuccess(parser, VAR_PREFIX_PERIOD + VALID_MINUS_WEEK_CAP_PERIOD,
                new ViewSessionCommand(VALID_MINUS_WEEK_CAP_PERIOD));
    }

    @Test
    public void parse_variablePlusZeroYearArgs_returnViewCommand() {
        assertParseSuccess(parser, VAR_PREFIX_PERIOD + VALID_PLUS_YEAR_CAP_PERIOD,
                new ViewSessionCommand(VALID_PLUS_YEAR_CAP_PERIOD));
    }

    @Test
    public void parse_variableNoSymbolArgs_throwParseException() {
        assertParseFailure(parser, VAR_PREFIX_PERIOD + INVALID_NO_SYMBOL_PERIOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_variableNoAmountArgs_throwParseException() {
        assertParseFailure(parser, VAR_PREFIX_PERIOD + INVALID_NO_AMOUNT_PERIOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_variableNoUnitArgs_throwParseException() {
        assertParseFailure(parser, VAR_PREFIX_PERIOD + INVALID_NO_UNIT_PERIOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_variableInvalidUnitArgs_throwParseException() {
        assertParseFailure(parser, VAR_PREFIX_PERIOD + INVALID_WRONG_UNIT_PERIOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSessionArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_SESSION_PREFIX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptySessionArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_EMPTY_SESSION_PREFIX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, EMPTY_PREAMBLE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewSessionCommand.MESSAGE_USAGE));
    }
}
