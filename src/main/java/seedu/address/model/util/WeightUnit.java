package seedu.address.model.util;

import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Utility class to represent unit of weight.
 */
public class WeightUnit {
    public static final String KILOGRAM = "kg";
    public static final String POUND = "lb";
    public static final String MESSAGE_INVALID_UNIT_STATUS = "Weight must be in kg or lb.";
    public static final double KILOGRAM_TO_POUND_MULTIPLIER = 2.20462;
    private final String weightUnit;

    /**
     * Constructs a Default WeightUnit of kg.
     */
    public WeightUnit() {
        this.weightUnit = KILOGRAM;
    }

    /**
     * Constructs a {@code WeightUnit}.
     *
     * @param weightUnit A String of either 'kg' or 'lb'.
     */
    public WeightUnit(String weightUnit) {
        checkArgument(isValidUnit(weightUnit), MESSAGE_INVALID_UNIT_STATUS);
        this.weightUnit = weightUnit;
    }

    /**
     * Returns true if a given String value is a valid unit.
     */
    public static boolean isValidUnit(String unit) {
        return unit.equals(KILOGRAM) | unit.equals(POUND);
    }

    /**
     * Returns true if a given String value represents pound unit.
     */
    public boolean isPoundUnit() {
        return this.weightUnit.equals(POUND);
    }

    public static double getKgInPound(double weightInKilogram) {
        checkArgument(weightInKilogram > 0);
        return weightInKilogram * KILOGRAM_TO_POUND_MULTIPLIER;
    }

    public static double getPoundInKg(double weightInPound) {
        checkArgument(weightInPound > 0);
        return weightInPound / KILOGRAM_TO_POUND_MULTIPLIER;
    }

    @Override
    public int hashCode() {
        return weightUnit.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightUnit // instanceof handles nulls
                && weightUnit.equals(((WeightUnit) other).weightUnit)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return weightUnit;
    }
}
