package smarthome.domain.device;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;

/**
 * A factory interface for creating Device objects.
 */
public interface DeviceFactory {

    /**
     * Creates a Device object based on the given parameters.
     *
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     * @param roomId the identifier of the room where the device is located
     * @return a Device object with the specified parameters
     */
    Device createDevice(DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId);

    /**
     * Creates a Device object based on the given parameters.
     *
     * @param deviceId the identifier of the device
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     * @param roomId the identifier of the room where the device is located
     * @param deviceStatus the status of the device
     * @return a Device object with the specified parameters
     */
    Device createDevice(DeviceId deviceId, DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId
            , DeviceStatus deviceStatus);
}

