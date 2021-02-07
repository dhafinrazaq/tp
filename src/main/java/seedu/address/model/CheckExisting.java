package seedu.address.model;

/**
 * This interface provides a weaker equality check.
 */
public interface CheckExisting<T> {
    /**
     * Returns true if 2 elements has the same unique identifier.
     * This defines a weaker notion of equality between two elements.
     */
    public boolean isIdentical(T other);
}
