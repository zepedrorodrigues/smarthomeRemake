package smarthome.domain.reading.vo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The TimeStamp class is a value object that encapsulates a LocalDateTime value. It provides methods to access the
 * value and to compare TimeStamp objects.
 */
public class TimeStamp
{
    // The LocalDateTime value encapsulated by the TimeStamp object.
    private final LocalDateTime value;

    /**
     * Constructs a new TimeStamp object.
     * @param value the LocalDateTime value to be encapsulated by the TimeStamp object.
     * @throws IllegalArgumentException if the provided value is null.
     */
    public TimeStamp(LocalDateTime value)
    {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    /**
     * Returns the LocalDateTime value encapsulated by the TimeStamp object.
     * @return the LocalDateTime value.
     */
    public LocalDateTime getValue()
    {
        return this.value;
    }

    /**
     * Returns the string representation of the LocalDateTime value.
     *
     * @return the string representation of the LocalDateTime value.
     */
    public String valueToString() {
        return String.valueOf(this.value);
    }

    /**
     * Checks if the provided object is equal to the current TimeStamp object.
     * @param o the object to be compared with the current object.
     * @return true if the provided object is of the same class and its value is equal to the value of the current
     * object.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeStamp timeStamp = (TimeStamp) o;

        return value.equals(timeStamp.value);
    }

    /**
     * Returns a hash code value for the TimeStamp object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(this.value);
    }
}