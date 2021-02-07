package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedSession.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.session.SessionParserUtil;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;

public class JsonAdaptedSessionTest {
    private static final String INVALID_GYM = " ";
    private static final String INVALID_EXERCISE_TYPE = " ";
    private static final String INVALID_END_TIME = "0";
    private static final String INVALID_START_TIME = "example.com";

    private static final String VALID_GYM = MACHOMAN.getGym().toString();
    private static final String VALID_EXERCISE_TYPE = MACHOMAN.getExerciseType().toString();
    private static final String VALID_START_TIME = SessionParserUtil.parseDateTimeToString(MACHOMAN.getStartTime());
    private static final String VALID_END_TIME = SessionParserUtil.parseDateTimeToString(MACHOMAN.getEndTime());

    @Test
    public void toModelType_validSessionDetails_returnsSession() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(MACHOMAN);
        assertEquals(MACHOMAN, session.toModelType());
    }

    @Test
    public void toModelType_invalidGym_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(INVALID_GYM, VALID_EXERCISE_TYPE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Gym.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullGym_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(null,
                VALID_EXERCISE_TYPE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gym.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidExerciseType_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_GYM, INVALID_EXERCISE_TYPE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = ExerciseType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullExerciseType_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_GYM, null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExerciseType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_GYM, VALID_EXERCISE_TYPE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Interval.MESSAGE_START_TIME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }


    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_GYM, VALID_EXERCISE_TYPE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = Interval.MESSAGE_END_TIME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_GYM, VALID_EXERCISE_TYPE, VALID_END_TIME, VALID_START_TIME);
        String expectedMessage = Interval.MESSAGE_DURATION_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }


}
