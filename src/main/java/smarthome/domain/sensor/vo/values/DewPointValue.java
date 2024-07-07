package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents the value of a dew point sensor
 */
public class DewPointValue implements Value {

    final private double dewPointValue;

    /**
     * Constructs a new DewPointValue object with the given Dew Point value
     *
     * @param dewPointValue the value of the sensor
     */
    public DewPointValue(double dewPointValue) {
        this.dewPointValue = dewPointValue;
    }

    /**
     * Returns a string representation of the dew point value
     *
     * @return a string representation of the dew point value
     */
    @Override
    public String valueToString() {
        return String.valueOf(dewPointValue);
    }

    /**
     * Determines if this DewPointValue is equal to another object
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DewPointValue that = (DewPointValue) object;
        return Double.compare(dewPointValue, that.dewPointValue) == 0;
    }

    /**
     * Returns the hash code of the DewPointValue
     *
     * @return the hash code of the DewPointValue
     */
    @Override
    public int hashCode() {
        return Objects.hash(dewPointValue);
    }
}
