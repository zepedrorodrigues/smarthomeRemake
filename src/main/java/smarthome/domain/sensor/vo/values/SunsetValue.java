package smarthome.domain.sensor.vo.values;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a specific value for sunset readings.
 */
public class SunsetValue implements Value {

    final private LocalDateTime value;

    /**
     * Constructs a new instance of the SunsetValue class.
     *
     * @param sunsetValue the LocalDateTime of the sunset.
     */
    public SunsetValue(LocalDateTime sunsetValue) {
        if (sunsetValue == null) {
            throw new IllegalArgumentException();
        }
        this.value = sunsetValue;
    }
    /**
     * Compares this SunsetValue with another object
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
        SunsetValue that = (SunsetValue) o;
        return this.value.equals(that.value);
    }

    /**
     * Retrieves the hash code of the SunsetValue
     *
     * @return the hash code of the SunsetValue
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    /**
     * Converts the sunset value to a string representation.
     *
     * @return a string representation of the sunset value.
     */
    @Override
    public String valueToString() {
        return String.valueOf(this.value);
    }
}
