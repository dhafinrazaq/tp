package seedu.address.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.session.ViewSessionCommand.MESSAGE_SHOW_SESSIONS_SUCCESS;
import static seedu.address.logic.commands.session.ViewSessionCommand.VALID_ALL_SESSIONS_PERIOD;
import static seedu.address.logic.commands.session.ViewSessionCommand.VALID_FUTURE_SESSIONS_PERIOD;
import static seedu.address.logic.commands.session.ViewSessionCommand.VALID_WEEK_SESSIONS_PERIOD;
import static seedu.address.testutil.TypicalSessions.GETWELL;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;
import static seedu.address.testutil.TypicalSessions.MACHOMAN_MINUS1WEEK;
import static seedu.address.testutil.TypicalSessions.MACHOMAN_PLUS2MONTHS;
import static seedu.address.testutil.TypicalSessions.MACHOMAN_TODAY;
import static seedu.address.testutil.TypicalSessions.MACHOMAN_TOMORROW;
import static seedu.address.testutil.TypicalSessions.getTypicalWithDayAfterAddressBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

public class ViewSessionCommandTest {

    private Model model = new ModelManager(getTypicalWithDayAfterAddressBook(), new UserPrefs());

    //TODO: Once the default Session List view has been changed to Week, update test case to verify.
    @Test
    public void execute_defaultSessionListIsAll_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand(VALID_ALL_SESSIONS_PERIOD);

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        ModelManager expectedModel = new ModelManager(getTypicalWithDayAfterAddressBook(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewFutureSessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand(VALID_FUTURE_SESSIONS_PERIOD);

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_TODAY);
        addressBookStub.withSession(MACHOMAN_TOMORROW);
        addressBookStub.withSession(MACHOMAN_PLUS2MONTHS);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewAllSessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand(VALID_ALL_SESSIONS_PERIOD);

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_TODAY);
        addressBookStub.withSession(MACHOMAN_TOMORROW);
        addressBookStub.withSession(MACHOMAN_PLUS2MONTHS);
        addressBookStub.withSession(MACHOMAN_MINUS1WEEK);
        addressBookStub.withSession(MACHOMAN);
        addressBookStub.withSession(GETWELL);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewPlus0DaySessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand("+0D"); //today

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_TODAY);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewMinus0DaySessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand("-0D"); //today

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_TODAY);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewPlus1DaySessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand("+1D"); //today & tomorrow

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_TODAY);
        addressBookStub.withSession(MACHOMAN_TOMORROW);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewMinus1WeekSessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand("-1w");

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_MINUS1WEEK);
        addressBookStub.withSession(MACHOMAN_TODAY);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewPlus2MonthsSessions_success() {
        ViewSessionCommand viewSessionCommand = new ViewSessionCommand("+2M");

        String expectedMessage = MESSAGE_SHOW_SESSIONS_SUCCESS;
        AddressBookBuilder addressBookStub = new AddressBookBuilder();
        addressBookStub.withSession(MACHOMAN_TODAY);
        addressBookStub.withSession(MACHOMAN_TOMORROW);
        addressBookStub.withSession(MACHOMAN_PLUS2MONTHS);
        ModelManager expectedModel = new ModelManager(addressBookStub.build(), new UserPrefs());

        assertCommandSuccess(viewSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewInvalidUnit_thrownAssertionError() {
        try {
            ViewSessionCommand viewSessionCommand = new ViewSessionCommand("+2s");
            viewSessionCommand.execute(model);
            Assertions.fail();
        } catch (AssertionError e) {
            Assertions.assertEquals(new AssertionError().getMessage(), e.getMessage());
        }
    }

    @Test
    public void equals() {
        ViewSessionCommand viewWeekCommand = new ViewSessionCommand(VALID_WEEK_SESSIONS_PERIOD);
        ViewSessionCommand viewAllCommand = new ViewSessionCommand(VALID_ALL_SESSIONS_PERIOD);

        // same object -> returns true
        assertTrue(viewWeekCommand.equals(viewWeekCommand));

        // same values -> returns true
        ViewSessionCommand viewWeekCommandCopy = new ViewSessionCommand(VALID_WEEK_SESSIONS_PERIOD);
        assertTrue(viewWeekCommand.equals(viewWeekCommandCopy));

        // different types -> returns false
        assertFalse(viewWeekCommand.equals(1));

        // null -> returns false
        assertFalse(viewWeekCommand.equals(null));

        // different Session -> returns false
        assertFalse(viewWeekCommand.equals(viewAllCommand));
    }


}