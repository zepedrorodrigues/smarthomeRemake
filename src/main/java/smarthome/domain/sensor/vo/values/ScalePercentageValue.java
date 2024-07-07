package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents the value of a scale percentage.
 */
public class ScalePercentageValue implements Value {


    private final double scalePercentageValue;

    /**
     * Constructs a new ScalePercentageValue.
     *
     * @param scalePercentageValue the scale percentage value
     */
    public ScalePercentageValue(double scalePercentageValue) {
        this.scalePercentageValue = scalePercentageValue;
    }

    /**
     * Method that compares two ScalePercentageValue objects
     *
     * @param o the object to compare
     * @return true if the objects are the same or have the same scale percentage value, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScalePercentageValue that = (ScalePercentageValue) o;
        return Double.compare(scalePercentageValue, that.scalePercentageValue) == 0;
    }

    /**
     * Method that returns the hash code of the scale percentage value
     *
     * @return the hash code of the scale percentage value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(scalePercentageValue);
    }

    /**
     * Returns the string representation of the scale percentage value.
     *
     * @return the string representation of the scale percentage value
     */
    @Override
    public String valueToString() {
        return String.valueOf(scalePercentageValue);
    }
}
