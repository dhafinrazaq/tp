package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> implements UiObserver {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private List<String> pastCommandList;
    private int pastCommandListCursor;

    // Independent Ui parts residing in this Ui container
    private Homepage homepage;
    private ClientListPanel clientListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private SettingsWindow settingsWindow;
    private RightSideBar rightSideBar;
    private StatusBarFooter statusBarFooter;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem settingsMenuItem;

    @FXML
    private StackPane clientListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Pane mainDisplay;

    @FXML
    private Pane rightDisplay;

    @FXML
    private ColumnConstraints gPaneLeft;

    @FXML
    private ColumnConstraints gPaneCentre;

    @FXML
    private ColumnConstraints gPaneRight;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.pastCommandList = new ArrayList<>();
        this.pastCommandListCursor = -1;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        setupKeypressHandlers();

        helpWindow = new HelpWindow();
        settingsWindow = new SettingsWindow(logic);
        settingsWindow.addUi(this);

        addDynamicGridPaneChange(primaryStage.getScene());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(settingsMenuItem, KeyCombination.valueOf("F2"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Sets up key press event handlers.
     */
    private void setupKeypressHandlers() {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
            if (k.getCode() == KeyCode.F3 || k.getCode() == KeyCode.F4) {
                ClientInfoPage.getCurrentClientInfoPage().selectTab(k.getCode());
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        clientListPanel = new ClientListPanel(this, logic);
        clientListPanelPlaceholder.getChildren().add(clientListPanel.getRoot());

        homepage = Homepage.getHomePage(this.logic.getAddressBook());
        setMainDisplay(homepage.getRoot());

        rightSideBar = new RightSideBar(this, logic);
        rightDisplay.getChildren().add(rightSideBar.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this, this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the settings window or focuses on it if it's already opened.
     */
    @FXML
    public void handleSettings() {
        if (!settingsWindow.isShowing()) {
            settingsWindow.show();
        } else {
            settingsWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        settingsWindow.hide();
        primaryStage.hide();
    }


    public List<String> getPastCommandList() {
        return this.pastCommandList;
    }

    public int getPastCommandListCursor() {
        return this.pastCommandListCursor;
    }

    public void setPastCommandListCursor(int cursor) {
        this.pastCommandListCursor = cursor;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            statusBarFooter.setDisplayString("Executing: " + commandText);
            pastCommandList.add(commandText);
            pastCommandListCursor = pastCommandList.size();

            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowSettings()) {
                handleSettings();
            }

            if (commandResult.hasFunctionToRun()) {
                setMainDisplay(commandResult.getPane().get());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            ClientInfoPage.getCurrentClientInfoPage().update(logic);
            clientListPanel.update();
            rightSideBar.update(commandResult, commandText);
            homepage.update();
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Sets the main gui.
     * Note: Only use this to set main display, so that it can bind to the entire screen.
     *
     * @param display The Pane to display
     */
    public void setMainDisplay(Pane display) {
        mainDisplay.getChildren().clear();
        AnchorPane.setTopAnchor(display, 0.0);
        AnchorPane.setBottomAnchor(display, 0.0);
        AnchorPane.setLeftAnchor(display, 0.0);
        AnchorPane.setRightAnchor(display, 0.0);
        mainDisplay.getChildren().add(display);
    }

    /**
     * Adds listener to width property such that if it is below a certain px, it will change to 2-grid mode.
     * <900px (2 grid); >=900 (3 grid)
     *
     * @param scene the scene which listener is added to.
     */
    private void addDynamicGridPaneChange(Scene scene) {
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {

            //if width < 900, change to 2 grid-style
            if (newWidth.doubleValue() < 900.0 && oldWidth.doubleValue() >= 900.0) {
                this.gPaneRight.setPercentWidth(0);
                this.gPaneCentre.setPercentWidth(75);
            } else if (newWidth.doubleValue() >= 900.0 && oldWidth.doubleValue() < 900) {
                this.gPaneCentre.setPercentWidth(60);
                this.gPaneRight.setPercentWidth(15);
            }
        });
    }

    /**
     * This method is called whenever the observed object is changed.
     */
    public void update() {
        ClientInfoPage.getCurrentClientInfoPage().update(logic);
        clientListPanel.update();
        homepage.update();
    }
}
