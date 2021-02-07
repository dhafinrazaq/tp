package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.exceptions.DuplicateEntityException;
import seedu.address.model.exceptions.EntityNotFoundException;

public class UniqueList<T extends CheckExisting<T> & Comparable<T>> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isIdentical);
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEntityException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the {@code target} in the list with {@code editedTarget}.
     * {@code target} must exist in the list.
     * The T identity of {@code editedTarget} must not be the same as another existing T in the list.
     */
    public void set(T target, T edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntityNotFoundException();
        }

        if (!target.isIdentical(edited) && contains(edited)) {
            throw new DuplicateEntityException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntityNotFoundException();
        }
    }

    public void setAll(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list.
     * It must not contain duplicate objects.
     */
    public void setAll(List<T> elements) {
        requireAllNonNull(elements);
        if (!elementsAreUnique(elements)) {
            throw new DuplicateEntityException();
        }

        internalList.setAll(elements);
    }

    /**
     * Returns all elements T in the internal list that matches {@code predicate}.
     */
    public Stream<T> findAllMatch(Predicate<T> predicate) {
        requireNonNull(predicate);
        return internalUnmodifiableList.stream().filter(predicate);
    }

    /**
     * Returns an element T in the internal list that matches {@code predicate}.
     */
    public Optional<T> findAnyMatch(Predicate<T> predicate) {
        requireNonNull(predicate);
        return internalUnmodifiableList.stream().filter(predicate).findAny();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueList<?> // instanceof handles nulls
                && internalList.equals(((UniqueList<?>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Sorts elements in the list using T's compareTo method.
     */
    public void sort() {
        internalList.sort(Comparable::compareTo);
    }

    /**
     * Returns true if {@code elements} contains are unique.
     * Note that this only compares using CheckExisiting::isIdentical
     */
    private boolean elementsAreUnique(List<T> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (elements.get(i).isIdentical(elements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
