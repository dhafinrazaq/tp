package seedu.address.logic.parser.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_EXERCISE_TYPE;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_GYM;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.session.EditSessionCommand;
import seedu.address.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditSessionCommandParser implements Parser<EditSessionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditSessionCommand
     * and returns an EditSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GYM, PREFIX_EXERCISE_TYPE, PREFIX_START_TIME, PREFIX_DURATION);

        Index index;

        try {
            index = SessionParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditSessionCommand.MESSAGE_USAGE), pe);
        }

        EditSessionDescriptor editSessionDescriptor = new EditSessionDescriptor();
        if (argMultimap.getValue(PREFIX_GYM).isPresent()) {
            editSessionDescriptor.setGym(SessionParserUtil.parseGym(argMultimap.getValue(PREFIX_GYM).get()));
        }
        if (argMultimap.getValue(PREFIX_EXERCISE_TYPE).isPresent()) {
            editSessionDescriptor
                    .setExerciseType(SessionParserUtil
                            .parseExerciseType(argMultimap.getValue(PREFIX_EXERCISE_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent() && argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editSessionDescriptor
                    .setInterval(
                            SessionParserUtil.parseIntervalFromStartAndDuration(argMultimap
                                            .getValue(PREFIX_START_TIME).get(),
                                    argMultimap.getValue(PREFIX_DURATION).get()));
        }

        if (!editSessionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSessionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSessionCommand(index, editSessionDescriptor);
    }

}
