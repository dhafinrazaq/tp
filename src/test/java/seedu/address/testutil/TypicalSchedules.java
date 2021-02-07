package seedu.address.testutil;

import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BENSON;
import static seedu.address.testutil.TypicalSessions.GETWELL;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.schedule.PaymentStatus;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Weight;

public class TypicalSchedules {

    // Manually added - Schedule's details found in {@code ScheduleCommandTestUtil}
    public static final Schedule ALICE_GETWELL = new ScheduleBuilder()
            .withClient(ALICE)
            .withSession(GETWELL)
            .build();

    public static final Schedule BENSON_GETWELL = new ScheduleBuilder()
            .withClient(BENSON)
            .withSession(GETWELL)
            .build();

    public static final Schedule ALICE_MACHOMAN = new ScheduleBuilder()
            .withClient(ALICE)
            .withSession(MACHOMAN)
            .build();

    public static final PaymentStatus PAYMENT_UNPAID = new PaymentStatus("unpaid");
    public static final PaymentStatus PAYMENT_PAID = new PaymentStatus("paid");

    public static final Remark EMPTY_REMARK = Remark.EMPTY_REMARK;
    public static final Remark TEST_REMARK = new Remark("Did 5 pushups");

    public static final Weight DEFAULT_WEIGHT = Weight.getDefaultWeight();
    public static final Weight TEST_WEIGHT = new Weight(58.3);
    public static final Weight TEST_WEIGHT2 = new Weight(47.9);

    private TypicalSchedules() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical Sessions.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Schedule schedule : getTypicalSchedules()) {
            ab.addSchedule(schedule);
        }
        return ab;
    }

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(ALICE_GETWELL, BENSON_GETWELL, ALICE_MACHOMAN));
    }
}
