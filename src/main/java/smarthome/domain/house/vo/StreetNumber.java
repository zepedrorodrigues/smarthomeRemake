package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

/**
 * Represents a street number.
 */
public class StreetNumber implements ValueObject {

    private final String streetNumber;

    /**
     * Constructs a StreetNumber object with the specified street number.
     *
     * @param streetNumber the street number to set
     * @throws IllegalArgumentException if the street number is null or blank
     */
    public StreetNumber(String streetNumber) {
        if (streetNumber == null || streetNumber.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.streetNumber = streetNumber;
    }

    /**
     * Retrieves the street number.
     *
     * @return the street number
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Determines if this StreetNumber object is equal to another object.
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
        final StreetNumber that = (StreetNumber) o;
        return streetNumber.equals(that.streetNumber);
    }

    /**
     * Returns the hash code of the StreetNumber object.
     *
     * @return the hash code of the StreetNumber object
     */
    @Override
    public int hashCode() {
        return streetNumber.hashCode();
    }
}
