package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

/**
 * Value object representing a street name.
 */
public class StreetName implements ValueObject {

    private final String street;

    /**
     * Constructs a StreetName object with the specified street name.
     *
     * @param streetName the street name to set
     * @throws IllegalArgumentException if the street name is null or blank
     */
    public StreetName(String streetName) {
        if (streetName == null || streetName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.street = streetName;
    }

    /**
     * Retrieves the street name.
     *
     * @return the street name
     */
    public String getStreetName() {
        return this.street;
    }

    /**
     * Compares this StreetName object with another object to determine if they are equal.
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
        StreetName that = (StreetName) o;
        return street.equals(that.street);
    }

    /**
     * Generates a hash code for the StreetName object.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return street.hashCode();
    }
}
