package seedu.address.ui;

import static seedu.address.logic.commands.session.ViewSessionCommand.MESSAGE_SHOW_SESSIONS_SUCCESS;
import static seedu.address.logic.commands.session.ViewSessionCommand.PREDICATE_SHOW_UPCOMING_WEEK_SESSIONS;
import static seedu.address.logic.parser.session.CliSyntax.PREFIX_PERIOD;

import java.util.List;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;

public class RightSideBar extends UiPart<AnchorPane> {
    private static final String FXML = "RightSideBar.fxml";
    private static final String DEFAULT_VIEW = "WEEK";
    private static final Predicate<Session> DEFAULT_PREDICATE = PREDICATE_SHOW_UPCOMING_WEEK_SESSIONS;
    private final MainWindow mainWindow;
    private final Logic logic;
    private String latestPeriod;

    @FXML
    private ListView<Session> sessionListView;

    @FXML
    private Label title;

    /**
     * Creates a {@code RightSideBar} with the given {@code ObservableList}.
     */
    public RightSideBar(MainWindow mainWindow, Logic logic) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.logic = logic;
        this.latestPeriod = DEFAULT_VIEW;
        this.title.setAlignment(Pos.CENTER);
        sessionListView.setItems(logic.getFilteredSessionList());
        sessionListView.setCellFactory(listView -> new RightSideBar.SessionListViewCell());

        updateTitle();
        logic.updateFilteredSessionList(DEFAULT_PREDICATE);
    }

    /**
     * Updates the title and content of the Session ListView.
     */
    public void update(CommandResult commandResult, String commandText) {
        updateLatestPeriod(commandResult, commandText);
        updateTitle();

        sessionListView.setItems(null);
        sessionListView.setItems(logic.getFilteredSessionList());
        sessionListView.setCellFactory(listView -> new SessionListViewCell());
    }

    private void updateTitle() {
        title.setText(latestPeriod);
    }

    /**
     * Updates {@code latestPeriod} if {@code commandResult} is from a successful {@code ViewSessionCommand}.
     * Do nothing otherwise.
     *
     * @param commandResult the command result to be checked
     * @param commandText the command input from the user
     */
    private void updateLatestPeriod(CommandResult commandResult, String commandText) {
        assert(commandResult != null && commandText != null);
        int startOfPeriod = commandText.indexOf(PREFIX_PERIOD.toString());
        if (startOfPeriod == -1) {
            return;
        }
        String period = commandText.substring(startOfPeriod + 2);
        boolean isSuccessfulViewSessionCommand = commandResult.getFeedbackToUser().equals(
                String.format(MESSAGE_SHOW_SESSIONS_SUCCESS, period));
        if (!isSuccessfulViewSessionCommand) {
            return;
        }
        this.latestPeriod = period.toUpperCase();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code SessionCard}.
     */
    class SessionListViewCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);

            if (empty || session == null) {
                setGraphic(null);
                setText(null);
            } else {
                List<Schedule> associatedSchedules = logic.getAssociatedScheduleList(session);
                int zeroBasedIndex = getIndex();
                int oneBasedIndex = zeroBasedIndex + 1;

                if (!associatedSchedules.isEmpty()) {
                    setGraphic(new SessionCard(session, oneBasedIndex, associatedSchedules).getRoot());
                } else {
                    setGraphic(new SessionCard(session, oneBasedIndex, null).getRoot());
                }
            }
        }
    }
}
