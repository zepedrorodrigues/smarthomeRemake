package smarthome.service;

import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;

import java.util.List;
import java.util.Optional;

/**
 * Service interface used to define the methods for managing device types.
 */
public interface IDeviceTypeService {
    /**
     * Get the list of device type names.
     *
     * @return the list of device types names
     */
    List<DeviceTypeName> getDeviceTypeNames();

    /**
     * Get a device type by its identity.
     *
     * @param id The identity of the device type.
     * @return The device type entity.
     */
    Optional<DeviceType> getDeviceTypeById(DeviceTypeName id);
}