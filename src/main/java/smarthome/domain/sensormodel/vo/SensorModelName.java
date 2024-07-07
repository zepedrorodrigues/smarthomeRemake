package smarthome.domain.sensormodel.vo;

import smarthome.ddd.DomainId;

/**
 * The SensorModelName class represents the model name of a sensor in the smart home domain.
 * It implements the DomainId interface, indicating that its equality is based on its value rather than identity.
 * It contains a single field: sensorModelName, represented as a String.
 * The class provides a getter method for this field and an overridden sameAs method for equality check.
 */
public class SensorModelName implements DomainId {

    private final String sensorModelName;

    /**
     * Constructs a SensorModelName object with the given sensorModelName.
     * If the sensorModelName is null or blank, it throws an IllegalArgumentException.
     *
     * @param sensorModelName the model name of the sensor
     */
    public SensorModelName(String sensorModelName) {
        if (sensorModelName == null || sensorModelName.isBlank()) {
            throw new IllegalArgumentException();

        }
        this.sensorModelName = sensorModelName;
    }

    /**
     * Returns the model name of the sensor.
     *
     * @return the model name of the sensor
     */
    public String getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the hash code value for this SensorModelName object.
     *
     * @return the hash code value for this SensorModelName object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorModelName that = (SensorModelName) o;
        return sensorModelName.equals(that.sensorModelName);
    }

    /**
     * Returns the hash code value for this SensorModelName object.
     *
     * @return the hash code value for this SensorModelName object
     */
    @Override
    public int hashCode() {
        return sensorModelName.hashCode();
    }
}
