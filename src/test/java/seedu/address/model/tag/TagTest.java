package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    public void constructor_invalidTagName_throwsIllegalArgumentException(String invalidTagName) {
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    public void constructor_validTagName(String validTagName) {
        assertEquals(validTagName, new Tag(validTagName).tagName);
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void invalidTagName_emptyString_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("");
    }

    @Test
    public void invalidTagName_singleHyphen_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("-");
    }

    @Test
    public void invalidTagName_multipleHyphen_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("--");
    }

    @Test
    public void invalidTagName_startsWithHyphen_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("-mytag");
    }

    @Test
    public void invalidTagName_startsWithHyphenMultiple_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("-my-tag");
    }

    @Test
    public void invalidTagName_endsWithHyphen_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("my-");
    }

    @Test
    public void invalidTagName_endsWithHyphenMultiple_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("my-tag-");
    }

    @Test
    public void invalidTagName_startsAndEndsWithHyphen_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("-my-tag-");
    }

    @Test
    public void invalidTagName_singleCharacter_throwsIllegalArgumentException() {
        constructor_invalidTagName_throwsIllegalArgumentException("1");
    }

    @Test
    public void validTagName_twoCharacter() {
        constructor_validTagName("a1");
    }

    @Test
    public void validTagName_singleHyphen() {
        constructor_validTagName("my-tag");
    }

    @Test
    public void validTagName_multipleHyphen() {
        constructor_validTagName("my-tag-has-more-than-one-tags");
    }

    @Test
    public void validTagName_consecutiveHyphens() {
        constructor_validTagName("my-tag-has------consecutive-----tags");
    }

}
