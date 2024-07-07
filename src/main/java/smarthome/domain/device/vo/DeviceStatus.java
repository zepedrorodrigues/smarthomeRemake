package smarthome.domain.device.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * A value object that is used to represent the deviceStatus of a device.
 */
public class DeviceStatus implements ValueObject {

    private final boolean status;

    /**
     * Constructor of the class DeviceStatus
     *
     * @param status boolean that represents the deviceStatus of the device
     */
    public DeviceStatus(boolean status) {
        this.status = status;
    }

    /**
     * Method that returns the deviceStatus of the device
     * True if the device is active, false otherwise
     *
     * @return boolean that represents the deviceStatus of the device
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Method that checks if the object is the same as the deviceStatus
     *
     * @param o Object that represents the deviceStatus
     * @return boolean that represents if the object is the same as the deviceStatus
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceStatus that = (DeviceStatus) o;
        return status == that.status;
    }

    /**
     * Method that returns the hash code of the deviceStatus
     *
     * @return int that represents the hash code of the deviceStatus
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(status);
    }
}
