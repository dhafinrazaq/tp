package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.schedule.EditScheduleTestUtil.DESC_SCHA;
import static seedu.address.logic.commands.schedule.EditScheduleTestUtil.DESC_SCHB;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.schedule.ScheduleCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClients;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SESSION;
import static seedu.address.testutil.TypicalSchedules.BENSON_GETWELL;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_PAID;
import static seedu.address.testutil.TypicalSchedules.PAYMENT_UNPAID;
import static seedu.address.testutil.TypicalSchedules.TEST_REMARK;
import static seedu.address.testutil.TypicalSchedules.getTypicalSchedules;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;
import static seedu.address.testutil.TypicalSessions.getTypicalSessions;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.session.Session;
import seedu.address.testutil.EditScheduleDescriptorBuilder;
import seedu.address.testutil.ScheduleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditClientCommand.
 */
public class EditScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Schedule schedule : getTypicalSchedules()) {
            ab.addSchedule(schedule);
        }
        for (Session session : getTypicalSessions()) {
            ab.addSession(session);
        }
        for (Client client : getTypicalClients()) {
            ab.addClient(client);
        }
        return ab;
    }

    /**
     * Updating BENSON-GETWELL -> BENSON-MACHOMAN should succeed because no BENSON-MACHOMAN exists in typicalAddressBook
     */
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Schedule editedSchedule = new ScheduleBuilder(BENSON_GETWELL)
                .withSession(MACHOMAN)
                .withPaymentStatus(PAYMENT_UNPAID)
                .withRemark(TEST_REMARK)
                .build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withUpdatedSessionIndex(INDEX_SECOND_SESSION)
                .withUpdatedPaymentStatus(PAYMENT_UNPAID)
                .withUpdatedRemark(TEST_REMARK)
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_SECOND_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(1), editedSchedule);

        assertCommandSuccess(editScheduleCommand, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredScheduleList().get(1).getRemark(), TEST_REMARK);
        assertEquals(model.getFilteredScheduleList().get(1).getPaymentStatus(), PAYMENT_UNPAID);
    }

    /**
     * Updating ALICE-GETWELL -> ALICE-ULTRAMAN should succeed because no ALICE-ULTRAMAN exists in typicalAddressBook
     */
    @Test
    public void execute_someFieldsSpecifiedFilteredList_success() {
        Schedule lastSchedule = model.getFilteredScheduleList().get(0);

        ScheduleBuilder scheduleInList = new ScheduleBuilder();
        Client lastClient = model.getFilteredClientList().get(0);
        Session lastSession = model.getFilteredSessionList().get(2);
        Schedule editedSchedule = scheduleInList
                .withClient(lastClient)
                .withSession(lastSession)
                .build();

        Index indexFirstClient = Index.fromOneBased(1);
        Index indexFirstSession = Index.fromOneBased(1);
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withClientIndex(INDEX_FIRST_CLIENT)
                .withSessionIndex(INDEX_FIRST_SESSION)
                .withUpdatedSessionIndex(INDEX_THIRD_SESSION)
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(indexFirstClient, indexFirstSession,
                descriptor);
        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSchedule(lastSchedule, editedSchedule);

        assertCommandSuccess(editScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditScheduleDescriptor editScheduleDescriptor = new EditScheduleDescriptor();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                editScheduleDescriptor);
        Schedule editedSchedule = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Client lastClient = model.getFilteredClientList().get(0);
        Session lastSession = model.getFilteredSessionList().get(2);

        Schedule scheduleInFilteredList = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        Schedule editedSchedule = new ScheduleBuilder(scheduleInFilteredList)
                .withClient(lastClient)
                .withSession(lastSession)
                .withPaymentStatus(PAYMENT_PAID)
                .withRemark(TEST_REMARK)
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                new EditScheduleDescriptorBuilder()
                        .withClientIndex(INDEX_SECOND_CLIENT)
                        .withSessionIndex(INDEX_FIRST_SESSION)
                        .withUpdatedSessionIndex(INDEX_THIRD_SESSION)
                        .withUpdatedPaymentStatus(PAYMENT_PAID)
                        .withUpdatedRemark(TEST_REMARK)
                        .build());

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertCommandSuccess(editScheduleCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Updating ALICE-GETWELL -> ALICE-MACHOMAN should fail because ALICE-MACHOMAN exists in typicalAddressBook
     */
    @Test
    public void execute_duplicateScheduleUnfilteredList_failure() {
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withUpdatedSessionIndex(INDEX_SECOND_SESSION)
                .withUpdatedPaymentStatus(PAYMENT_PAID)
                .withUpdatedRemark(TEST_REMARK)
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_DUPLICATE_SCHEDULE, editScheduleCommand);

        ScheduleCommandTestUtil.assertCommandFailure(editScheduleCommand, model, expectedMessage);
    }

    /**
     * Updating ALICE-GETWELL should fail due to {@code outOfBoundIndex}
     */
    @Test
    public void execute_invalidUpdatedSessionIndexFilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredScheduleList().size() + 1);
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withSessionIndex(INDEX_FIRST_SESSION).withUpdatedSessionIndex(outOfBoundIndex).build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                descriptor);

        assertCommandFailure(editScheduleCommand, model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditScheduleCommand standardCommand = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                DESC_SCHA);

        // same values -> returns true
        EditScheduleDescriptor copyDescriptor = new EditScheduleDescriptor(DESC_SCHA);
        EditScheduleCommand commandWithSameValues = new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_THIRD_SESSION,
                 DESC_SCHA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditScheduleCommand(INDEX_FIRST_CLIENT, INDEX_FIRST_SESSION,
                DESC_SCHB)));
    }

}
