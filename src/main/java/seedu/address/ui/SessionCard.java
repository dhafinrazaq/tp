package seedu.address.ui;

import static seedu.address.model.session.Interval.SIMPLE_DATE_TIME_PATTERN_FORMATTER;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;


/**
 * An UI component that displays information of a {@code Client}.
 */
public class SessionCard extends UiPart<Region> {

    private static final String FXML = "SessionListCard.fxml";
    private static final String PAID_CLIENT_CSS_ID = "paidClient";
    private static final String NOT_PAID_CLIENT_CSS_ID = "notPaidClient";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Session session;
    @FXML
    private Label dayOfWeek;
    @FXML
    private Label sessionTime;
    @FXML
    private Label gymName;
    @FXML
    private FlowPane associatedClient;


    /**
     * Creates a {@code ScheduleCard} with the given {@code Schedule}.
     */
    public SessionCard(Session session, int displayedIndex, List<Schedule> schedules) {
        super(FXML);
        this.session = session;
        id.setText(displayedIndex + ". ");
        sessionTime.setText(session.getInterval().getAdjustedStartDateTime());
        gymName.setText(session.getGym().toString());
        dayOfWeek.setText(session.getInterval().getFormattedStartDateTime(SIMPLE_DATE_TIME_PATTERN_FORMATTER));

        if (schedules != null && !schedules.isEmpty()) {
            // Add the Clients' name with the color adjusted depending on whether paid or not
            schedules.forEach(schedule -> {
                Label clientLabel = new Label(schedule.getClientName().toString());

                if (schedule.isPaid()) {
                    clientLabel.setId(PAID_CLIENT_CSS_ID);
                } else {
                    clientLabel.setId(NOT_PAID_CLIENT_CSS_ID);
                }

                associatedClient.getChildren().add(clientLabel);
                }
            );
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SessionCard)) {
            return false;
        }

        // state check
        SessionCard card = (SessionCard) other;
        return session.equals(card.session);
    }
}
