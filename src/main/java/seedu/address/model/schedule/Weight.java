package seedu.address.model.schedule;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.util.WeightUnit.KILOGRAM;

import seedu.address.model.util.WeightUnit;


/**
 * Represents a weight associated to a Schedule in the address book.
 */
public class Weight {
    public static final String MESSAGE_INVALID_WEIGHT_STATUS = "Weight must be a positive number and less than 1000kg.";
    public static final Double MAX_WEIGHT_ACCEPTED = 1000.0;

    /**
     * Value of weight in kg.
     */
    private final double weight;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight for schedule.
     */
    public Weight(double weight) {
        checkArgument(isValidWeight(weight), MESSAGE_INVALID_WEIGHT_STATUS);
        this.weight = weight;
    }

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight for schedule.
     */
    public Weight(double weight, WeightUnit unit) {
        double weightInKg = unit.isPoundUnit() ? WeightUnit.getPoundInKg(weight) : weight;
        checkArgument(isValidWeight(weightInKg), MESSAGE_INVALID_WEIGHT_STATUS);
        this.weight = weightInKg;
    }

    /**
     * Constructs a Default Weight of 0.
     */
    private Weight() {
        this.weight = 0;
    }

    /**
     * Returns true if a given double value is a valid weight double and less than 10000.
     */
    public static boolean isValidWeight(double weight) {
        return weight > 0 && weight < MAX_WEIGHT_ACCEPTED;
    }

    /**
     * Returns true if a given Weight object contains a valid weight.
     * @param weight Weight object to check if the value is valid weight.
     * @throws NullPointerException throws NullPointerException if either of the given input is null.
     */
    public static boolean isValidWeight(Weight weight) {
        return isValidWeight(weight.getWeight());
    }

    /**
     * Returns a {@code: Weight} object initialised to 0kg.
     */
    public static Weight getDefaultWeight() {
        return new Weight();
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return String.format("%.1f", this.weight) + KILOGRAM;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && weight == ((Weight) other).weight); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(weight).hashCode();
    }
}
