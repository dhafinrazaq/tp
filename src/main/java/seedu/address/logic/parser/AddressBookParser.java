package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.client.AddClientCommand;
import seedu.address.logic.commands.client.DeleteClientCommand;
import seedu.address.logic.commands.client.EditClientCommand;
import seedu.address.logic.commands.client.FindClientCommand;
import seedu.address.logic.commands.client.ListClientCommand;
import seedu.address.logic.commands.client.ViewClientCommand;
import seedu.address.logic.commands.schedule.AddScheduleCommand;
import seedu.address.logic.commands.schedule.DeleteScheduleCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand;
import seedu.address.logic.commands.session.AddSessionCommand;
import seedu.address.logic.commands.session.DeleteSessionCommand;
import seedu.address.logic.commands.session.EditSessionCommand;
import seedu.address.logic.commands.session.ViewSessionCommand;
import seedu.address.logic.parser.client.AddClientCommandParser;
import seedu.address.logic.parser.client.DeleteClientCommandParser;
import seedu.address.logic.parser.client.EditClientCommandParser;
import seedu.address.logic.parser.client.FindClientCommandParser;
import seedu.address.logic.parser.client.ViewClientCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.schedule.AddScheduleCommandParser;
import seedu.address.logic.parser.schedule.DeleteScheduleCommandParser;
import seedu.address.logic.parser.schedule.EditScheduleCommandParser;
import seedu.address.logic.parser.session.AddSessionCommandParser;
import seedu.address.logic.parser.session.DeleteSessionCommandParser;
import seedu.address.logic.parser.session.EditSessionCommandParser;
import seedu.address.logic.parser.session.ViewSessionCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {
    /**
     * Creates a Function that can throw ParseException
     */
    private interface ParseFunction<T, U> {
        U apply(T args) throws ParseException;
    }

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used a hashmap to get related function.
     */
    private static final HashMap<String, ParseFunction<String, Command>> commandMapper;
    static {
        commandMapper = new HashMap<>();

        //Client-Related Commands
        commandMapper.put(AddClientCommand.COMMAND_WORD, (args) -> new AddClientCommandParser().parse(args));
        commandMapper.put(EditClientCommand.COMMAND_WORD, (args) -> new EditClientCommandParser().parse(args));
        commandMapper.put(DeleteClientCommand.COMMAND_WORD, (args) -> new DeleteClientCommandParser().parse(args));
        commandMapper.put(FindClientCommand.COMMAND_WORD, (args) -> new FindClientCommandParser().parse(args));
        commandMapper.put(ViewClientCommand.COMMAND_WORD, (args) -> new ViewClientCommandParser().parse(args));
        commandMapper.put(ListClientCommand.COMMAND_WORD, (args) -> new ListClientCommand());

        //Session-Related Commands
        commandMapper.put(AddSessionCommand.COMMAND_WORD, (args) -> new AddSessionCommandParser().parse(args));
        commandMapper.put(EditSessionCommand.COMMAND_WORD, (args) -> new EditSessionCommandParser().parse(args));
        commandMapper.put(DeleteSessionCommand.COMMAND_WORD, (args) -> new DeleteSessionCommandParser().parse(args));
        commandMapper.put(ViewSessionCommand.COMMAND_WORD, (args) -> new ViewSessionCommandParser().parse(args));

        //Schedule-Related Commands
        commandMapper.put(AddScheduleCommand.COMMAND_WORD, (args) -> new AddScheduleCommandParser().parse(args));
        commandMapper.put(DeleteScheduleCommand.COMMAND_WORD, (args) -> new DeleteScheduleCommandParser().parse(args));
        commandMapper.put(EditScheduleCommand.COMMAND_WORD, (args) -> new EditScheduleCommandParser().parse(args));

        //Common-Application Commands
        commandMapper.put(ClearCommand.COMMAND_WORD, (args) -> new ClearCommand());
        commandMapper.put(ExitCommand.COMMAND_WORD, (args) -> new ExitCommand());
        commandMapper.put(HelpCommand.COMMAND_WORD, (args) -> new HelpCommand());
        commandMapper.put(SettingsCommand.COMMAND_WORD, (args) -> new SettingsCommand());
        commandMapper.put(HomeCommand.COMMAND_WORD, (args) -> new HomeCommand());
    }

    /**
     * Gets the full set of commands available in this program
     *
     * @return a List of String
     */
    public static List<String> getCommandList() {
        return commandMapper.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandMapper.containsKey(commandWord)) {
            return commandMapper.get(commandWord).apply(arguments);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
