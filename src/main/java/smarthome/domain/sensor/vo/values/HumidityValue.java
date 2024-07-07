package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents the value of a humidity sensor
 */
public class HumidityValue implements Value {

    final private double humidityValue;

    /**
     * Constructs a new HumidityValue object with the given humidity value
     *
     * @param humidityValue the value of the sensor
     */
    public HumidityValue(double humidityValue) {
        this.humidityValue = humidityValue;
    }

    /**
     * Determines if this HumidityValue is equal to another object
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        HumidityValue that = (HumidityValue) object;
        return Double.compare(humidityValue, that.humidityValue) == 0;
    }

    /**
     * Returns the hash code of the HumidityValue
     *
     * @return the hash code of the HumidityValue
     */
    @Override
    public int hashCode() {
        return Objects.hash(humidityValue);
    }

    /**
     * Returns a string representation of the humidity value
     *
     * @return a string representation of the humidity value
     */
    @Override
    public String valueToString() {
        return String.valueOf(humidityValue);
    }
}
