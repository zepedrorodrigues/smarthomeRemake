package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.sensor.Sensor;


/**
 * This class represents the Sensor data model in the database.
 * It is annotated as an Entity, meaning it is a JPA entity and is mapped to a database table.
 * The table name is defined as "SENSOR".
 */
@Entity
@Table(name = "SENSOR")
public class SensorDataModel {

    @Id
    private String sensorId;
    private String deviceId;
    private String sensorModelName;

    /**
     * Default constructor for SensorDataModel.
     */
    public SensorDataModel() {

    }

    /**
     * Constructor for SensorDataModel that takes a Sensor object as input.
     * It initializes the sensorId, deviceId, and sensorModelName fields with the corresponding values from the
     * Sensor object.
     * @param sensor The Sensor object
     */
    public SensorDataModel( Sensor sensor) {
        sensorId = sensor.getIdentity().getSensorId();
        deviceId = sensor.getDeviceId().getIdentity();
        sensorModelName = sensor.getSensorModelName().getSensorModelName();
    }

    /**
     * This method returns the sensor id.
     *
     * @return The id of the Sensor.
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * This method returns the device id.
     *
     * @return The id of the Device.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * This method returns the sensor model name.
     *
     * @return The SensorModelName.
     */
    public String getSensorModelName() {
        return sensorModelName;
    }

}
