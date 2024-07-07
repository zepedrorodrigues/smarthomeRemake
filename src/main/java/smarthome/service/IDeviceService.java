package smarthome.service;

import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service interface for managing devices in a smart home system through RESTful endpoints.
 */
public interface IDeviceService {

    /**
     * Adds a device to a room.
     *
     * @param deviceName the name of the device
     * @param deviceType the type of the device
     * @param roomId     the ID of the room to add the device to
     * @return the added device
     */
    Device addDeviceToRoom(DeviceName deviceName, DeviceTypeName deviceType, RoomId roomId);

    /**
     * Retrieves all devices located in a specified room.
     *
     * @param roomId the ID of the room to query devices in
     * @return a list of devices in the specified room
     */
    Iterable<Device> getDevicesInRoom(RoomId roomId);

    /**
     * Retrieves all device IDs located in a specified room.
     *
     * @param roomId the ID of the room to query device IDs in
     * @return a list of device IDs in the specified room
     */
    Iterable<DeviceId> getDeviceIdsInRoom(RoomId roomId);

    /**
     * Deactivates a specified device.
     *
     * @param deviceId the ID of the device to deactivate
     * @return the deactivated device
     */
    Device deactivateDevice(DeviceId deviceId);

    /**
     * Retrieves devices categorized by their sensor types.
     *
     * @return a HashMap where each key is a sensor type name, and the value is a set of devices
     * that have sensors of that type
     * @throws IllegalAccessException if there is issue accessing sensor data
     */
    HashMap<String, List<Device>> getDevicesBySensorType() throws IllegalAccessException;

    /**
     * Retrieves a device by its ID.
     * @param deviceId the ID of the device to retrieve
     * @return the device with the specified ID
     */
    Optional<Device> getDeviceById(DeviceId deviceId);

    /**
     * Retrieves all device IDs in the system.
     * @return a list of all device IDs
     */
    List<DeviceId> findDeviceIds();
}
