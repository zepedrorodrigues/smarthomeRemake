package smarthome.domain.reading.vo;

import smarthome.domain.sensor.vo.values.Value;

import java.util.Objects;

/**
 * Represents the value of a reading.
 */
public class ReadingValue implements Value {

    private final String value;

    /**
     * Initializes the value of the reading.
     *
     * @param value the value of the reading.
     */
    public ReadingValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    /**
     * Returns the value of the reading.
     *
     * @return the value of the reading.
     */
    @Override
    public String valueToString() {
        return this.value;
    }

    /**
     * Compares the value of the reading with another object.
     *
     * @param o the object to compare with.
     * @return true if the object is equal to the value of the reading, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReadingValue that = (ReadingValue) o;
        return this.value.equals(that.value);
    }

    /**
     * Returns the hash code of the value of the reading.
     *
     * @return the hash code of the value of the reading.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
