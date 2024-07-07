package smarthome.domain.sensor.vo;

import smarthome.ddd.DomainId;

/**
 * Represents a sensor id.
 */
public class SensorId implements DomainId {

    private final String id;

    /**
     * Constructs a SensorId object with the specified id value.
     *
     * @param id the sensor identifier
     * @throws IllegalArgumentException if the identifier is null or blank
     */
    public SensorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * Retrieves the sensor identifier.
     *
     * @return the sensor identifier
     */
    public String getSensorId() {
        return id;
    }

    /**
     * Determines if this SensorId object is equal to another object.
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
        final SensorId sensorId1 = (SensorId) o;
        return id.equals(sensorId1.id);
    }

    /**
     * Returns the hash code of the SensorId.
     *
     * @return the hash code of the SensorId
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
