package smarthome.domain.room.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents the length of an object in the smart home domain.
 * This class is used to model the dimension of length in various contexts within the smart home,
 * ensuring that only valid positive length values are used throughout the system.
 */
public class Length implements ValueObject {
    private final double value;

    /**
     * Constructs a Length object with a given value.
     *
     * @param value the length value to be assigned. Must be a positive number.
     * @throws IllegalArgumentException if the provided value is not positive.
     */
    public Length(double value) {
        if(!validValue(value))
            throw new IllegalArgumentException();
        this.value = value;
    }

    /**
     * Returns the value of the length.
     *
     * @return the double value representing the length.
     */
    public double getLength() {
        return value;
    }

    /**
     * Validates the given length value.
     *
     * @param value the length value to validate.
     * @return true if the value is greater than 0, otherwise false.
     */
    private boolean validValue(double value){
        return value > 0;
    }

    /**
     * Compares this Length object to another object for equality.
     * @param object the object to compare this Length against.
     * @return true if the provided object is also a Length and has an equal value, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Length length = (Length) object;
        return Double.compare(value, length.value) == 0;
    }

    /**
     * Generates a hash code for a Length object based on the encapsulated value.
     * @return the hash code of the Length object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
