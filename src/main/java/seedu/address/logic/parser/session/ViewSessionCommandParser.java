package seedu.address.logic.parser.session;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.session.ViewSessionCommand.isValidPeriod;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_PERIOD;

import seedu.address.logic.commands.session.ViewSessionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewSessionCommandParser implements Parser<ViewSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewSessionCommand
     * and returns an ViewSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERIOD);

        if (argMultimap.getValue(PREFIX_PERIOD).isEmpty() || !argMultimap.getPreamble().isEmpty()
                || !isValidPeriod(argMultimap.getValue(PREFIX_PERIOD).get())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
        }

        return new ViewSessionCommand(argMultimap.getValue(PREFIX_PERIOD).get());
    }
}

