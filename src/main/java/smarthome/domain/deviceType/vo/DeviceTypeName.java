package smarthome.domain.deviceType.vo;

import smarthome.ddd.DomainId;

/**
 * Represents the type name of a device, serving as a domain identifier.
 */
public class DeviceTypeName implements DomainId {

    private final String deviceTypeName;

    /**
     * Constructs a DeviceTypeName object with the specified device type name.
     *
     * @param deviceTypeName the device type name
     * @throws IllegalArgumentException if the device type name is null or empty
     */
    public DeviceTypeName(String deviceTypeName) {
        if (deviceTypeName == null || deviceTypeName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.deviceTypeName = deviceTypeName;
    }

    /**
     * Returns the device type name.
     *
     * @return the device type name
     */
    public String getDeviceTypeName() {
        return this.deviceTypeName;
    }

    /**
     * Compares this DeviceTypeName object with another object to determine if they are the same.
     *
     * @param o the object to compare
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceTypeName deviceTypeName1 = (DeviceTypeName) o;
        return deviceTypeName.equals(deviceTypeName1.deviceTypeName);
    }

    /**
     * Returns the hash code of the device type name.
     *
     * @return the hash code of the device type name
     */
    @Override
    public int hashCode() {
        return deviceTypeName.hashCode();
    }
}
