package smarthome.domain.sensortype.vo;

import smarthome.ddd.ValueObject;

/**
 * Value object representing a sensor type unit.
 */
public class SensorTypeUnit implements ValueObject {

    private final String unit;

    /**
     * Constructs a SensorTypeUnit object with the specified unit.
     *
     * @param unit the unit to set
     * @throws IllegalArgumentException if the unit is null or blank
     */
    public SensorTypeUnit(String unit) {
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.unit = unit;
    }

    /**
     * Retrieves the unit.
     *
     * @return the unit
     */
    public String getSensorTypeUnit() {
        return this.unit;
    }

    /**
     * Compares this SensorTypeUnit object with another object to determine if they are equal.
     *
     * @param o the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorTypeUnit that = (SensorTypeUnit) o;
        return this.unit.equals(that.unit);
    }

    /**
     * Generates a hash code for the SensorTypeUnit object.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return this.unit.hashCode();
    }
}
