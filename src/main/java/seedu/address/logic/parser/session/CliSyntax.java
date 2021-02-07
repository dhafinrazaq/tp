package seedu.address.logic.parser.session;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_GYM = new Prefix("g/");
    public static final Prefix PREFIX_START_TIME = new Prefix("at/");
    public static final Prefix PREFIX_EXERCISE_TYPE = new Prefix("ex/");
    public static final Prefix PREFIX_DURATION = new Prefix("t/");
    public static final Prefix PREFIX_PERIOD = new Prefix("p/");
    public static final Prefix PREFIX_FORCE = new Prefix("f/");
}
