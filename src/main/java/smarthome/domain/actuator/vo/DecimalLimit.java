package smarthome.domain.actuator.vo;

import lombok.Getter;
import smarthome.domain.sensor.vo.values.Value;

import java.util.Objects;


/**
 * This class represents a Value Object DecimalLimit.
 * It implements the ValueObject interface.
 */
@Getter
public class DecimalLimit implements Value {


    private final double decimalLimit;

    /**
     * Constructs a DecimalLimit with the given decimalLimit.
     *
     * @param decimalLimit the decimal limit.
     */
    public DecimalLimit(double decimalLimit) {
        this.decimalLimit = decimalLimit;
    }

    /**
     * Checks if this DecimalLimit is the same as the given object (same class with same decimal limit).
     *
     * @param o the object to compare with
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecimalLimit that = (DecimalLimit) o;
        return Double.compare(decimalLimit, that.decimalLimit) == 0;
    }

    /**
     * Returns the hash code of the decimal limit.
     *
     * @return the hash code of the decimal limit
     */
    @Override
    public int hashCode() {
        return Objects.hash(decimalLimit);
    }

    /**
     * Converts the decimal limit to a string representation.
     *
     * @return a string representation of the decimal limit
     */
    @Override
    public String valueToString() {
        return String.valueOf(decimalLimit);
    }
}
