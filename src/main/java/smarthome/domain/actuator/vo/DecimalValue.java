package smarthome.domain.actuator.vo;

import smarthome.domain.sensor.vo.values.Value;

import java.util.Objects;

/**
 * Represents a specific decimal value for actuators.
 */
public class DecimalValue implements Value {

    private final double decimalValue;

    /**
     * Constructs a new instance of the DecimalValue class.
     *
     * @param decimalValue the double value of the decimal.
     */
    public DecimalValue(double decimalValue) {
        this.decimalValue = decimalValue;
    }

    /**
     * Checks if this DecimalValue is the same as the given object (same class with same decimal value).
     *
     * @param o the object to compare with
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecimalValue that = (DecimalValue) o;
        return Double.compare(decimalValue, that.decimalValue) == 0;
    }

    /**
     * Returns the hash code of the decimal value.
     *
     * @return the hash code of the decimal value
     */
    @Override
    public int hashCode() {
        return Objects.hash(decimalValue);
    }

    /**
     * Converts the decimal value to a string representation.
     *
     * @return a string representation of the decimal value.
     */
    @Override
    public String valueToString() {
        return String.valueOf(decimalValue);
    }
}
