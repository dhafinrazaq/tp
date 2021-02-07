package seedu.address.storage;

import static seedu.address.model.session.Interval.MESSAGE_DURATION_CONSTRAINTS;
import static seedu.address.model.session.Interval.MESSAGE_END_TIME_CONSTRAINTS;
import static seedu.address.model.session.Interval.MESSAGE_START_TIME_CONSTRAINTS;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.session.SessionParserUtil;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

public class JsonAdaptedSession {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "A session is missing %s field!";

    private final String gym;
    private final String exerciseType;
    private final String start;
    private final String end;

    /**
     * Constructs a {@code JsonAdaptedSession} with the given Session details.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("gym") String gym,
                              @JsonProperty("exerciseType") String exerciseType,
                              @JsonProperty("start") String start,
                              @JsonProperty("end") String end) {
        this.gym = gym;
        this.exerciseType = exerciseType;
        this.start = start;
        this.end = end;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        gym = source.getGym().toString();
        exerciseType = source.getExerciseType().toString();
        start = SessionParserUtil.parseDateTimeToString(source.getStartTime());
        end = SessionParserUtil.parseDateTimeToString(source.getEndTime());
    }

    /**
     * Converts this Jackson-friendly adapted Session object into the model's {@code Session} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Session.
     */
    public Session toModelType() throws IllegalValueException {
        /* To do the same as Client's toModelType codes, we need to create field-typed classes */

        if (gym == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gym.class.getSimpleName()));
        }
        if (!Gym.isValidGym(gym)) {
            throw new IllegalValueException(Gym.MESSAGE_CONSTRAINTS);
        }
        Gym modelGym = new Gym(gym);

        if (exerciseType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExerciseType.class.getSimpleName()));
        }
        if (!ExerciseType.isValidExerciseType(exerciseType)) {
            throw new IllegalValueException(ExerciseType.MESSAGE_CONSTRAINTS);
        }
        ExerciseType modelExerciseType = new ExerciseType(exerciseType);


        if (start == null || SessionParserUtil.isInvalidDateTime(start)) {
            throw new IllegalValueException(MESSAGE_START_TIME_CONSTRAINTS);
        }

        if (end == null || SessionParserUtil.isInvalidDateTime(end)) {
            throw new IllegalValueException(MESSAGE_END_TIME_CONSTRAINTS);
        }

        final LocalDateTime startDateTime = SessionParserUtil.parseStringToDateTime(start);
        final LocalDateTime endDateTime = SessionParserUtil.parseStringToDateTime(end);
        final int duration = (int) startDateTime.until(endDateTime, ChronoUnit.MINUTES);

        if (!Interval.isValidInterval(duration)) {
            throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
        }

        Interval modelInterval = new Interval(startDateTime, duration);

        return new Session(modelGym, modelExerciseType, modelInterval);
    }
}
