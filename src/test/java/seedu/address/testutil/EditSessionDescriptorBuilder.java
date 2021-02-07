package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Gym;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

/**
 * A utility class to help with building EditSessionDescriptor objects.
 */
public class EditSessionDescriptorBuilder {

    private EditSessionDescriptor descriptor;

    public EditSessionDescriptorBuilder() {
        descriptor = new EditSessionDescriptor();
    }

    public EditSessionDescriptorBuilder(EditSessionDescriptor descriptor) {
        this.descriptor = new EditSessionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSessionDescriptor} with fields containing {@code Session}'s details
     */
    public EditSessionDescriptorBuilder(Session session) {
        descriptor = new EditSessionDescriptor();
        descriptor.setGym(session.getGym());
        descriptor.setExerciseType(session.getExerciseType());
        descriptor.setInterval(session.getInterval());
    }

    /**
     * Sets the {@code Gym} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withGym(String gym) {
        descriptor.setGym(new Gym(gym));
        return this;
    }

    /**
     * Sets the {@code ExerciseType} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withExerciseType(String exerciseType) {
        descriptor.setExerciseType(new ExerciseType(exerciseType));
        return this;
    }

    /**
     * Sets the {@code Interval} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withInterval(String startTime, String duration) {
        descriptor.setInterval(new Interval(LocalDateTime.parse(startTime, Interval.DATE_TIME_FORMATTER),
                Integer.parseInt(duration)));
        return this;
    }

    public EditSessionDescriptor build() {
        return descriptor;
    }
}
