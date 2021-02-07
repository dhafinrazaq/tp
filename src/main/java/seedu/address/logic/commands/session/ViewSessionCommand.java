package seedu.address.logic.commands.session;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_PERIOD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.session.Session;

/**
 * Finds and lists all Sessions in address book regardless of date and time.
 */
public class ViewSessionCommand extends Command {

    public static final String COMMAND_WORD = "sview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the sessions shown in the Session List "
            + "to the period specified. "
            + "Possible periods -> 'week', 'all', 'future', 'past'. "
            + "Variable periods are also possible with this format: (+/-)#(D/W/M/Y) where # is a non-negative integer. "
            + "Parameters: " + PREFIX_PERIOD + "PERIOD \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERIOD + "week "
            + "or " + COMMAND_WORD + " " + PREFIX_PERIOD + "+3W";
    public static final String MESSAGE_SHOW_SESSIONS_SUCCESS = "Session List updated with requested period: %1$s";

    public static final Pattern VALID_PATTERN = Pattern.compile("^(\\+|-)\\d+[DdWwMmYy]$");

    public static final String VALID_WEEK_SESSIONS_PERIOD = "week";
    public static final String VALID_ALL_SESSIONS_PERIOD = "all";
    public static final String VALID_FUTURE_SESSIONS_PERIOD = "future";
    public static final String VALID_PAST_SESSIONS_PERIOD = "past";

    public static final Predicate<Session> PREDICATE_SHOW_UPCOMING_WEEK_SESSIONS = (session) ->
            session.getStartTime().isBefore(LocalDateTime.now().truncatedTo(DAYS).plusDays(8).minusMinutes(1))
                    && session.getStartTime().isAfter(LocalDateTime.now().truncatedTo(DAYS));
    public static final Predicate<Session> PREDICATE_SHOW_FUTURE_SESSIONS = (session) ->
            session.getStartTime().isAfter(LocalDateTime.now());
    public static final Predicate<Session> PREDICATE_SHOW_PAST_SESSIONS = (session) ->
            session.getEndTime().isBefore(LocalDateTime.now());

    /**
     * Uses a hashmap to get the corresponding predicate for the period specified.
     */
    public static final HashMap<String, Predicate<Session>> PREDICATE_HASH_MAP = new HashMap<>();
    static {
        PREDICATE_HASH_MAP.put(VALID_WEEK_SESSIONS_PERIOD, PREDICATE_SHOW_UPCOMING_WEEK_SESSIONS);
        PREDICATE_HASH_MAP.put(VALID_FUTURE_SESSIONS_PERIOD, PREDICATE_SHOW_FUTURE_SESSIONS);
        PREDICATE_HASH_MAP.put(VALID_PAST_SESSIONS_PERIOD, PREDICATE_SHOW_PAST_SESSIONS);
        PREDICATE_HASH_MAP.put(VALID_ALL_SESSIONS_PERIOD, PREDICATE_SHOW_ALL_SESSIONS);
    }
    private String period;

    /** Creates a ViewSessionCommand.
     *
     * @param period non-empty string that represents a valid period
     */
    public ViewSessionCommand(String period) {
        assert(isValidPeriod(period));
        this.period = period;
    }

    /**
     * Checks if a string provided by the user is recognised as a period
     *
     * @param period a String to check whether if it is a valid period.
     * @return true if period is valid, false otherwise.
     */
    public static boolean isValidPeriod(String period) {
        return PREDICATE_HASH_MAP.containsKey(period) || VALID_PATTERN.matcher(period).matches();
    }

    private ChronoUnit getUnitOfTime(char c) {
        switch (c) {
        case 'D':
        case 'd':
            return ChronoUnit.DAYS;
        case 'M':
        case 'm':
            return ChronoUnit.MONTHS;
        case 'Y':
        case 'y':
            return ChronoUnit.YEARS;
        case 'W':
        case 'w':
            return ChronoUnit.WEEKS;
        default:
            return null; //It should never reach here, because it matches the pattern
        }
    }

    /**
     * Returns a Predicate that filters Sessions that starts between current date and a custom date
     * (inclusive of current date). If custom date is in the past, start from 0000hrs; else, end at 2359hrs.
     *
     * @param periodAmount amount of temporal unit to offset from current date.
     * @param periodUnit type of temporal unit based on ChronoUnit.
     * @param isPast true if the specified amount of time should be subtracted from current date,
     *               false if it should be added to current date.
     * @return the corresponding predicate based on amount and unit of time to offset from current date.
     */
    private static Predicate<Session> getCustomPeriodPredicate(int periodAmount, ChronoUnit periodUnit,
                                                               boolean isPast) {
        assert(periodUnit != null);
        if (isPast) {
            return (session) -> session.getStartTime().isAfter(LocalDateTime.now().truncatedTo(DAYS)
                    .minus(periodAmount, periodUnit))
                    && session.getStartTime().isBefore(LocalDateTime.now().truncatedTo(DAYS)
                    .plusDays(1).minusMinutes(1));
        } else {
            return (session) -> session.getStartTime().isBefore(LocalDateTime.now().truncatedTo(DAYS)
                    .plus(periodAmount, periodUnit).plusDays(1).minusMinutes(1))
                    && session.getStartTime().isAfter(LocalDateTime.now().truncatedTo(DAYS));
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Session> pred = PREDICATE_HASH_MAP.get(period);

        if (pred == null) {
            boolean isPast = (this.period.charAt(0) == '-') ? true : false;
            int periodAmount = Integer.parseInt(this.period.substring(1, this.period.length() - 1));
            ChronoUnit periodUnit = getUnitOfTime(this.period.charAt(this.period.length() - 1));
            pred = getCustomPeriodPredicate(periodAmount, periodUnit, isPast);
        }
        model.updateFilteredSessionList(pred);
        return new CommandResult(String.format(MESSAGE_SHOW_SESSIONS_SUCCESS, period));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewSessionCommand // instanceof handles nulls
                && period.equals(((ViewSessionCommand) other).period)); // state check
    }
}
