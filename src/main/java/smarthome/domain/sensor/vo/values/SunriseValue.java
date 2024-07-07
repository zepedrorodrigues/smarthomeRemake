package smarthome.domain.sensor.vo.values;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a specific value for sunrise readings.
 */
public class SunriseValue implements Value{

    final private LocalDateTime sunriseValue;

    /**
     * Constructs a new instance of the SunriseValue class.
     *
     * @param sunriseValue the LocalDateTime of the sunrise.
     */
    public SunriseValue(LocalDateTime sunriseValue) {
        if (sunriseValue == null) {
            throw new IllegalArgumentException();
        }
        this.sunriseValue = sunriseValue;
    }

    /**
     * Compares this SunriseValue with another object
     *
     * @param o the object to compare with
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SunriseValue that = (SunriseValue) o;
        return sunriseValue.equals(that.sunriseValue);
    }

    /**
     * Returns the hash code of the sunrise value.
     *
     * @return the hash code of the sunrise value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sunriseValue);
    }

    /**
     * Converts the sunrise value to a string representation.
     *
     * @return a string representation of the sunrise value.
     */
    @Override
    public String valueToString() {
        return String.valueOf(sunriseValue);
    }
}
