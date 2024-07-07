package smarthome.domain.deviceType;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.deviceType.vo.DeviceTypeName;

/**
 * This class represents an aggregate root DeviceType.
 * It implements the AggregateRoot interface.
 */
public class DeviceType implements AggregateRoot<DeviceTypeName> {

    private final DeviceTypeName deviceTypeName;

    /**
     * Constructs a DeviceType with the given typeName.
     *
     * @param deviceTypeName the type of the device. It must not be null or blank.
     * @throws IllegalArgumentException if the deviceType is not valid (null or blank)
     */
    public DeviceType( DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null) {
            throw new IllegalArgumentException();
        }
        this.deviceTypeName = deviceTypeName;
    }

    /**
     * Returns the identity of the device type, which is its name.
     *
     * @return the name of the actuator type
     */
    @Override
    public DeviceTypeName getIdentity() {
        return deviceTypeName;
    }

    /**
     * Checks if this DeviceType is the same as the given object (same class with same device type).
     *
     * @param o the object to compare with
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        DeviceType that = (DeviceType) o;
        return deviceTypeName.equals(that.deviceTypeName);
    }

    /**
     * Returns the hash code of the device type.
     *
     * @return the hash code of the device type
     */
    @Override
    public int hashCode() {
        return deviceTypeName.hashCode();
    }


}

