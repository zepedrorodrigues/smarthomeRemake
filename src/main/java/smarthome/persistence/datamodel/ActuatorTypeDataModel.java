package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.actuatortype.ActuatorType;

/**
 * This class represents the ActuatorType entity in the database.
 * It maps the ActuatorType domain model to a format that can be stored in the database.
 */
@Entity
@Table(name = "ACTUATOR_TYPE")
public class ActuatorTypeDataModel {
    @Id
    private String actuatorTypeName;

    /**
     * Default constructor for the ActuatorTypeDataModel class.
     * This is required by JPA.
     */
    public ActuatorTypeDataModel() {
    }

    /**
     * Constructor for the ActuatorTypeDataModel class.
     * It takes an ActuatorType domain model as a parameter and initializes the actuatorTypeName field.
     *
     * @param actuatorType The ActuatorType domain model.
     */
    public ActuatorTypeDataModel(ActuatorType actuatorType) {
        this.actuatorTypeName = actuatorType.getIdentity().toString();
    }

    /**
     * This method returns the name of the ActuatorType.
     *
     * @return The name of the ActuatorType.
     */
    public String getActuatorTypeName() {
        return actuatorTypeName;
    }
}