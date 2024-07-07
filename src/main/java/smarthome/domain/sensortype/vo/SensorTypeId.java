package smarthome.domain.sensortype.vo;

import smarthome.ddd.DomainId;

/**
 * Represents a sensor type id.
 */
public class SensorTypeId implements DomainId {

    private final String id;

    /**
     * Constructs a SensorTypeId object with the specified id value.
     *
     * @param id the sensor type identifier
     * @throws IllegalArgumentException if the identifier is null or blank
     */
    public SensorTypeId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * Retrieves the sensor type identifier.
     *
     * @return the sensor type identifier
     */
    public String getSensorTypeId() {
        return id;
    }

    /**
     * Determines if this SensorTypeId object is equal to another object.
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
        final SensorTypeId that = (SensorTypeId) o;
        return id.equals(that.id);
    }

    /**
     * Returns the hash code of the SensorTypeId
     *
     * @return the hash code of the SensorTypeId
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
