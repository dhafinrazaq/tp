package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.schedule.PaymentStatus;
import seedu.address.model.schedule.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Weight;
import seedu.address.model.session.Interval;
import seedu.address.model.session.Session;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_CLIENT = "Your data file contains duplicate client(s).";
    public static final String MESSAGE_DUPLICATE_SESSION = "Your data file contains overlapping session(s).";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "Your data file contains duplicate schedule(s).";
    public static final String CLIENT_NOT_FOUND = "Cannot find client with email %s that is associated to a schedule.";
    public static final String SESSION_NOT_FOUND =
            "Cannot find session with interval %s that is associated to a schedule.";

    private final List<JsonAdaptedClient> clients = new ArrayList<>();
    private final List<JsonAdaptedSession> sessions = new ArrayList<>();
    private final List<JsonAdaptedSchedule> schedules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given Clients.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("clients") List<JsonAdaptedClient> clients,
                                       @JsonProperty("sessions") List<JsonAdaptedSession> sessions,
                                       @JsonProperty("schedules") List<JsonAdaptedSchedule> schedules) {
        this.clients.addAll(clients);
        this.sessions.addAll(sessions);
        this.schedules.addAll(schedules);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        this.clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).collect(Collectors.toList()));
        this.sessions.addAll(source.getSessionList().stream().map(JsonAdaptedSession::new)
                .collect(Collectors.toList()));
        this.schedules.addAll(source.getScheduleList().stream().map(JsonAdaptedSchedule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedClient jsonAdaptedClient : this.clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (addressBook.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
            }
            addressBook.addClient(client);
        }

        for (JsonAdaptedSession jsonAdaptedSession : this.sessions) {
            Session session = jsonAdaptedSession.toModelType();
            if (addressBook.hasSession(session)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SESSION);
            }
            addressBook.addSession(session);
        }

        for (JsonAdaptedSchedule jsonAdaptedSchedule : this.schedules) {
            Email clientEmail = jsonAdaptedSchedule.getClientEmail();
            Client client = getClientWithEmail(clientEmail, addressBook);
            if (client == null) {
                throw new IllegalValueException(String.format(CLIENT_NOT_FOUND, clientEmail));
            }

            Interval sessionInterval = jsonAdaptedSchedule.getSessionInterval();
            Session session = getSessionWithInterval(sessionInterval, addressBook);
            if (session == null) {
                throw new IllegalValueException(String.format(SESSION_NOT_FOUND, sessionInterval));
            }

            PaymentStatus paymentStatus = jsonAdaptedSchedule.getPaymentStatus();
            Remark remark = jsonAdaptedSchedule.getRemark();
            Weight weight = jsonAdaptedSchedule.getWeight();

            Schedule schedule = new Schedule(client, session, paymentStatus, remark, weight);
            if (addressBook.hasSchedule(schedule)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCHEDULE);
            }
            addressBook.addSchedule(schedule);
        }

        return addressBook;
    }

    /**
     * Returns the {@code Client} with the same {@code Email} from {@code addressBook} or null if not found.
     */
    private Client getClientWithEmail(Email email, AddressBook addressBook) {
        for (Client client : addressBook.getClientList()) {
            if (client.getEmail().equals(email)) {
                return client;
            }
        }
        return null;
    }

    /**
     * Returns the {@code Session} with the same {@code id} from {@code addressBook} or null if not found.
     */
    private Session getSessionWithInterval(Interval interval, AddressBook addressBook) {
        for (Session session : addressBook.getSessionList()) {
            if (session.getInterval().equals(interval)) {
                return session;
            }
        }
        return null;
    }

}
