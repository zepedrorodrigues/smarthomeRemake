package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;

/**
 * Repository for devices.
 */
public interface IDeviceRepository extends IRepository<DeviceId, Device> {

    /**
     * Finds all devices located in the specified room.
     * <p>
     * @param roomId the identity of the room to search for devices.
     * @return an iterable collection of devices located in the specified room.
     */
    Iterable<Device> findDevicesByRoomId(RoomId roomId);

    /**
     * Finds all device IDs located in the specified room.
     * <p>
     * @param roomId the identity of the room to search for device IDs.
     * @return an iterable collection of device IDs located in the specified room.
     */
    Iterable<DeviceId> findDeviceIdsByRoomId(RoomId roomId);

    /**
     * Updates the status of a device.
     * <p>
     * @param device the device to update.
     * @return the updated device.
     */
    Device update(Device device);

    /**
     * Retrieves all device IDs of a specified type.
     * <p>
     * @param deviceTypeName the ID of the device type to search for.
     * @return an iterable collection of device IDs of the specified type.
     */
    Iterable<DeviceId> findDeviceIdsByDeviceTypeName(DeviceTypeName deviceTypeName);

    /**
     * Retrieves all device IDs in the repository.
     * <p>
     * @return an iterable collection of all device IDs.
     */
    Iterable<DeviceId> findDeviceIds();
}
