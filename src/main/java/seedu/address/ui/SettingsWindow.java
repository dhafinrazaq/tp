package seedu.address.ui;

import static seedu.address.model.util.WeightUnit.KILOGRAM;
import static seedu.address.model.util.WeightUnit.POUND;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.util.WeightUnit;


/**
 * Controller for a help page
 */
public class SettingsWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE = "Please select your preferred unit of mass: ";
    private static final ToggleGroup group = new ToggleGroup();

    private static final Logger logger = LogsCenter.getLogger(SettingsWindow.class);
    private static final String FXML = "SettingsWindow.fxml";

    private List<UiObserver> observerList = new ArrayList<>();

    @FXML
    private RadioButton kgRadio;

    @FXML
    private RadioButton poundRadio;

    @FXML
    private Label helpMessage;
    /**
     * Creates a new Settings.
     *
     * @param root Stage to use as the root of the Settings.
     */
    public SettingsWindow(Stage root, Logic logic) {
        super(FXML, root);

        this.kgRadio.setToggleGroup(this.group);
        this.kgRadio.setUserData(KILOGRAM);
        this.kgRadio.setText(KILOGRAM);

        this.poundRadio.setToggleGroup(group);
        this.poundRadio.setUserData(POUND);
        this.poundRadio.setText(POUND);

        // set the default radio selected option to preferredWeightUnit from userPrefs
        if (logic.getPreferredWeightUnit().isPoundUnit()) {
            this.poundRadio.setSelected(true);
        } else {
            this.kgRadio.setSelected(true);
        }

        // watch for changes to radio selected option then update userPrefs
        this.group.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            logic.setPreferredWeightUnit(new WeightUnit(group.getSelectedToggle().getUserData().toString()));
            notifyUIs();
        });

        //Create an event on ESC click, it will close this window
        root.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
            if (k.getCode() == KeyCode.ESCAPE) {
                root.close();
            }
        });
    }

    /**
     * Creates a new SettingsWindow.
     */
    public SettingsWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Shows the settings window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing settings window of the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the settings window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Adds an observer to the set of observers for this object
     * @param o an observer to be added.
     */
    public void addUi(UiObserver o) {
        assert(o != null);
        observerList.add(o);
    }

    /**
     * If this is called, then notify and update all of its observers.
     */
    public void notifyUIs() {
        for (UiObserver o: observerList) {
            o.update();
        }
    }
}
