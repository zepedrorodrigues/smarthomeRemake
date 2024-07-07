package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * This class represents a solar irradiance value.
 * It implements the Value interface.
 */
public class SolarIrradianceValue implements Value {

    private final double solarIrradianceValue;

    /**
     * Constructs a new SolarIrradianceValue.
     * @param solarIrradianceValue the solar irradiance value. Must be non-negative.
     * @throws IllegalArgumentException if the provided value is negative.
     */
    public SolarIrradianceValue(double solarIrradianceValue) {
        if (solarIrradianceValue < 0) {
            throw new IllegalArgumentException();
        }
        this.solarIrradianceValue = solarIrradianceValue;
    }

    /**
     * Returns the string representation of the solar irradiance value.
     * @return the string representation of the solar irradiance value.
     */
    @Override
    public String valueToString() {
        return Double.toString(solarIrradianceValue);
    }

    /**
     * Checks if this SolarIrradianceValue is equal to the provided object.
     * @param o the object to compare with.
     * @return true if the provided object is a SolarIrradianceValue with the same value, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolarIrradianceValue that = (SolarIrradianceValue) o;
        return Double.compare(solarIrradianceValue, that.solarIrradianceValue) == 0;
    }

    /**
     * Returns the hash code of the solar irradiance value.
     * @return the hash code of the solar irradiance value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(solarIrradianceValue);
    }
}
