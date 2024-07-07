package smarthome.domain.device;

import lombok.Getter;
import smarthome.ddd.AggregateRoot;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;

import java.util.UUID;

/**
 * Represents a device within a smart home environment.
 * A device is considered an aggregate root in the domain-driven design context,
 * uniquely identified by a {@link DeviceId}. It also includes additional
 * attributes such as the {@link DeviceName} it has, its {@link DeviceTypeName},
 * its {@link DeviceStatus} and the {@link RoomName} where it is located.
 */
@Getter
public class Device implements AggregateRoot<DeviceId> {
    private final DeviceId deviceId;
    private DeviceStatus deviceStatus;
    private final DeviceName deviceName;
    private final DeviceTypeName deviceTypeName;
    private final RoomId roomId;

    /**
     * Constructs a Device object with the given parameters.
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     * @param roomId the identifier of the room where the device is located
     *               @throws IllegalArgumentException if any of the parameters is null
     */

    protected Device(DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId) {
        if (validParameters(deviceName, deviceTypeName, roomId)) {
            this.deviceName = deviceName;
            this.deviceTypeName = deviceTypeName;
            this.roomId = roomId;
        } else {
            throw new IllegalArgumentException();
        }
        this.deviceStatus = new DeviceStatus(true);
        this.deviceId = new DeviceId(UUID.randomUUID().toString());
    }

    /**
     * Constructs a Device object with the given parameters.
     * This constructor is typically used when retrieving a device from the database.
     *
     * @param deviceId the unique identifier of the device
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     * @param roomId the identifier of the room where the device is located
     * @param deviceStatus the current status of the device
     */

    protected Device(DeviceId deviceId, DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId,
                     DeviceStatus deviceStatus) {
        this.deviceName = deviceName;
        this.deviceTypeName = deviceTypeName;
        this.roomId = roomId;
        this.deviceStatus = deviceStatus;
        this.deviceId = deviceId;
    }

    /**
     * Checks if the parameters are valid to create a device.
     *
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     * @param roomId     the room id where the device is
     * @return true if all parameters are valid, false otherwise
     */

    private static boolean validParameters(DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId) {
        return deviceName != null && deviceTypeName != null && roomId != null;
    }

    /**
     * This method is used to get the unique identifier of the device.
     * @return the unique identifier of the device
     */
    @Override
    public DeviceId getIdentity() {
        return this.deviceId;
    }

    /**
     * Deactivates the device. If the device is already deactivated, an
     * IllegalArgumentException is thrown.
     */
    public void deactivateDevice() {
        if (this.deviceStatus.getStatus()) {
            this.deviceStatus = new DeviceStatus(false);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if the given object is equal to this device.
     * Two devices are considered equal if they have the same device id.
     *
     * @param object the object to compare with
     * @return true if the given object is equal to this device, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Device device = (Device) object;
        return deviceId.equals(device.deviceId);
    }

    /**
     * Returns the hash code of this device.
     * The hash code is computed based on the device id.
     *
     * @return the hash code of this device
     */
    @Override
    public int hashCode() {
        return deviceId.hashCode();
    }
}