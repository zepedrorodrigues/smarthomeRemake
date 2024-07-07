package smarthome.domain.deviceType;

import org.springframework.stereotype.Component;
import smarthome.domain.deviceType.vo.DeviceTypeName;

/**
 * Represents a factory for creating device types.
 */
@Component
public class DeviceTypeFactoryImpl implements DeviceTypeFactory{

    /**
     * Constructs a DeviceTypeFactoryImpl object.
     */
    public DeviceTypeFactoryImpl() {
    }

    /**
     * Creates a device type with the specified device type name.
     * @param deviceTypeName the name of the device type
     * @return the created device type
     */
    @Override
    public DeviceType createDeviceType(DeviceTypeName deviceTypeName) {
        return new DeviceType(deviceTypeName);
    }
}
