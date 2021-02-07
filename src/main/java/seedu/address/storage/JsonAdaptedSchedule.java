package seedu.address.storage;

import static seedu.address.model.session.Interval.MESSAGE_END_TIME_CONSTRAINTS;
import static seedu.address.model.session.Interval.MESSAGE_START_TIME_CONSTRAINTS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.schedule.ScheduleParserUtil;
import seedu.address.logic.parser.session.SessionParserUtil;
import seedu.address.model.client.Email;
import seedu.address.model.schedule.PaymentStatus;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Weight;
import seedu.address.model.session.Interval;

public class JsonAdaptedSchedule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "A schedule is missing %s!";
    public static final String START_TIME_FIELD = "session start time";
    public static final String END_TIME_FIELD = "session end time";

    private final String clientEmail;
    private final String start;
    private final String end;
    private final String paymentStatus;
    private final String remark;
    private final String weight;

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given Schedule details.
     */
    @JsonCreator
    public JsonAdaptedSchedule(@JsonProperty("clientEmail") String clientEmail,
                               @JsonProperty("sessionStart") String start,
                               @JsonProperty("sessionEnd") String end,
                               @JsonProperty("paymentStatus") String paymentStatus,
                               @JsonProperty("remark") String remark,
                               @JsonProperty("weight") String weight) {
        this.clientEmail = clientEmail;
        this.start = start;
        this.end = end;
        this.paymentStatus = paymentStatus;
        this.remark = remark;
        this.weight = weight;
    }

    /**
     * Converts a given {@code Schedule} into this class for Jackson use.
     */
    public JsonAdaptedSchedule(Schedule source) {
        clientEmail = source.getClientEmail().toString();
        start = SessionParserUtil.parseDateTimeToString(source.getStartTime());
        end = SessionParserUtil.parseDateTimeToString(source.getEndTime());
        paymentStatus = source.getPaymentStatus().getValue();
        remark = source.getRemark().value;
        weight = String.valueOf(source.getWeight().getWeight());
    }

    /**
     * Converts this Jackson-friendly adapted Schedule object to get its model's {@code Email} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Email.
     */
    public Email getClientEmail() throws IllegalValueException {
        if (clientEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }

        if (!Email.isValidEmail(clientEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        return new Email(clientEmail);
    }

    /**
     * Converts this Jackson-friendly adapted Schedule object to get its model's {@code Interval} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted id.
     */
    public Interval getSessionInterval() throws IllegalValueException {
        if (start == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    START_TIME_FIELD));
        }

        if (SessionParserUtil.isInvalidDateTime(start)) {
            throw new IllegalValueException(MESSAGE_START_TIME_CONSTRAINTS);
        }

        if (end == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    END_TIME_FIELD));
        }

        if (SessionParserUtil.isInvalidDateTime(end)) {
            throw new IllegalValueException(MESSAGE_END_TIME_CONSTRAINTS);
        }

        return SessionParserUtil.parseIntervalFromStartAndEnd(start, end);
    }

    /**
     * Converts this Jackson-friendly adapted Schedule object to get its model's {@code PaymentStatus} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted payment status.
     */
    public PaymentStatus getPaymentStatus() throws IllegalValueException {
        if (paymentStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PaymentStatus.class.getSimpleName()));
        }

        return ScheduleParserUtil.parsePaymentStatus(paymentStatus);
    }

    /**
     * Converts this Jackson-friendly adapted Schedule object to get its model's {@code remark} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark value.
     */
    public Remark getRemark() throws IllegalValueException {
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Remark.class.getSimpleName()));
        }

        return new Remark(remark);
    }

    /**
     * Converts this Jackson-friendly adapted Schedule object to get its model's {@code weight} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted weight value.
     */
    public Weight getWeight() throws IllegalValueException {
        if (weight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Weight.class.getSimpleName()));
        }
        try {
            Double weightDouble = Double.valueOf(weight);
            if (weightDouble == 0) {
                return Weight.getDefaultWeight();
            } else if (weightDouble > 0) {
                return new Weight(weightDouble);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new IllegalValueException(String.format(Weight.MESSAGE_INVALID_WEIGHT_STATUS,
                    Weight.class.getSimpleName()));
        }
    }
}
