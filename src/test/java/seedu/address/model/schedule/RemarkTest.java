package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void isValidRemark() {
        // null Remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // valid Remarks
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark(" ")); // spaces only
        assertTrue(Remark.isValidRemark("MyRemark"));
        assertTrue(Remark.isValidRemark("-")); // one character
        assertTrue(Remark.isValidRemark("My Super Duper Dupe Remark")); // multispace Remark

        // check trimmed remark
        assertEquals(new Remark("   a   "), new Remark("a"));
    }
}

