package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaymentStatus(null));
    }

    @Test
    public void isPaid() {
        PaymentStatus paidPayment = new PaymentStatus("paid");
        PaymentStatus unpaidPayment = new PaymentStatus("unpaid");

        assertTrue(paidPayment.isPaid());
        assertTrue(!unpaidPayment.isPaid());
    }

    @Test
    public void isValidPaymentStatus() {
        // null Payment Status
        assertThrows(NullPointerException.class, () -> PaymentStatus.isValidPaymentStatus(null));

        // valid Remarks
        assertTrue(PaymentStatus.isValidPaymentStatus("paid")); // paid
        assertTrue(PaymentStatus.isValidPaymentStatus("unpaid")); // unpaid
    }
}
