package smarthome.domain.deviceType;

import smarthome.domain.deviceType.vo.DeviceTypeName;

/**
 * Represents a factory for creating device types.
 */
public interface DeviceTypeFactory {

    /**
     * Creates a device type with the specified device type name.
     * @param deviceTypeName the name of the device type
     * @return the created device type
     */
    DeviceType createDeviceType(DeviceTypeName deviceTypeName);
}
