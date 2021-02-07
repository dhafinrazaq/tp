package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.WeightUnit.KILOGRAM_TO_POUND_MULTIPLIER;
import static seedu.address.model.util.WeightUnit.getKgInPound;
import static seedu.address.model.util.WeightUnit.getPoundInKg;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeightUnitTest {
    private static final double VALID_WEIGHT_IN_KG = 5;
    private static final double SIMILAR_WEIGHT_IN_POUND = 5 * KILOGRAM_TO_POUND_MULTIPLIER;
    private static final double INVALID_ZERO_WEIGHT = 0;
    private static final double INVALID_NEGATIVE_WEIGHT = -0.01;

    @Test
    public void constructor_invalidWeightUnit_throwsIllegalArgumentException() {
        String invalidWeightUnit = "g";
        assertThrows(IllegalArgumentException.class, () -> new WeightUnit(invalidWeightUnit));
        assertThrows(IllegalArgumentException.class, () -> new WeightUnit(String.valueOf(INVALID_ZERO_WEIGHT)));
    }

    @Test
    public void getPoundInKgTest() {

        // eq: invalidWeight
        assertThrows(IllegalArgumentException.class, () -> getPoundInKg(INVALID_ZERO_WEIGHT));
        assertThrows(IllegalArgumentException.class, () -> getPoundInKg(INVALID_NEGATIVE_WEIGHT));

        // eq: valid weight
        assertEquals(getPoundInKg(SIMILAR_WEIGHT_IN_POUND), VALID_WEIGHT_IN_KG);
    }

    @Test
    public void getKgInPoundTest() {

        // eq: invalidWeight
        assertThrows(IllegalArgumentException.class, () -> getKgInPound(INVALID_ZERO_WEIGHT));
        assertThrows(IllegalArgumentException.class, () -> getKgInPound(INVALID_NEGATIVE_WEIGHT));

        // eq: valid weight
        assertEquals(getKgInPound(VALID_WEIGHT_IN_KG), SIMILAR_WEIGHT_IN_POUND);
    }

    @Test
    public void isValidWeightUnit() {
        // eq: null
        assertThrows(NullPointerException.class, () -> WeightUnit.isValidUnit(null));

        // eq: valid weight
        assertTrue(WeightUnit.isValidUnit("kg"));
        assertTrue(WeightUnit.isValidUnit("lb"));
    }

    @Test
    public void equalsTest() {
        WeightUnit firstWeightUnit = new WeightUnit("kg");
        WeightUnit secondWeightUnit = new WeightUnit("lb");
        WeightUnit similarToSecondWeightUnit = new WeightUnit("lb");

        assertEquals(firstWeightUnit, firstWeightUnit);
        assertEquals(secondWeightUnit, secondWeightUnit);
        assertEquals(similarToSecondWeightUnit, similarToSecondWeightUnit);

        assertNotEquals(secondWeightUnit, firstWeightUnit);
        assertEquals(similarToSecondWeightUnit, secondWeightUnit);
    }

    @Test
    public void hashCodeTest() {
        String weightUnitInString = "kg";
        WeightUnit weightUnit = new WeightUnit(weightUnitInString);

        assertEquals(weightUnitInString.hashCode(), weightUnit.hashCode());
    }
}
