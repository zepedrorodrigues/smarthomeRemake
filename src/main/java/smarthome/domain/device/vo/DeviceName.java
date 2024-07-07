package smarthome.domain.device.vo;

import smarthome.ddd.ValueObject;

/**
 * This class represents a Value Object DeviceName.
 * It implements the ValueObject interface.
 */
public class DeviceName implements ValueObject {

    private final String deviceName;

    /**
     * Constructs a DeviceName with the given name.
     *
     * @param deviceName the name of the device. It must not be null or blank.
     * @throws IllegalArgumentException if the deviceName is not valid (null or blank)
     */
    public DeviceName(String deviceName) {
        if (deviceName == null || deviceName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.deviceName = deviceName;
    }

    /**
     * Returns the device name.
     *
     * @return the device name
     */
    public String getDeviceName() {
        return this.deviceName;
    }

    /**
     * Checks if this DeviceName is the same as the given object (same class with same device name).
     *
     * @param o the object to compare with
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceName that = (DeviceName) o;
        return deviceName.equals(that.deviceName);
    }

    /**
     * Returns the hash code of the device name.
     *
     * @return the hash code of the device name
     */
    @Override
    public int hashCode() {
        return deviceName.hashCode();}
}
