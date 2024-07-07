package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents the value of a power consumption.
 * It is a value object and an immutable object, an implementation of the Value interface.
 */
public class PowerConsumptionValue implements Value{
    /**
     * The value of the power consumption.
     */
    private final double value;

    /**
     * Creates a new PowerConsumptionValue.
     * @param value the value of the power consumption
     */
    public PowerConsumptionValue(double value) {
        if(value<0){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    /**
     * Compares the PowerConsumptionValue with another object.
     * @param object the object to compare with
     * @return  true if the objects are the same or have the same value, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PowerConsumptionValue that = (PowerConsumptionValue) object;
        return Double.compare(value, that.value) == 0;
    }

    /**
     * Returns the hash code value for the PowerConsumptionValue.
     * @return a hash code value for this PowerConsumptionValue, based on Objects.hash method on the primitive value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    /**
     * Converts the value to a string representation.
     *
     * @return A string representation of the value.
     */
    @Override
    public String valueToString() {
        return Double.toString(value);
    }
}
