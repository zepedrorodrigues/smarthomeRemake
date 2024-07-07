package smarthome.domain.reading.vo;

import smarthome.ddd.DomainId;

import java.util.Objects;

/**
 * Represents a Reading id.
 */
public class ReadingId implements DomainId {

    private final String id;

    /**
     * Constructs a ReadingId object with the specified id value.
     *
     * @param id the reading identifier
     * @throws IllegalArgumentException if the identifier is null or blank
     */
    public ReadingId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * Retrieves the reading identifier.
     *
     * @return the reading identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Determines if this ReadingId object is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReadingId oReadingId = (ReadingId) o;
        return id.equals(oReadingId.id);
    }

    /**
     * Returns the hash code of the ReadingId.
     *
     * @return the hash code of the ReadingId
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

