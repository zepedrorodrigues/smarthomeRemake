package smarthome.domain.actuator.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;


/**
 * This class represents a Value Object Precision.
 * It implements the ValueObject interface.
 */
public class Precision implements ValueObject {

    private final int precision;

    /**
     * Constructs a Precision with the given precision.
     *
     * @param precision the precision. It must not be negative.
     * @throws IllegalArgumentException if the precision is negative
     */
    public Precision(int precision) {
        if( precision < 0 ) {
            throw new IllegalArgumentException();
        }
        this.precision = precision;
    }

    /**
     * Returns the precision.
     *
     * @return the precision
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Checks if this Precision is the same as the given object (same class with same precision).
     *
     * @param o the object to compare with
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Precision precision1 = (Precision) o;
        return precision == precision1.precision;
    }

    /**
     * Returns the hash code of the precision.
     *
     * @return the hash code of the precision
     */
    @Override
    public int hashCode() {
        return Objects.hash(precision);
    }
}
