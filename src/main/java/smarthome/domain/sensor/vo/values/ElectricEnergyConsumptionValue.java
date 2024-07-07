package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * This class represents the electric energy consumption value.
 * It implements the Value interface and ensures that the value is not negative.
 */
public class ElectricEnergyConsumptionValue implements Value {
    private final double value;

    /**
     * Constructs a new ElectricEnergyConsumptionValue with the specified value.
     *
     * @param value the value of the electric energy consumption. It should be a non-negative number.
     * @throws IllegalArgumentException if the value is negative.
     */
    public ElectricEnergyConsumptionValue(double value) {
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
     * Checks if the given object is equal to this electric energy consumption value.
     * Two electric energy consumption values are considered equal if they have the same value.
     *
     * @param object the object to compare with
     * @return true if the given object is equal to this electric energy consumption value, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ElectricEnergyConsumptionValue that = (ElectricEnergyConsumptionValue) object;
        return Double.compare(value, that.value) == 0;
    }

    /**
     * Returns the hash code of this electric energy consumption value.
     * The hash code is computed based on the value.
     *
     * @return the hash code of this electric energy consumption value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}