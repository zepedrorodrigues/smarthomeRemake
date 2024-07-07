package smarthome.domain.actuatormodel;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

/**
 * Aggregate root for the actuator model.
 */
public class ActuatorModel implements AggregateRoot<ActuatorModelName> {


    private final ActuatorModelName actuatorModelName;
    private final ActuatorTypeName actuatorTypeName;

    /**
     * Constructs an ActuatorModel object with the specified actuator model name.
     *
     * @param actuatorModelName the actuator model name, which is the identity of the actuator model
     * @param actuatorTypeName  the actuator type name, which is the identity of the actuator type
     * @throws IllegalArgumentException if the actuator model name is null
     */
    public ActuatorModel(ActuatorModelName actuatorModelName, ActuatorTypeName actuatorTypeName) {
        if (actuatorModelName == null || actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        this.actuatorModelName = actuatorModelName;
        this.actuatorTypeName = actuatorTypeName;
    }

    /**
     * Retrieves the actuator model name, that is the identity of the actuator model.
     *
     * @return the actuator model name
     */
    @Override
    public ActuatorModelName getIdentity() {
        return actuatorModelName;
    }

    /**
     * Retrieves the actuator type name, that is the identity of the actuator type.
     *
     * @return the actuator type name
     */
    public ActuatorTypeName getActuatorTypeName() {
        return this.actuatorTypeName;
    }

    /**
     * Compares this ActuatorModel object with another object to determine if they are the same.
     *
     * @param o the object to compare
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActuatorModel that = (ActuatorModel) o;
        return actuatorModelName.equals(that.actuatorModelName);
    }

    /**
     * Returns the hash code of the actuator model.
     *
     * @return the hash code of the actuator model
     */
    @Override
    public int hashCode() {
        return actuatorModelName.hashCode();
    }
}
