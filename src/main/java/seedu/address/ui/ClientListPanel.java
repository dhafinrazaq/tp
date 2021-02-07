package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.client.Client;
import seedu.address.model.session.Session;

/**
 * Panel containing the list of Clients.
 */
public class ClientListPanel extends UiPart<Region> {
    private static final String FXML = "ClientListPanel.fxml";
    public final Logic logic;
    private final MainWindow mainWindow;

    @FXML
    private ListView<Client> clientListView;

    /**
     * Creates a {@code ClientListPanel} with the given {@code ObservableList}.
     */
    public ClientListPanel(MainWindow mainWindow, Logic logic) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.logic = logic;
        clientListView.setItems(logic.getFilteredClientList());
        clientListView.setCellFactory(listView -> new ClientListViewCell());
    }

    /**
     * Updates the content of the Client ListView
     */
    public void update() {
        clientListView.setItems(null);
        clientListView.setItems(logic.getFilteredClientList());
        clientListView.setCellFactory(listView -> new ClientListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Client} using a {@code ClientCard}.
     */
    class ClientListViewCell extends ListCell<Client> {
        @Override
        protected void updateItem(Client client, boolean empty) {
            super.updateItem(client, empty);

            if (empty || client == null) {
                setGraphic(null);
                setText(null);
            } else {
                List<Session> associatedSessions = logic.getAssociatedSessionList(client);
                List<Session> sortedUpcomingSessions = associatedSessions
                        .stream()
                        .filter(s -> !s.getStartTime().isBefore(LocalDateTime.now()))
                        .sorted()
                        .collect(Collectors.toList());
                if (sortedUpcomingSessions.size() > 0) {
                    setGraphic(new ClientCard(client, getIndex() + 1, sortedUpcomingSessions.get(0)).getRoot());
                } else {
                    setGraphic(new ClientCard(client, getIndex() + 1, null).getRoot());
                }
            }
        }
    }

    /**
     * When the user click on any profile in ListView, it will display it on the Main GUI.
     */
    public void onMouseClicked_displayClientInfo() {
        Client toView = clientListView.getFocusModel().getFocusedItem();
        ClientInfoPage cip = ClientInfoPage.getClientInfoPage(toView, logic.getAssociatedScheduleList(toView),
                logic.getPreferredWeightUnit());
        mainWindow.setMainDisplay(cip.getRoot());
    }
}
