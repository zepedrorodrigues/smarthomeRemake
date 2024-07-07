package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.sensortype.SensorType;

/**
 * This class represents the SensorType entity in the database.
 * It maps the SensorType domain model to a format that can be stored in the database.
 */
@Entity
@Table(name = "SENSOR_TYPE")
public class SensorTypeDataModel {
    @Id
    private String sensorTypeId;

    private String sensorTypeName;
    private String sensorTypeUnit;

    /**
     * Default constructor for the SensorTypeDataModel class.
     * This is required by JPA.
     */
    public SensorTypeDataModel() {
    }

    /**
     * Constructor for the SensorTypeDataModel class.
     * It takes a SensorType domain model as a parameter and initializes the sensorTypeName field.
     *
     * @param sensorType The SensorType domain model.
     */
    public SensorTypeDataModel(SensorType sensorType) {
        this.sensorTypeId = sensorType.getIdentity().getSensorTypeId();
        this.sensorTypeName = sensorType.getSensorTypeName().getSensorTypeName();
        this.sensorTypeUnit = sensorType.getSensorTypeUnit().getSensorTypeUnit();
    }

    /**
     * This method returns the id of the SensorType.
     *
     * @return The id of the SensorType.
     */
    public String getSensorTypeId() {
        return sensorTypeId;
    }

    /**
     * This method returns the name of the SensorType.
     *
     * @return The name of the SensorType.
     */
    public String getSensorTypeName() {
        return sensorTypeName;
    }

    /**
     * This method returns the unit of the SensorType.
     *
     * @return The unit of the SensorType.
     */
    public String getSensorTypeUnit() {
        return sensorTypeUnit;
    }
}
