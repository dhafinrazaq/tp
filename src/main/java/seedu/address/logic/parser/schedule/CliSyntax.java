package seedu.address.logic.parser.schedule;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    /* Prefix definitions */
    public static final Prefix PREFIX_CLIENT_INDEX = new Prefix("c/");
    public static final Prefix PREFIX_SESSION_INDEX = new Prefix("s/");
    public static final Prefix PREFIX_PAYMENT_STATUS = new Prefix("pd/");
    public static final Prefix PREFIX_UPDATED_SESSION_INDEX = new Prefix("us/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_WEIGHT = new Prefix("w/");
}
