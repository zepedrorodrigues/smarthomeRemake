package smarthome.persistence.mem;

import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceTypeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The DeviceTypeRepositoryMemImpl class represents a repository for managing DeviceType entities.
 * DeviceTypeRepositoryMemImpl is a class that implements the IRepository interface.
 */
public class DeviceTypeRepositoryMemImpl implements IDeviceTypeRepository {

    private final Map<DeviceTypeName, DeviceType> DATA = new HashMap<>();

    /**
     * Saves a device type to the repository.
     *
     * @param deviceType the device type to save
     * @return the saved device type
     */
    @Override
    public DeviceType save(DeviceType deviceType) {
        if (deviceType == null || containsIdentity(deviceType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(deviceType.getIdentity(), deviceType);
        return deviceType;
    }

    /**
     * Finds all device types in the repository.
     *
     * @return all device types in the repository
     */
    @Override
    public Iterable<DeviceType> findAll() {
        return  DATA.values();
    }

    /**
     * Returns all device type names in the repository.
     *
     * @return all device type names in the repository
     */
    @Override
    public Iterable<DeviceTypeName> findDeviceTypeNames() {
        return DATA.keySet();
    }

    /**
     * Finds a device type by its identity.
     *
     * @param deviceTypeName the identity of the device type
     * @return the device type with the given identity or an empty optional if no such device type exists
     */
    @Override
    public Optional<DeviceType> findByIdentity(DeviceTypeName deviceTypeName) {
        if(deviceTypeName == null) {
            throw new IllegalArgumentException();
        }
        DeviceType deviceType = DATA.get(deviceTypeName);
        return Optional.ofNullable(deviceType);
    }

    /**
     * Checks if the repository contains a device type with the given identity.
     *
     * @param deviceTypeName the identity to check for
     * @return true if the repository contains a device type with the given identity, false otherwise
     */
    @Override
    public boolean containsIdentity(DeviceTypeName deviceTypeName) {
        if(deviceTypeName == null) {
            throw new IllegalArgumentException();
        }
         return DATA.containsKey(deviceTypeName);
    }
}
