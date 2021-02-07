package seedu.address.ui;

import static seedu.address.model.util.WeightUnit.getKgInPound;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.session.SessionParserUtil;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.PaymentStatus;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Weight;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Interval;
import seedu.address.model.util.WeightUnit;

/**
 * The Client Info Page. Provides an overview of the client, including personal details
 * schedules related to the client, and a weight progress tracker.
 */
public class ClientInfoPage extends UiPart<AnchorPane> {
    private static final String FXML = "ClientInfoPage.fxml";
    private static final Logger logger = LogsCenter.getLogger(ClientInfoPage.class);
    private static ClientInfoPage currentPage;
    private Client client;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblAddress;

    @FXML
    private FlowPane tags;

    @FXML
    private LineChart<String, Number> weightLineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabWeight;

    @FXML
    private Tab tabSchedule;

    @FXML
    private TableView<Schedule> schedulesToDisplay;

    @FXML
    private TableColumn<Schedule, Interval> intervalColumn;

    @FXML
    private TableColumn<Schedule, ExerciseType> exTypeColumn;

    @FXML
    private TableColumn<Schedule, Remark> remarkColumn;

    @FXML
    private TableColumn<Schedule, PaymentStatus> paymentStatusColumn;

    /**
     * Displays a client's profile in a separate window.
     * It should display all the details pertaining to this {@code Client}.
     *
     * @param client        The client to display.
     * @param associatedSchedules The list of schedules related to the client.
     */
    private ClientInfoPage(Client client, List<Schedule> associatedSchedules, WeightUnit weightUnit) {
        super(FXML);
        this.client = client;

        this.initializeProfile();
        this.initializeSchedule(associatedSchedules);
        this.initializeChart(weightUnit, associatedSchedules);
        currentPage = this;
    }

    private ClientInfoPage() {
        super(FXML);
        this.client = null;
        currentPage = this;
    }

    /**
     * Sets the viewing panel of tabPane using KeyCode.
     */
    public void selectTab(KeyCode key) {
        if (this.client == null) {
            return;
        }
        if (key == KeyCode.F3) {
            this.tabPane.getSelectionModel().select(tabSchedule);
        } else if (key == KeyCode.F4) {
            this.tabPane.getSelectionModel().select(tabWeight);
        }
    }

    /**
     * Gets the current {@code ClientInfoPage} instance.
     *
     * @return the current {@code ClientInfoPage} instance.
     */
    public static ClientInfoPage getCurrentClientInfoPage() {
        if (currentPage == null) {
            currentPage = new ClientInfoPage();
        }
        return currentPage;
    }

    /**
     * Updates the GUI with the latest information about the client
     */
    public void update(Logic logic) {
        if (this.client == null) {
            return;
        }
        Optional<Client> optionalClient = logic.getAddressBook().getClientList().stream()
                .filter(x->x.isIdentical(this.client)).findFirst();
        if (optionalClient.isPresent()) {
            this.client = optionalClient.get();
            initializeProfile();
            initializeSchedule(logic.getAssociatedScheduleList(this.client));
            initializeChart(logic.getPreferredWeightUnit(), logic.getAssociatedScheduleList(this.client));
        }
    }

    public static ClientInfoPage getClientInfoPage(Client client,
                                                   List<Schedule> associatedSchedules,
                                                   WeightUnit weightUnit) {
        return new ClientInfoPage(client, associatedSchedules, weightUnit);
    }

    /**
     * Updates / Set up the UI Profile component.
     */
    private void initializeProfile() {
        this.lblName.setText(client.getName().fullName);
        this.lblPhone.setText(client.getPhone().value);
        this.lblEmail.setText(client.getEmail().value);
        this.lblAddress.setText(client.getAddress().value);
        tags.getChildren().clear();
        client.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        try {
            Image clientProfileImage = retrieveImage(client.getName().fullName);
            if (clientProfileImage.getException() == null) {
                // no exception means client profile image existed
                this.imgProfile.setImage(clientProfileImage);
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            assert false : "Should not reach here";
            logger.info("Invalid image url, using default image\nException: " + e);
        }

    }

    private Image retrieveImage(String fullname) {
        // find image based on client's fullname, in lower case, dashed. Example: Alex Yeoh -> profile-alex-yeoh.jpg
        String sluggedName = String.join("-", fullname.split("\\s+")).toLowerCase();
        return new Image("file:data/images/profile-" + sluggedName + ".jpg");
    }

    /**
     * Updates the line chart with the given schedule list. Schedules with Weight present and associated to the client
     *     will be included into the line chart.
     */
    @SuppressWarnings("unchecked")
    private void initializeChart(WeightUnit weightUnit, List<Schedule> associatedSchedules) {
        //x is date (at the bottom)
        //y is weight (at the left)
        XYChart.Series<String, Number> xy = new XYChart.Series<>();

        List<XYChart.Data<String, Number>> xyList = associatedSchedules.stream()
                .filter(x -> x.getClient().equals(client) && !x.getWeight().equals(Weight.getDefaultWeight()))
                .sorted() //use default comparator
                .map(x -> {
                    XYChart.Data<String, Number> data = new XYChart.Data<>(
                            SessionParserUtil.parseDateTimeToString(x.getSession().getStartTime()),
                            weightUnit.isPoundUnit()
                                    ? getKgInPound(x.getWeight().getWeight())
                                    : x.getWeight().getWeight());
                    return data;
                })
                .collect(Collectors.toList());

        if (xyList.size() > 0) {
            xy.getData().addAll(xyList);
            weightLineChart.getData().setAll(xy);
            yAxis.setAutoRanging(false);
            yAxis.setLabel(weightUnit.toString());
            int lowerBound = xyList.stream().mapToInt(x -> (int) x.getYValue().doubleValue()).min().getAsInt() - 3;
            int upperBound = xyList.stream().mapToInt(x -> (int) x.getYValue().doubleValue()).max().getAsInt() + 3;

            yAxis.setLowerBound(lowerBound);
            yAxis.setUpperBound(upperBound);
            yAxis.setTickUnit(1);
            if (!tabPane.getTabs().contains(tabWeight)) {
                tabPane.getTabs().add(tabWeight);
            }
        } else {
            tabPane.getTabs().remove(tabWeight);
        }
    }

    private void initializeSchedule(List<Schedule> associatedSchedules) {
        Collections.sort(associatedSchedules);
        Collections.reverse(associatedSchedules);

        this.schedulesToDisplay.setPlaceholder(new Label("No schedules to display"));
        this.schedulesToDisplay.setPrefHeight(250);

        intervalColumn.setCellValueFactory(new PropertyValueFactory<>("interval"));
        exTypeColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseType"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        remarkColumn.setSortable(false);

        // remove previous items (if exists) and add new objects after update
        this.schedulesToDisplay.getItems().clear();
        this.schedulesToDisplay.getItems().addAll(associatedSchedules);

        // Make remark column can wrap text
        remarkColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Schedule, Remark> call(TableColumn<Schedule, Remark> arg0) {
                return new TableCell<>() {
                    private Text text;

                    @Override
                    public void updateItem(Remark item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item.toString());
                            text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
                            text.setStyle("-fx-stroke: white; -fx-stroke-width: 0.5; -fx-padding: 10px;");
                            text.setFont(Font.font("Segoe UI Light"));
                            this.setWrapText(true);

                            setGraphic(text);
                        }
                    }
                };
            }
        });

        // Make payment column in red on paid and green on not paid
        paymentStatusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Schedule, PaymentStatus> call(TableColumn<Schedule, PaymentStatus> arg0) {
                return new TableCell<>() {
                    private Text text;

                    @Override
                    public void updateItem(PaymentStatus item, boolean isEmpty) {
                        super.updateItem(item, isEmpty);
                        if (!isEmpty()) {
                            text = new Text(item.toString());

                            if (item.isPaid()) {
                                text.setStyle("-fx-stroke: #00FF00; -fx-stroke-width: 0.5; -fx-padding: 10px;");
                            } else {
                                text.setStyle("-fx-stroke: #FF0000; -fx-stroke-width: 0.5; -fx-padding: 10px;");
                            }
                            text.setFont(Font.font("Segoe UI Light"));

                            setGraphic(text);
                        }
                    }
                };
            }
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientInfoPage)) {
            return false;
        }

        // state check
        ClientInfoPage card = (ClientInfoPage) other;
        return client.equals(card.client);
    }
}
