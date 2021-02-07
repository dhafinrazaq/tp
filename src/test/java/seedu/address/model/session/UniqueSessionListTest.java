package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_DURATION_GETWELL;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_EXERCISE_TYPE_MACHOMAN;
import static seedu.address.logic.commands.session.SessionCommandTestUtil.VALID_START_TIME_GETWELL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSessions.GETWELL;
import static seedu.address.testutil.TypicalSessions.MACHOMAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.UniqueList;
import seedu.address.model.exceptions.DuplicateEntityException;
import seedu.address.model.exceptions.EntityNotFoundException;
import seedu.address.testutil.SessionBuilder;

public class UniqueSessionListTest {

    private final UniqueList<Session> uniqueSessionList = new UniqueList<>();

    @Test
    public void contains_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.contains(null));
    }

    @Test
    public void contains_sessionNotInList_returnsFalse() {
        assertFalse(uniqueSessionList.contains(GETWELL));
    }

    @Test
    public void contains_sessionInList_returnsTrue() {
        uniqueSessionList.add(GETWELL);
        assertTrue(uniqueSessionList.contains(GETWELL));
    }

    @Test
    public void contains_sessionWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSessionList.add(GETWELL);
        Session editedGetwell = new SessionBuilder(GETWELL)
                .withInterval(VALID_START_TIME_GETWELL, VALID_DURATION_GETWELL)
                .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN)
                .build();
        assertTrue(uniqueSessionList.contains(editedGetwell));
    }

    @Test
    public void add_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.add(null));
    }

    @Test
    public void add_duplicateSession_throwsDuplicateEntityException() {
        uniqueSessionList.add(GETWELL);
        assertThrows(DuplicateEntityException.class, () -> uniqueSessionList.add(GETWELL));
    }

    @Test
    public void set_nullTargetSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.set(null, GETWELL));
    }

    @Test
    public void set_nullEditedSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.set(GETWELL, null));
    }

    @Test
    public void set_targetSessionNotInList_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueSessionList.set(GETWELL, GETWELL));
    }

    @Test
    public void set_editedSessionIsSameSession_success() {
        uniqueSessionList.add(GETWELL);
        uniqueSessionList.set(GETWELL, GETWELL);
        UniqueList<Session> expectedUniqueSessionList = new UniqueList<>();
        expectedUniqueSessionList.add(GETWELL);
        assertEquals(expectedUniqueSessionList, uniqueSessionList);
    }

    @Test
    public void set_editedSessionHasSameIdentity_success() {
        uniqueSessionList.add(GETWELL);
        Session editedGetwell = new SessionBuilder(GETWELL)
                .withInterval(VALID_START_TIME_GETWELL, VALID_DURATION_GETWELL)
                .withExerciseType(VALID_EXERCISE_TYPE_MACHOMAN)
                .build();
        uniqueSessionList.set(GETWELL, editedGetwell);
        UniqueList<Session> expectedUniqueSessionList = new UniqueList<>();
        expectedUniqueSessionList.add(editedGetwell);
        assertEquals(expectedUniqueSessionList, uniqueSessionList);
    }

    @Test
    public void set_editedSessionHasDifferentIdentity_success() {
        uniqueSessionList.add(GETWELL);
        uniqueSessionList.set(GETWELL, MACHOMAN);
        UniqueList<Session> expectedUniqueSessionList = new UniqueList<>();
        expectedUniqueSessionList.add(MACHOMAN);
        assertEquals(expectedUniqueSessionList, uniqueSessionList);
    }

    @Test
    public void set_editedSessionHasNonUniqueIdentity_throwsDuplicateEntityException() {
        uniqueSessionList.add(GETWELL);
        uniqueSessionList.add(MACHOMAN);
        assertThrows(DuplicateEntityException.class, () -> uniqueSessionList.set(GETWELL, MACHOMAN));
    }

    @Test
    public void remove_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.remove(null));
    }

    @Test
    public void remove_sessionDoesNotExist_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueSessionList.remove(GETWELL));
    }

    @Test
    public void remove_existingSession_removesSession() {
        uniqueSessionList.add(GETWELL);
        uniqueSessionList.remove(GETWELL);
        UniqueList<Session> expectedUniqueSessionList = new UniqueList<>();
        assertEquals(expectedUniqueSessionList, uniqueSessionList);
    }

    @Test
    public void sets_nullUniqueSessionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.setAll((UniqueList<Session>) null));
    }

    @Test
    public void sets_uniqueSessionList_replacesOwnListWithProvidedUniqueSessionList() {
        uniqueSessionList.add(GETWELL);
        UniqueList<Session> expectedUniqueSessionList = new UniqueList<>();
        expectedUniqueSessionList.add(MACHOMAN);
        uniqueSessionList.setAll(expectedUniqueSessionList);
        assertEquals(expectedUniqueSessionList, uniqueSessionList);
    }

    @Test
    public void sets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSessionList.setAll((List<Session>) null));
    }

    @Test
    public void sets_list_replacesOwnListWithProvidedList() {
        uniqueSessionList.add(GETWELL);
        List<Session> sessionList = Collections.singletonList(MACHOMAN);
        uniqueSessionList.setAll(sessionList);
        UniqueList<Session> expectedUniqueSessionList = new UniqueList<>();
        expectedUniqueSessionList.add(MACHOMAN);
        assertEquals(expectedUniqueSessionList, uniqueSessionList);
    }

    @Test
    public void sets_listWithDuplicateSessions_throwsDuplicateEntityException() {
        List<Session> listWithDuplicateSessions = Arrays.asList(GETWELL, GETWELL);
        assertThrows(DuplicateEntityException.class, () -> uniqueSessionList.setAll(listWithDuplicateSessions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueSessionList
                .asUnmodifiableObservableList().remove(0));
    }
}

