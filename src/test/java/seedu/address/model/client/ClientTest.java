package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.client.ClientCommandTestUtil.VALID_TAG_INJURY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Client client = new ClientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> client.getTags().remove(0));
    }

    @Test
    public void isSameClient() {
        // same object -> returns true
        assertTrue(ALICE.isIdentical(ALICE));

        // null -> returns false
        assertFalse(ALICE.isIdentical(null));

        // different phone and email -> returns false
        Client editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isIdentical(editedAlice));

        // different name -> returns true
        editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isIdentical(editedAlice));

        // same name, same phone, different email -> returns true
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_INJURY).build();
        assertFalse(ALICE.isIdentical(editedAlice));

        // same email, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_INJURY).build();
        assertTrue(ALICE.isIdentical(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_INJURY).build();
        assertTrue(ALICE.isIdentical(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Client aliceCopy = new ClientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different Client -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Client editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ClientBuilder(ALICE).withTags(VALID_TAG_INJURY).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
