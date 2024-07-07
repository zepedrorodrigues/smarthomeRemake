package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.deviceType.DeviceType;

/**
 * This class represents the DeviceType entity in the database.
 * It maps the DeviceType domain model to a format that can be stored in the database.
 */
@Entity
@Table(name = "DEVICE_TYPE")
public class DeviceTypeDataModel {

    @Id
    private String deviceTypeName;

    /**
     * Default constructor for the DeviceTypeDataModel class.
     * This is required by JPA.
     */
    public DeviceTypeDataModel() {
    }

    /**
     * Constructor for the DeviceTypeDataModel class.
     * It takes a DeviceType domain model as a parameter and initializes the deviceTypeName field.
     *
     * @param deviceType The DeviceType domain model.
     */
    public DeviceTypeDataModel(DeviceType deviceType) {
        this.deviceTypeName = deviceType.getIdentity().toString();
    }

    /**
     * This method returns the name of the DeviceType.
     *
     * @return The name of the DeviceType.
     */
    public String getDeviceTypeName() {
        return deviceTypeName;
    }
}
