package smarthome.domain.device;

import org.springframework.stereotype.Component;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;

/**
 * A factory class for creating Device objects.
 */
@Component
public class DeviceFactoryImpl implements DeviceFactory {

    @Override
    public Device createDevice(DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId) {
        return new Device(deviceName, deviceTypeName, roomId);
    }
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

    @Override
    public Device createDevice(DeviceId deviceId, DeviceName deviceName, DeviceTypeName deviceTypeName, RoomId roomId, DeviceStatus deviceStatus) {
        return new Device(deviceId, deviceName, deviceTypeName, roomId, deviceStatus);
    }
}
