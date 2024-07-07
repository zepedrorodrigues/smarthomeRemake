package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import smarthome.domain.device.Device;

/**
 * The DeviceDataModel class represents a data model entity for a device.
 * It maps to the DEVICE table in the database and provides fields corresponding
 * to device attributes such as deviceId, roomId, deviceName, deviceType, and deviceStatus.
 */

@Entity
@Table(name = "DEVICE")
@Getter
public class DeviceDataModel {

    @Id
    private String deviceId;

    private String roomIdentity;
    private String deviceName;
    private String deviceTypeName;
    private boolean deviceStatus;

    /**
     * Constructs a new DeviceDataModel instance.
     */
    public DeviceDataModel() {
        // Empty constructor
    }

    /**
     * Constructs a new DeviceDataModel instance based on the provided Device domain object.
     *
     * @param device The Device domain object from which to initialize the DeviceDataModel.
     */
    public DeviceDataModel(Device device) {
        this.deviceId = device.getIdentity().getIdentity();
        this.deviceName = device.getDeviceName().getDeviceName();
        this.deviceTypeName = device.getDeviceTypeName().getDeviceTypeName();
        this.roomIdentity = device.getRoomId().getRoomId();
        this.deviceStatus = device.getDeviceStatus().getStatus();
    }

    /**
     * Updates the status of the device based on the given domain object.
     *
     * @param device the domain object from which to update the status
     * @return true if the status was updated successfully, false otherwise
     */
    public boolean updateDeviceFromDomain(Device device) {
        if (device == null || !this.deviceId.equals(device.getIdentity().getIdentity())) {
            return false;
        }
        try {
            this.deviceName = device.getDeviceName().getDeviceName();
            this.deviceTypeName = device.getDeviceTypeName().getDeviceTypeName();
            this.roomIdentity = device.getRoomId().getRoomId();
            this.deviceStatus = device.getDeviceStatus().getStatus();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the status of the device.
     * @return the status of the device. If the device is active, it returns true. Otherwise, it returns false.
     */
    public boolean getDeviceStatus() {
        return deviceStatus;
    }
}



