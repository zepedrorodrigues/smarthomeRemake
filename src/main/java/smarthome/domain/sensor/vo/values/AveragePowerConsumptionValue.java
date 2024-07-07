package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * This class represents the average power consumption value.
 * It implements the Value interface and ensures that the value is not negative.
 */
public class AveragePowerConsumptionValue implements Value {
    private final double value;

    /**
     * Constructs a new AveragePowerConsumptionValue with the specified value.
     *
     * @param value the value of the average power consumption. It should be a non-negative number.
     * @throws IllegalArgumentException if the value is negative.
     */
    public AveragePowerConsumptionValue(double value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    /**
     * Returns a string representation of the value.
     *
     * @return a string representation of the value.
     */
    @Override
    public String valueToString() {
        return String.valueOf(value);
    }

    /**
     * Checks if the given object is equal to this average power consumption value.
     * Two average power consumption values are considered equal if they have the same value.
     *
     * @param o the object to compare with
     * @return true if the given object is equal to this average power consumption value, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AveragePowerConsumptionValue that = (AveragePowerConsumptionValue) o;
        return Double.compare(that.value, value) == 0;
    }

    /**
     * Returns the hash code of this average power consumption value.
     * The hash code is computed based on the value.
     *
     * @return the hash code of this average power consumption value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}