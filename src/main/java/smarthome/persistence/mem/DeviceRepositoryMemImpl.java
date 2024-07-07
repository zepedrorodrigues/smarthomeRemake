package smarthome.persistence.mem;

import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.room.vo.RoomId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository for devices.
 * <p>
 * This repository is backed by an in-memory HashMap data structure.
 * It is an in-memory implementation of the repository.
 * </p>
 */
public class DeviceRepositoryMemImpl implements IDeviceRepository {

    private final Map<DeviceId, Device> DATA = new HashMap<>();

    /**
     * Saves a device to the repository.
     * <p>
     * The method first checks if the device parameter is null and throws an IllegalArgumentException if it is.
     * It also checks if the device's identity is already in the repository and throws an IllegalArgumentException if it is, not allowing for duplicate identities to be saved.
     * The method then saves the device to the repository and returns the saved device.
     * </p>
     * @param device the device to save
     * @return the saved device
     */
    @Override
    public Device save(Device device) {
        if (device == null || containsIdentity(device.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(device.getIdentity(), device);
        return device;
    }

    /**
     * Finds all devices in the repository.
     * <p>
     * The method returns an iterable collection of all devices in the repository.
     * The method returns an empty collection if no devices are in the repository.
     * </p>
     * @return all devices in the repository
     */
    @Override
    public Iterable<Device> findAll() {
        return DATA.values();
    }

    /**
     * Finds a device by its identity.
     * <p>
     *      * The method first checks if the device parameter is null and throws an IllegalArgumentException if it is.
     * The method returns an optional containing the device with the given identity if it exists in the repository.
     * The method returns an empty optional if no such device exists.
     * </p>
     * @param deviceId the identity of the device.
     * @return the device with the given identity or an empty optional
     * if no such device exists.
     */
    @Override
    public Optional<Device> findByIdentity(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        return Optional.ofNullable(DATA.get(deviceId));
    }

    /**
     * Checks if the repository contains a device with the given identity.
     * <p>
     * The method first checks if the device parameter is null and throws an IllegalArgumentException if it is.
     * The method returns true if the repository contains a device with the given identity, and false otherwise.
     * </p>
     * @param deviceId the identity to check for.
     * @return true if the repository contains a device with the given identity, false otherwise.
     */
    @Override
    public boolean containsIdentity(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        return DATA.containsKey(deviceId);
    }

    /**
     * Finds all devices located in the specified room.
     * <p>
     * The method first checks if the roomID parameter is null and throws an IllegalArgumentException if it is.
     * The method then iterates over all devices in the repository and filters out the devices located in the specified room.
     * The method returns an iterable collection of devices located in the specified room.
     * The method returns an empty collection if no devices are located in the specified room.
     * The method does not return null.
     * The method does not return duplicate devices.
     * The method does not return devices located in other rooms.
     * </p>
     * @param roomID the identity of the room to search for devices.
     * @return an iterable collection of devices located in the specified room.
     * @throws IllegalArgumentException if the roomId parameter is null
     */
    @Override
    public Iterable<Device> findDevicesByRoomId(RoomId roomID) {
        if (roomID == null) {
            throw new IllegalArgumentException();
        }

        return DATA.values().stream()
                .filter(device -> device.getRoomId().equals(roomID))
                .toList();
    }

    /**
     * Finds all device IDs located in the specified room.
     * <p>
     * The method first checks if the roomId parameter is null and throws an IllegalArgumentException if it is.
     * The method then iterates over all devices in the repository and filters out the device IDs located in the specified room.
     * The method returns an iterable collection of device IDs located in the specified room.
     * The method returns an empty collection if no device IDs are located in the specified room.
     * The method does not return null.
     * The method does not return duplicate device IDs.
     * The method does not return device IDs located in other rooms.
     * </p>
     *
     * @param roomId the identity of the room to search for device IDs.
     * @return an iterable collection of device IDs located in the specified room.
     * @throws IllegalArgumentException if the roomId parameter is null
     */
    @Override
    public Iterable<DeviceId> findDeviceIdsByRoomId(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }

        return DATA.values().stream()
                .filter(device -> device.getRoomId().equals(roomId))
                .map(Device::getIdentity)
                .toList();
    }

    /**
     * Updates a device in the repository.
     * <p>
     * The method first checks if the device with the given ID exists in the repository.
     * If it does not exist, it throws an IllegalArgumentException.
     * <p>
     * The method then retrieves the device from the repository, updates its value objects, and saves it back to the repository.
     * The method returns the updated device.
     * The method does not update the device's identity.
     * </p>
     * @param device the device to update
     * @return the updated device
     * @throws IllegalArgumentException if the device ID does not exist in the repository
     */
    @Override
    public Device update(Device device) {
        if (!containsIdentity(device.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(device.getIdentity(), device);
        return device;
    }

    /**
     * Retrieves all device IDs in the repository by the device type name.
     * @param deviceTypeName the name of the device type to search for
     * @return an iterable collection of device IDs with the given device type name
     */
    @Override
    public Iterable<DeviceId> findDeviceIdsByDeviceTypeName(DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null)
            throw new IllegalArgumentException();

        return DATA.values().stream().filter(device -> device.getDeviceTypeName().equals(deviceTypeName))
                .map(Device::getIdentity).toList();
    }

    /**
     * Retrieves all the device IDs in the repository.
     * @return an iterable collection of all device IDs in the repository
     */
    @Override
    public Iterable<DeviceId> findDeviceIds() {
        return DATA.keySet();
    }
}
