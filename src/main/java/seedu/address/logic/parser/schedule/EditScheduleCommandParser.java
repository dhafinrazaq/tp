package seedu.address.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_UPDATED_SESSION_INDEX;
import static seedu.address.logic.parser.schedule.CliSyntax.PREFIX_WEIGHT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.EditScheduleCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Remark;

public class EditScheduleCommandParser implements Parser<EditScheduleCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditScheduleCommand
     * and returns an EditScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_INDEX, PREFIX_SESSION_INDEX,
                        PREFIX_UPDATED_SESSION_INDEX, PREFIX_PAYMENT_STATUS, PREFIX_REMARK, PREFIX_WEIGHT);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT_INDEX, PREFIX_SESSION_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditScheduleCommand.MESSAGE_USAGE));
        }

        Index clientIndex;
        Index sessionIndex;
        try {
            clientIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLIENT_INDEX).get());
            sessionIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SESSION_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditScheduleCommand.MESSAGE_USAGE), pe);
        }

        EditScheduleDescriptor editScheduleDescriptor = new EditScheduleDescriptor();

        if (argMultimap.getValue(PREFIX_SESSION_INDEX).isPresent()) {
            editScheduleDescriptor
                    .setSessionIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SESSION_INDEX).get()));
        }

        if (argMultimap.getValue(PREFIX_CLIENT_INDEX).isPresent()) {
            editScheduleDescriptor
                    .setClientIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLIENT_INDEX)
                            .get()));
        }

        if (argMultimap.getValue(PREFIX_UPDATED_SESSION_INDEX).isPresent()) {
            editScheduleDescriptor
                    .setUpdatedSessionIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_UPDATED_SESSION_INDEX)
                            .get()));
        }

        if (argMultimap.getValue(PREFIX_PAYMENT_STATUS).isPresent()) {
            editScheduleDescriptor
                    .setUpdatedPayment(ScheduleParserUtil
                            .parsePaymentStatus(argMultimap.getValue(PREFIX_PAYMENT_STATUS).get()));
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editScheduleDescriptor
                    .setUpdatedRemark(new Remark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            editScheduleDescriptor
                    .setUpdatedWeight(ScheduleParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get()));
        }

        if (!editScheduleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditScheduleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditScheduleCommand(clientIndex, sessionIndex, editScheduleDescriptor);
    }

}
