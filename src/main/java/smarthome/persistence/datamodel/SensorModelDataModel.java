package smarthome.persistence.datamodel;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.sensormodel.SensorModel;

/**
 * The SensorModelDataModel class represents a data model entity for a sensor model.
 * It maps to the SENSOR_MODEL table in the database and provides fields corresponding
 * to sensor model attributes such as sensorModelName and sensorTypeId.
 *
 * @Entity annotation specifies that the class is an entity and is mapped to a database table.
 * @Table annotation specifies the name of the database table to be used for mapping.
 */
@Entity
@Table(name = "SENSOR_MODEL")
public class SensorModelDataModel {

    @Id
    private String sensorModelName;

    private String sensorTypeId;

    /**
     * Default constructor.
     */
    public SensorModelDataModel() {
    }

    /**
     * Constructs a SensorModelDataModel object with the given SensorModel.
     *
     * @param sensorModel The SensorModel object to be used for creating the SensorModelDataModel.
     */
    public SensorModelDataModel(SensorModel sensorModel) {
        this.sensorModelName = sensorModel.getIdentity().getSensorModelName();
        this.sensorTypeId = sensorModel.getSensorTypeId().getSensorTypeId();
    }

    /**
     * Returns the model name of the sensor.
     *
     * @return the model name of the sensor
     */
    public String getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the type id of the sensor.
     *
     * @return the type id of the sensor
     */
    public String getSensorTypeId() {
        return sensorTypeId;
    }

}
