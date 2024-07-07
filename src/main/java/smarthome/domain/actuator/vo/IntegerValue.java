package smarthome.domain.actuator.vo;

import smarthome.domain.sensor.vo.values.Value;

import java.util.Objects;

/**
 * Represents a specific integer value for actuators.
 */
public class IntegerValue implements Value {

    private final int integerValue;

    /**
     * Constructs a new instance of the IntegerValue class.
     *
     * @param integerValue the double value of the decimal.
     */
    public IntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    /**
     * Checks if this IntegerValue is the same as the given object (same class with same decimal value).
     *
     * @param o the object to compare with
     * @return true if the objects are the same, false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerValue that = (IntegerValue) o;
        return integerValue == that.integerValue;
    }

    /**
     * Returns the hash code of the decimal value.
     *
     * @return the hash code of the decimal value
     */
    @Override
    public int hashCode() {
        return Objects.hash(integerValue);
    }

    /**
     * Converts the decimal value to a string representation.
     *
     * @return a string representation of the decimal value.
     */
    @Override
    public String valueToString() {
        return String.valueOf(integerValue);
    }
}
