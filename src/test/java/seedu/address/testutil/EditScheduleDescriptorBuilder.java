package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.schedule.PaymentStatus;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Weight;

/**
 * A utility class to help with building EditScheduleDescriptorBuilder objects.
 */
public class EditScheduleDescriptorBuilder {

    private EditScheduleDescriptor descriptor;

    public EditScheduleDescriptorBuilder(EditScheduleDescriptor descriptor) {
        this.descriptor = new EditScheduleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditScheduleDescriptorBuilder} with fields containing {@code Schedule}'s details
     */
    public EditScheduleDescriptorBuilder() {
        descriptor = new EditScheduleDescriptor();
    }

    /**
     * Sets the {@code Index} of the {@code EditScheduleDescriptorBuilder} that we are building.
     */
    public EditScheduleDescriptorBuilder withClientIndex(Index clientIndex) {
        descriptor.setClientIndex(clientIndex);
        return this;
    }

    /**
     * Sets the {@code session Index} of the {@code EditScheduleDescriptorBuilder} that we are building.
     */
    public EditScheduleDescriptorBuilder withUpdatedSessionIndex(Index sessionIndex) {
        descriptor.setUpdatedSessionIndex(sessionIndex);
        return this;
    }

    /**
     * Sets the {@code paymentStatus} of the {@code EditScheduleDescriptorBuilder} that we are building.
     */
    public EditScheduleDescriptorBuilder withUpdatedPaymentStatus(PaymentStatus paymentStatus) {
        descriptor.setUpdatedPayment(paymentStatus);
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code EditScheduleDescriptorBuilder} that we are building.
     */
    public EditScheduleDescriptorBuilder withUpdatedRemark(Remark remark) {
        descriptor.setUpdatedRemark(remark);
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code EditScheduleDescriptorBuilder} that we are building.
     */
    public EditScheduleDescriptorBuilder withUpdatedWeight(Weight weight) {
        descriptor.setUpdatedWeight(weight);
        return this;
    }

    /**
     * Sets the {@code updated session Index} of the {@code EditScheduleDescriptorBuilder} that we are building.
     */
    public EditScheduleDescriptorBuilder withSessionIndex(Index sessionIndex) {
        descriptor.setSessionIndex(sessionIndex);
        return this;
    }


    public EditScheduleDescriptor build() {
        return descriptor;
    }
}
