package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_TAG_INJURY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.UniqueList;
import seedu.address.model.exceptions.DuplicateEntityException;
import seedu.address.model.exceptions.EntityNotFoundException;
import seedu.address.testutil.ClientBuilder;

public class UniqueClientListTest {

    private final UniqueList<Client> uniqueClientList = new UniqueList<>();

    @Test
    public void contains_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.contains(null));
    }

    @Test
    public void contains_clientNotInList_returnsFalse() {
        assertFalse(uniqueClientList.contains(ALICE));
    }

    @Test
    public void contains_clientInList_returnsTrue() {
        uniqueClientList.add(ALICE);
        assertTrue(uniqueClientList.contains(ALICE));
    }

    @Test
    public void contains_clientWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClientList.add(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_INJURY)
                .build();
        assertTrue(uniqueClientList.contains(editedAlice));
    }

    @Test
    public void add_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.add(null));
    }

    @Test
    public void add_duplicateClient_throwsDuplicateEntityException() {
        uniqueClientList.add(ALICE);
        assertThrows(DuplicateEntityException.class, () -> uniqueClientList.add(ALICE));
    }

    @Test
    public void set_nullTargetClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.set(null, ALICE));
    }

    @Test
    public void set_nullEditedClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.set(ALICE, null));
    }

    @Test
    public void set_targetClientNotInList_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueClientList.set(ALICE, ALICE));
    }

    @Test
    public void set_editedClientIsSameClient_success() {
        uniqueClientList.add(ALICE);
        uniqueClientList.set(ALICE, ALICE);
        UniqueList<Client> expectedUniqueClientList = new UniqueList<>();
        expectedUniqueClientList.add(ALICE);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void set_editedClientHasSameIdentity_success() {
        uniqueClientList.add(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_INJURY)
                .build();
        uniqueClientList.set(ALICE, editedAlice);
        UniqueList<Client> expectedUniqueClientList = new UniqueList<>();
        expectedUniqueClientList.add(editedAlice);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void set_editedClientHasDifferentIdentity_success() {
        uniqueClientList.add(ALICE);
        uniqueClientList.set(ALICE, BOB);
        UniqueList<Client> expectedUniqueClientList = new UniqueList<>();
        expectedUniqueClientList.add(BOB);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void set_editedClientHasNonUniqueIdentity_throwsDuplicateEntityException() {
        uniqueClientList.add(ALICE);
        uniqueClientList.add(BOB);
        assertThrows(DuplicateEntityException.class, () -> uniqueClientList.set(ALICE, BOB));
    }

    @Test
    public void remove_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.remove(null));
    }

    @Test
    public void remove_clientDoesNotExist_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueClientList.remove(ALICE));
    }

    @Test
    public void remove_existingClient_removesClient() {
        uniqueClientList.add(ALICE);
        uniqueClientList.remove(ALICE);
        UniqueList<Client> expectedUniqueClientList = new UniqueList<>();
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void sets_nullUniqueClientList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setAll((UniqueList<Client>) null));
    }

    @Test
    public void sets_uniqueClientList_replacesOwnListWithProvidedUniqueClientList() {
        uniqueClientList.add(ALICE);
        UniqueList<Client> expectedUniqueClientList = new UniqueList<>();
        expectedUniqueClientList.add(BOB);
        uniqueClientList.setAll(expectedUniqueClientList);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void sets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setAll((List<Client>) null));
    }

    @Test
    public void sets_list_replacesOwnListWithProvidedList() {
        uniqueClientList.add(ALICE);
        List<Client> clientList = Collections.singletonList(BOB);
        uniqueClientList.setAll(clientList);
        UniqueList<Client> expectedUniqueClientList = new UniqueList<>();
        expectedUniqueClientList.add(BOB);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void sets_listWithDuplicateClients_throwsDuplicateEntityException() {
        List<Client> listWithDuplicateClients = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateEntityException.class, () -> uniqueClientList.setAll(listWithDuplicateClients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueClientList.asUnmodifiableObservableList().remove(0));
    }
}
