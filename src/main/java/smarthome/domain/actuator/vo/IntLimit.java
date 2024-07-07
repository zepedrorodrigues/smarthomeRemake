package smarthome.domain.actuator.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * This class represents the IntLimit value object.
 */
public class IntLimit implements ValueObject {

    private final int intLimit;

    /**
     * Constructs a new IntLimit with the given intLimit.
     *
     * @param intLimit The intLimit to be set.
     */
    public IntLimit(int intLimit) {
        this.intLimit = intLimit;
    }

    /**
     * Retrieves the intLimit.
     *
     * @return The intLimit.
     */
    public int getIntLimit() {
        return intLimit;
    }

    /**
     * Checks if this IntLimit is equal to the given object (same class with same int limit).
     *
     * @param o the object to compare with
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntLimit intLimit1 = (IntLimit) o;
        return intLimit == intLimit1.intLimit;
    }

    /**
     * Generates a hash code for this IntLimit.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(intLimit);
    }
}
