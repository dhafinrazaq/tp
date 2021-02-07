package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.model.CheckExisting;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.session.ExerciseType;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

/**
 * Represents an association class between Client and Session.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Schedule implements CheckExisting<Schedule>, Comparable<Schedule> {
    // Identity fields
    private Client client;
    private Session session;

    // Data fields
    private PaymentStatus paymentStatus;
    private Remark remark;
    private Weight weight;

    /**
     * Every field must be present and not null.
     */
    public Schedule(Client client, Session session, PaymentStatus paymentStatus, Remark remark, Weight weight) {
        requireAllNonNull(client, session);
        this.client = client;
        this.session = session;
        this.paymentStatus = paymentStatus;
        this.remark = remark;
        this.weight = weight;
    }

    /**
     * Every field must be present and not null.
     * paymentStatus is set to unpaid.
     * remark is set to an empty string.
     */
    public Schedule(Client client, Session session) {
        requireAllNonNull(client, session);
        this.client = client;
        this.session = session;
        this.paymentStatus = PaymentStatus.PAYMENT_STATUS_UNPAID;
        this.remark = Remark.EMPTY_REMARK;
        this.weight = Weight.getDefaultWeight();
    }

    public Client getClient() {
        return client;
    }

    public Name getClientName() {
        return client.getName();
    }

    public Email getClientEmail() {
        return client.getEmail();
    }

    public Session getSession() {
        return session;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public boolean isPaid() {
        return paymentStatus.isPaid();
    }

    public Remark getRemark() {
        return remark;
    }

    public Weight getWeight() {
        return weight;
    }

    public ExerciseType getExerciseType() {
        return session.getExerciseType();
    }

    public Interval getInterval() {
        return session.getInterval();
    }

    public LocalDateTime getStartTime() {
        return getInterval().getStart();
    }

    public LocalDateTime getEndTime() {
        return getInterval().getEnd();
    }

    public Schedule setClient(Client newClient) {
        return new Schedule(newClient, this.session, this.paymentStatus, this.remark, this.weight);
    }

    public Schedule setSession(Session newSession) {
        return new Schedule(this.client, newSession, this.paymentStatus, this.remark, this.weight);
    }

    /**
     * Returns true if both Schedules have the same identity fields (equal {@code client} and {@code session}).
     */
    @Override
    public boolean isIdentical(Schedule otherSchedule) {
        if (otherSchedule == this) {
            return true;
        }

        if (otherSchedule == null) {
            return false;
        }

        return otherSchedule.client.equals(this.client) && otherSchedule.session.equals(this.session);
    }

    /**
     * Returns true if both Schedules have the same identity and data fields.
     * This defines a stronger notion of equality between two Schedules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.client.equals(this.client) && otherSchedule.session.equals(this.session)
                && otherSchedule.paymentStatus.equals(paymentStatus) && otherSchedule.remark.equals(this.remark)
                && otherSchedule.weight.equals(weight);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(client, session);
    }

    @Override
    public int compareTo(Schedule other) {
        return this.getSession().compareTo(other.getSession());
    }

    @Override
    public String toString() {
        String paymentStatusString = "Payment status: " + paymentStatus;
        String remarkPresent = !remark.equals(Remark.EMPTY_REMARK) ? "Remark: " + remark : "";
        String weightPresent = Weight.isValidWeight(weight) ? "Weight: " + weight : "";
        return "Client "
                + client
                + "\n"
                + " with session "
                + session
                + "\n"
                + paymentStatusString
                + "\n"
                + remarkPresent
                + "\n"
                + weightPresent;
    }
}
