package smarthome.domain.room.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents the height value object in the domain of a smart home system.
 * This class encapsulates the height value ensuring it adheres to the business rules
 * defined for heights within the system.
 */
public class Height implements ValueObject {
    private final double value;

    /**
     * Constructs a Height object with the specified value.
     *
     * @param value the height value to be encapsulated within this object. Must be greater than 0.
     * @throws IllegalArgumentException if the provided value does not satisfy the validValue predicate.
     */
    public Height(double value) {
        if(!validValue(value))
            throw new IllegalArgumentException();
        this.value = value;
    }

    /**
     * Retrieves the encapsulated height value.
     *
     * @return the height value.
     */
    public double getHeight() {
        return value;
    }

    /**
     * Validates the height value according to business rules.
     *
     * @param value the height value to validate.
     * @return {@code true} if the value is greater than 0, {@code false} otherwise.
     */
    private boolean validValue(double value){
        return value > 0;
    }

    /**
     * Compares this Height object to another object for equality.
     * @param object the object to compare this Height against.
     * @return {@code true} if the provided object is also a Height and has an equal value, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Height height = (Height) object;
        return Double.compare(value, height.value) == 0;
    }

    /**
     * Generates a hash code for a Height object based on the encapsulated value.
     * In this case, the hash code is generated using the encapsulated value, not the object itself.
     * @return the hash code of the Height object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);}
}


