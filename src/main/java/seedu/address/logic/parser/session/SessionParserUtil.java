package seedu.address.logic.parser.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.isNonNegativeInteger;
import static seedu.address.model.session.Interval.MESSAGE_END_TIME_CONSTRAINTS;
import static seedu.address.model.session.Interval.MESSAGE_START_TIME_CONSTRAINTS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;

/**
 * Contains utility methods used for parsing strings in the various *SessionParser classes.
 */
public class SessionParserUtil extends ParserUtil {

    /**
     * Parses a {@code String gym} into a {@code Gym}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param gym String to be parsed.
     * @throws ParseException if the given {@code gym} is invalid.
     */
    public static Gym parseGym(String gym) throws ParseException {
        requireNonNull(gym);
        String trimmedGym = gym.trim();
        if (!Gym.isValidGym(trimmedGym)) {
            throw new ParseException(Gym.MESSAGE_CONSTRAINTS);
        }
        return new Gym(trimmedGym);
    }

    /**
     * Parses a {@code String exerciseType} into an {@code ExerciseType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param exerciseType String to be parsed.
     * @throws ParseException if the given {@code exerciseType} is invalid.
     */
    public static ExerciseType parseExerciseType(String exerciseType) throws ParseException {
        requireNonNull(exerciseType);
        String trimmedExerciseType = exerciseType.trim();
        if (!ExerciseType.isValidExerciseType(trimmedExerciseType)) {
            throw new ParseException(ExerciseType.MESSAGE_CONSTRAINTS);
        }
        return new ExerciseType(trimmedExerciseType);
    }

    /**
     * Parses a {@code String startTime, @code String duration} into an {@code Interval}.
     *
     * @param startTime Interval's start time.
     * @param duration Interval's duration in minutes.
     * @throws ParseException if the given {@code interval} is invalid.
     */
    public static Interval parseIntervalFromStartAndDuration(String startTime, String duration) throws ParseException {
        requireNonNull(startTime, duration);
        if (isInvalidDateTime(startTime)) {
            throw new ParseException(Interval.MESSAGE_DATE_TIME_CONSTRAINTS);
        }

        // trim white spaces from duration
        duration = duration.trim();

        if (!isNonNegativeInteger(duration)) {
            throw new ParseException(Interval.MESSAGE_DURATION_CONSTRAINTS);
        }

        LocalDateTime start = parseStringToDateTime(startTime);
        int dur = Integer.parseInt(duration);

        if (!Interval.isValidInterval(dur)) {
            throw new ParseException(Interval.MESSAGE_DURATION_CONSTRAINTS);
        }
        return new Interval(start, dur);
    }

    /**
     * Parses a {@code String startTime, @code String duration} into an {@code Interval}.
     *
     * @param startTime Interval's start time.
     * @param endTime Interval's end time.
     * @throws ParseException if the given {@code interval} is invalid.
     */
    public static Interval parseIntervalFromStartAndEnd(String startTime, String endTime) throws ParseException {
        requireNonNull(startTime, endTime);

        if (isInvalidDateTime(startTime)) {
            throw new ParseException(MESSAGE_START_TIME_CONSTRAINTS);
        }

        if (isInvalidDateTime(endTime)) {
            throw new ParseException(MESSAGE_END_TIME_CONSTRAINTS);
        }

        LocalDateTime start = parseStringToDateTime(startTime);
        LocalDateTime end = parseStringToDateTime(endTime);

        int duration = (int) Duration.between(start, end).toMinutes();

        if (!Interval.isValidInterval(duration)) {
            throw new ParseException(Interval.MESSAGE_DURATION_CONSTRAINTS);
        }

        return new Interval(start, duration);
    }

    /**
     * Standardizes the output format for LocalDateTime string.
     *
     * @param dateTime LocalDateTime input.
     * @return The String object of datetime parsed.
     */
    public static String parseDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(Interval.DATE_TIME_FORMATTER);
    }

    /**
     * Standardizes the input format for LocalDateTime string.
     *
     * @param input the string to parse into LocalDateTime.
     * @return The LocalDateTime object converted.
     */
    public static LocalDateTime parseStringToDateTime(String input) throws ParseException {
        try {
            return LocalDateTime.parse(input.trim(),
                    Interval.DATE_TIME_FORMATTER.withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            throw new ParseException(Interval.MESSAGE_DATE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Checks whether the String input can be parsed into the standardized LocalDateTime format
     *
     * @param input the String to check.
     * @return true if input is a invalid date time pattern.
     */
    public static boolean isInvalidDateTime(String input) {
        try {
            parseStringToDateTime(input);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
