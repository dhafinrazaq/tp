package seedu.address.logic.parser.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;

public class SessionParserUtilTest {
    private static final String INVALID_DURATION = "0";
    private static final String INVALID_START_TIME = "32/09/2020 1600";
    private static final String INVALID_END_TIME = "29/09/2020 1559";

    private static final String VALID_GYM = "Machoman";
    private static final String VALID_EXERCISE_TYPE = "Fitness";
    private static final String VALID_START_TIME = "29/09/2020 1600";
    private static final String VALID_END_TIME = "29/09/2020 1601";
    private static final String VALID_DURATION = "1";
    private static final String WHITESPACE = " \t\r\n";
    private static final LocalDateTime VALID_START_TIME_LOCALDATETIME =
            LocalDateTime.of(2020, 9, 29, 16, 0);

    @Test
    public void parseGym_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SessionParserUtil.parseGym((String) null));
    }

    @Test
    public void parseGym_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SessionParserUtil.parseGym(""));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseGym("  "));
    }

    @Test
    public void parseGym_validValueWithoutWhitespace_returnsGym() throws Exception {
        Gym expectedGym = new Gym(VALID_GYM);
        assertEquals(expectedGym, SessionParserUtil.parseGym(VALID_GYM));
    }

    @Test
    public void parseGym_validValueWithWhitespace_returnsTrimmedGym() throws Exception {
        String gymWithWhitespace = WHITESPACE + VALID_GYM + WHITESPACE;
        Gym expectedGym = new Gym(VALID_GYM);
        assertEquals(expectedGym, SessionParserUtil.parseGym(gymWithWhitespace));
    }

    @Test
    public void parseExerciseType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SessionParserUtil.parseExerciseType((String) null));
    }

    @Test
    public void parseExerciseType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SessionParserUtil.parseExerciseType(""));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseExerciseType("  "));
    }

    @Test
    public void parseExerciseType_validValueWithoutWhitespace_returnsExerciseType() throws Exception {
        ExerciseType expectedExerciseType = new ExerciseType(VALID_EXERCISE_TYPE);
        assertEquals(expectedExerciseType, SessionParserUtil.parseExerciseType(VALID_EXERCISE_TYPE));
    }

    @Test
    public void parseExerciseType_validValueWithWhitespace_returnsTrimmedExerciseType() throws Exception {
        String exerciseTypeWithWhitespace = WHITESPACE + VALID_EXERCISE_TYPE + WHITESPACE;
        ExerciseType expectedExerciseType = new ExerciseType(VALID_EXERCISE_TYPE);
        assertEquals(expectedExerciseType, SessionParserUtil.parseExerciseType(exerciseTypeWithWhitespace));
    }

    @Test
    public void parseStringToDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                SessionParserUtil.parseStringToDateTime((String) null));
    }

    @Test
    public void parseStringToDateTime_invalidDateTime_throwsParseException() {

        // invalid format
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("2x/10/2020 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("29/10/202x 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("29-10-12 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("9/10/2020 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("09/9/2020 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("09/09/20 1600"));

        // invalid month / date
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("12/13/2020 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("32/10/2020 1600"));

        // day-month combination do not exist
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("31/09/2020 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("30/02/2020 1600"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("29/02/2021 1600"));

        // invalid leap year date
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("29/02/2100 1600"));

        // invalid time
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("01/02/2020 2400"));
        assertThrows(ParseException.class, () -> SessionParserUtil.parseStringToDateTime("01/02/2020 2270"));
    }

    @Test
    public void parseStringToDateTime_validValueWithoutWhitespace_returnsCorrectDateTime() throws Exception {
        assertEquals(VALID_START_TIME_LOCALDATETIME, SessionParserUtil.parseStringToDateTime(VALID_START_TIME));
    }

    @Test
    public void parseStringToDateTime_validValueWithWhitespace_returnsCorrectDateTime() throws Exception {
        String startTimeWithWhitespace = WHITESPACE + VALID_START_TIME + WHITESPACE;
        assertEquals(VALID_START_TIME_LOCALDATETIME, SessionParserUtil.parseStringToDateTime(startTimeWithWhitespace));
    }

    @Test
    public void parseIntervalFromStartAndDuration_invalidDuration_throwsParseException() {
        assertThrows(ParseException.class, () -> SessionParserUtil
                .parseIntervalFromStartAndDuration(VALID_START_TIME, INVALID_DURATION));
    }

    @Test
    public void parseIntervalFromStartAndEnd_invalidEndTime_throwsParseException() {
        assertThrows(ParseException.class, () ->
                SessionParserUtil.parseIntervalFromStartAndEnd(VALID_START_TIME, VALID_START_TIME));
        assertThrows(ParseException.class, () ->
                SessionParserUtil.parseIntervalFromStartAndEnd(VALID_START_TIME, INVALID_END_TIME));
    }

    @Test
    public void parseIntervalFromStartAndDuration_validValues_returnsInterval() throws Exception {
        Interval expectedInterval = new Interval(VALID_START_TIME_LOCALDATETIME, 1);
        assertEquals(expectedInterval,
                SessionParserUtil.parseIntervalFromStartAndDuration(VALID_START_TIME, VALID_DURATION));
        assertEquals(expectedInterval, SessionParserUtil
                .parseIntervalFromStartAndDuration(VALID_START_TIME, WHITESPACE + VALID_DURATION + WHITESPACE));
    }

    @Test
    public void parseIntervalFromStartAndEnd_validValues_returnsInterval() throws Exception {
        Interval expectedInterval = new Interval(VALID_START_TIME_LOCALDATETIME, 1);
        assertEquals(expectedInterval,
                SessionParserUtil.parseIntervalFromStartAndEnd(VALID_START_TIME, VALID_END_TIME));
    }

    @Test
    public void isInvalidDateTime_sanityCheck() {
        assertTrue(SessionParserUtil.isInvalidDateTime(INVALID_START_TIME));
        assertFalse(SessionParserUtil.isInvalidDateTime(VALID_START_TIME));
    }
}

