package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * This class represents a Data Transfer Object (DTO) for a Device.
 * It is used to transfer data between different layers of the application.
 */
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private String roomId;
    private String deviceName;
    private String deviceTypeName;
    private String deviceId;
    private boolean deviceStatus;

    /**
     * Constructs a new DeviceDTO with the given parameters.
     * This constructor is used when the device is already created and therefore has an id and a status.
     * <p>
     * @param deviceId       the unique identifier of the device
     * @param deviceName     the name of the device
     * @param deviceTypeName     the type of the device
     * @param roomId the location of the device
     * @param deviceStatus   the status of the device
     */
    public DeviceDTO(String deviceId, String deviceName, String deviceTypeName, String roomId, boolean deviceStatus) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceTypeName = deviceTypeName;
        this.roomId = roomId;
        this.deviceStatus = deviceStatus;
    }

    /**
     * Constructs a new DeviceDTO with the given parameters.
     * This constructor is used when the device is not yet created and therefore does not have an id and a status.
     * <p>
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     * @param roomId   the location of the device
     */
    public DeviceDTO(String deviceName, String deviceTypeName, String roomId) {
        this.deviceName = deviceName;
        this.deviceTypeName = deviceTypeName;
        this.roomId = roomId;
    }

    /**
     * Constructs a new DeviceDTO with the given parameters.
     * This constructor is used when the device is not yet created and therefore does not have an id and a status.
     *
     * @param deviceName the name of the device
     * @param deviceTypeName the type of the device
     */
    public DeviceDTO(String deviceName, String deviceTypeName) {
        this.deviceName = deviceName;
        this.deviceTypeName = deviceTypeName;
    }

    /**
     * Empty constructor. Needed for the REST API to work and for the Jackson library to automatically convert JSON
     * to a DeviceDTO object.
     */
    public DeviceDTO() {
    }

    /**
     * Returns the unique identifier of the device.
     *
     * @return the unique identifier of the device
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the name of the device.
     *
     * @return the name of the device
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Returns the type of the device.
     *
     * @return the type of the device
     */
    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    /**
     * Returns the location of the device.
     *
     * @return the location of the device
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Returns the status of the device.
     *
     * @return the status of the device
     */
    public boolean getDeviceStatus() {
        return deviceStatus;
    }
}