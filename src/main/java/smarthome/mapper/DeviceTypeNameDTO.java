package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * This class represents a Data Transfer Object (DTO) for a Device Type Name.
 */
public class DeviceTypeNameDTO extends RepresentationModel<DeviceTypeNameDTO> {

    private String deviceTypeName;

    /**
     * Constructs a new DeviceTypeNameDTO with the given parameters.
     * This constructor is used when the device type is already created and therefore has a name.
     *
     * @param deviceTypeName the name of the device type
     */
    public DeviceTypeNameDTO(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    /**
     * Empty constructor.
     * Needed for the REST API to work and for the Jackson library to automatically convert JSON
     * to a DeviceDTO object.
     */
    public DeviceTypeNameDTO() {
        // Empty constructor
    }

    /**
     * Returns the name of the device type.
     *
     * @return the name of the device type
     */
    public String getDeviceTypeName() {
        return deviceTypeName;
    }
}
