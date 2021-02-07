package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Schedule's payment status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPaymentStatus(String)}
 */
public class PaymentStatus {
    public static final String VALUE_PAID = "paid";
    public static final String VALUE_UNPAID = "unpaid";
    public static final PaymentStatus PAYMENT_STATUS_UNPAID = new PaymentStatus(VALUE_UNPAID);
    public static final String MESSAGE_INVALID_PAYMENT_STATUS = "Payment Status must be either paid or unpaid";


    private final String value;

    /**
     * Constructs a {@code PaymentStatus}.
     *
     * @param status Whether paid or not paid.
     */
    public PaymentStatus(String status) {
        requireNonNull(status);

        // "paid" or "unpaid" should be case-insensitive
        status = status.toLowerCase();
        checkArgument(isValidPaymentStatus(status), MESSAGE_INVALID_PAYMENT_STATUS);
        this.value = status;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if the {@code value} is paid, or false if unpaid.
     */
    public boolean isPaid() {
        assert isValidPaymentStatus(value);
        return value.equals(VALUE_PAID);
    }

    /**
     * Returns true if a given string is a valid payment status value ("paid" or "unpaid", case insensitive).
     */
    public static boolean isValidPaymentStatus(String stringToTest) {
        return stringToTest.toLowerCase().equals(VALUE_PAID) || stringToTest.toLowerCase().equals(VALUE_UNPAID);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentStatus // instanceof handles nulls
                && value.equals(((PaymentStatus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
