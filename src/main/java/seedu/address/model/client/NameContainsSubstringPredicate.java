package seedu.address.model.client;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Client}'s {@code Name} matches any substring given.
 */
public class NameContainsSubstringPredicate implements Predicate<Client> {
    private final List<String> keywords;

    public NameContainsSubstringPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(client.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSubstringPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsSubstringPredicate) other).keywords)); // state check
    }

}
