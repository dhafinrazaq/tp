package seedu.address.logic.commands.schedule;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.testutil.EditScheduleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class EditScheduleTestUtil {

    public static final String VALID_CLIENT_INDEX_SCHA = "1";
    public static final String VALID_CLIENT_INDEX_SCHB = "2";
    public static final String VALID_SESSION_INDEX_SCHA = "1";
    public static final String VALID_SESSION_INDEX_SCHB = "2";

    public static final EditScheduleDescriptor DESC_SCHA;
    public static final EditScheduleDescriptor DESC_SCHB;

    static {
        DESC_SCHA = new EditScheduleDescriptorBuilder()
                .withClientIndex(Index.fromOneBased(Integer.parseInt(VALID_CLIENT_INDEX_SCHA)))
                .withSessionIndex(Index.fromOneBased(Integer.parseInt(VALID_SESSION_INDEX_SCHA))).build();
        DESC_SCHB = new EditScheduleDescriptorBuilder()
                .withClientIndex(Index.fromOneBased(Integer.parseInt(VALID_CLIENT_INDEX_SCHB)))
                .withSessionIndex(Index.fromOneBased(Integer.parseInt(VALID_SESSION_INDEX_SCHB))).build();
    }
}
