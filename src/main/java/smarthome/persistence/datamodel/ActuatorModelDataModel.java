package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.actuatormodel.ActuatorModel;

/**
 * The ActuatorModelDataModel class represents a data model entity for an actuator.
 * It maps to the ACTUATOR table in the database and provides fields corresponding
 * to actuator attributes such as actuatorId, deviceId, actuatorModelName, etc.
 */
@Entity
@Table(name = "ACTUATOR_MODEL")
public class ActuatorModelDataModel {

    @Id
    private String actuatorModelName;
    private String actuatorTypeName;

    /**
     * Constructs a new ActuatorModelDataModel instance.
     */
    public ActuatorModelDataModel() {
    }

    /**
     * Constructs a new ActuatorModelDataModel instance based on the provided ActuatorModel domain object.
     *
     * @param actuatorModel The ActuatorModel domain object from which to initialize the ActuatorModelDataModel.
     */
    public ActuatorModelDataModel(ActuatorModel actuatorModel) {
        this.actuatorModelName = actuatorModel.getIdentity().getActuatorModelName();
        this.actuatorTypeName = actuatorModel.getActuatorTypeName().getActuatorTypeName();
    }

    /**
     * Returns the unique identifier of the actuator.
     *
     * @return the unique identifier of the actuator
     */
    public String getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the name of the actuator.
     *
     * @return the name of the actuator
     */
    public String getActuatorTypeName() {
        return actuatorTypeName;
    }
}
