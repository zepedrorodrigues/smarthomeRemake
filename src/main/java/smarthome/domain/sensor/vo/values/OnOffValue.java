package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents the value of a sensor that can be either on or off
 */
public class OnOffValue implements Value {

    final private boolean value;

    /**
     * Creates a new OnOffValue
     *
     * @param value the value of the sensor
     */
    public OnOffValue(boolean value) {
        this.value = value;
    }

    /**
     * Returns a string representation of the on/off value
     *
     * @return a string representation of the on/off value
     */
    @Override
    public String valueToString() {
        return String.valueOf(value);
    }

    /**
     * Determines if this OnOffValue is equal to another object
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
        final OnOffValue that = (OnOffValue) o;
        return value == that.value;
    }

    /**
     * Returns the hash code of the OnOffValue
     *
     * @return the hash code of the OnOffValue
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
