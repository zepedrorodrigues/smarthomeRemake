package smarthome.domain.room.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents the width of an object in the smart home domain.
 * This class is used to model the dimension of width in various contexts within the smart home,
 * ensuring that only valid positive width values are used throughout the system.
 */
public class Width implements ValueObject {
    private final double value;

    /**
     * Constructs a Width object.
     *
     * @param value the width value. Must be greater than 0.
     * @throws IllegalArgumentException if width is less than or equal to 0.
     */
    public Width(double value) {
        if (!validValue(value))
            throw new IllegalArgumentException();
        this.value = value;
    }

    /**
     * Returns the width value.
     *
     * @return the width value.
     */
    public double getWidth() {
        return this.value;
    }

    /**
     * Validates the given width value.
     *
     * @param value the width value to validate.
     * @return true if the value is greater than 0, otherwise false.
     */
    private boolean validValue(double value) {
        return value > 0;
    }

    /**
     * Compares this Width object to another object for equality.
     *
     * @param o the object to compare this Width to.
     * @return true if the given object is a Width and its width value is equal to this Width's width value, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Width that = (Width) o;
        return Double.compare(this.value, that.value) == 0;
    }

    /**
     * Generates a hash code for this Width object.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }
}
