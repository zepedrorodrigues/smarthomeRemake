package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data transfer object for the id of a reading.
 */
public class DeviceIdDTO extends RepresentationModel<DeviceIdDTO> {

    private final String deviceId;

    /**
     * Constructs a new DeviceIdDTO from the given deviceId.
     * This constructor is used when the device is already created and therefore has an id.
     * It provides a convenient way to create DeviceDTO instances that only contain the deviceId.
     * <p>
     *
     * @param deviceId
     */
    public DeviceIdDTO(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Get id of the device.
     *
     * @return the id of the device
     */
    public String getDeviceId() {
        return this.deviceId;
    }
}
