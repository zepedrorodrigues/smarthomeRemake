package smarthome.domain.sensortype.vo;

import smarthome.ddd.ValueObject;

/**
 * SensorTypeName class.
 * This class represents the name of a sensor type.
 */
public class SensorTypeName implements ValueObject {
    private final String sensorTypeName;

    /**
     * Constructs a new SensorTypeName.
     *
     * @param sensorTypeName the name of the sensor type
     * @throws IllegalArgumentException if the sensor type name is null or blank
     */
    public SensorTypeName(String sensorTypeName) {
        if (sensorTypeName == null || sensorTypeName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.sensorTypeName = sensorTypeName;

    }

    /**
     * Returns the sensor type name.
     *
     * @return the sensor type name
     */
    public String getSensorTypeName() {
        return sensorTypeName;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as
     * those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorTypeName that = (SensorTypeName) o;
        return sensorTypeName.equals(that.sensorTypeName);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as
     * those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return sensorTypeName.hashCode();
    }
}

