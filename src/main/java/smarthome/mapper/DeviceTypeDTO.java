package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * This class represents a Data Transfer Object (DTO) for a device type.
 */
public class DeviceTypeDTO extends RepresentationModel<DeviceTypeDTO> {

    private String deviceTypeName;

    /**
     * Constructs an empty DeviceTypeDTO.
     */
    public DeviceTypeDTO() {
        // Empty constructor
    }

    /**
     * Constructs a new DeviceTypeDTO with the specified device type name.
     *
     * @param deviceTypeName the name of the device type
     */
    public DeviceTypeDTO(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    /**
     * Returns the name of the device type.
     *
     * @return the name of the device type
     */
    public String getDeviceTypeName() {
        return this.deviceTypeName;
    }
}
