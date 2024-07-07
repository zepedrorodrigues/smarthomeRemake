package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents the value of a temperature sensor
 */
public class TemperatureValue implements Value {

    final private double value;

    /**
     * Constructs a new TemperatureValue object with the given temperature value
     *
     * @param value the value of the sensor
     */
    public TemperatureValue(double value) {
        this.value = value;
    }

    /**
     * Returns a string representation of the temperature value
     *
     * @return a string representation of the temperature value
     */
    @Override
    public String valueToString() {
        return String.valueOf(value);
    }

    /**
     * Determines if this TemperatureValue is equal to another object
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TemperatureValue that = (TemperatureValue) o;
        return Double.compare(value, that.value) == 0;
    }

    /**
     * Returns the hash code of the TemperatureValue
     *
     * @return the hash code of the TemperatureValue
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
