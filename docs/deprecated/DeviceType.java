package smarthome.domain.device.vo;

import smarthome.ddd.ValueObject;

/**
 * This class represents a Value Object DeviceType.
 * It implements the ValueObject interface.
 */
public class DeviceType implements ValueObject {

    private final String deviceType;

    /**
     * Constructs a DeviceType with the given type.
     *
     * @param deviceType the type of the device. It must not be null or blank.
     * @throws IllegalArgumentException if the deviceType is not valid (null or blank)
     */
    public DeviceType( String deviceType) {
        if (deviceType == null || deviceType.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.deviceType = deviceType;
    }

    /**
     * Returns the device type.
     *
     * @return the device type
     */
    public String getDeviceType() {
        return this.deviceType;
    }

    /**
     * Checks if this DeviceType is the same as the given object (same class with same device type).
     *
     * @param o the object to compare with
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceType that = (DeviceType) o;
        return deviceType.equals(that.deviceType);
    }

    /**
     * Returns the hash code of the device type.
     *
     * @return the hash code of the device type
     */
    @Override
    public int hashCode() {
        return deviceType.hashCode();
    }
}
