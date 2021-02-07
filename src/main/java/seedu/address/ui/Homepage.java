package seedu.address.ui;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

public class Homepage extends UiPart<AnchorPane> {
    private static final String FXML = "Homepage.fxml";
    private static Homepage homepage;
    private ReadOnlyAddressBook addressBook;

    @FXML
    private GridPane gridpane;

    @FXML
    private ListView<Schedule> todaySchedule;

    @FXML
    private Label lblCurrentlyDetails;

    @FXML
    private Label lblContent;

    @FXML
    private Label lblQotd;

    private Homepage(ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.addressBook = addressBook;
    }

    /**
     * Initialise a Singleton Homepage GUI with an addressbook
     *
     * @param addressBook Address Book that this homepage reference from.
     */
    public static Homepage getHomePage(ReadOnlyAddressBook addressBook) {
        if (Homepage.homepage == null) {
            Homepage.homepage = new Homepage(addressBook);
        }
        return getHomePage();
    }

    /**
     * Gets the Singleton Homepage GUI. To get homepage successfully, it must be initialised
     *     first with an addressbook
     */
    public static Homepage getHomePage() {
        homepage.update();
        return homepage;
    }

    /**
     * Updates the different components within this Ui.
     */
    public void update() {
        updateStatistics();
        updateContent();
        updateQotd();
    }

    /**
     * Updates the Statistics shown in Homepage
     */
    private void updateStatistics() {
        assert(Homepage.homepage != null);
        this.addressBook = addressBook;
        int clients = addressBook.getClientList().size();
        int sessions = addressBook.getSessionList().size();
        int schedule = addressBook.getScheduleList().size();
        String display = String.format("%-10d Clients\n%-10d Sessions \n%-10d Schedules", clients, sessions, schedule);
        this.lblCurrentlyDetails.setText(display);
    }

    /**
     * Updates the content shown in Homepage
     */
    private void updateContent() {
        assert(Homepage.homepage != null);
        this.lblContent.setText("Today's Schedule - "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE dd MMMM")));
        todaySchedule.setItems(getTodaySchedule(addressBook.getScheduleList()));
        todaySchedule.setPlaceholder(new Label("There are no schedules assigned today!"));
        todaySchedule.setPrefHeight(250);
        todaySchedule.setCellFactory(listView -> new Homepage.ScheduleListViewCell());
    }

    private ObservableList<Schedule> getTodaySchedule(ObservableList<Schedule> scheduleList) {
        Interval interval = new Interval(LocalDateTime.now().truncatedTo(DAYS), 60 * 24); //full day interval
        Session overlappingSession = new Session(new Gym("temp"), new ExerciseType("temp"), interval);
        List<Schedule> updatedList = scheduleList.stream()
                .filter(x -> x.getSession().isIdentical(overlappingSession))
                .sorted()
                .collect(Collectors.toList());
        return FXCollections.observableList(updatedList);
    }

    /**
     * Updates the Quote of the day shown in homepage
     */
    private void updateQotd() {
        this.lblQotd.setText("Quote of the day:\nDo Something Today That Your Future Self Will Thank You For");
    }

    class ScheduleListViewCell extends ListCell<Schedule> {
        @Override
        protected void updateItem(Schedule schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label lbl = new Label();
                lbl.setText(schedule.getClient().getName() + "\n" + schedule.getSession().getGym() + "\n"
                        + schedule.getSession().getInterval().toString());
                lbl.setStyle("-fx-padding:10px;");
                setGraphic(lbl);
            }
        }
    }
}
