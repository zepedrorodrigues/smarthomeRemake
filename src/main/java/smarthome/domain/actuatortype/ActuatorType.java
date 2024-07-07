package smarthome.domain.actuatortype;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;


/**
 * Aggregate root for the actuator type.
 */
public class ActuatorType implements AggregateRoot<ActuatorTypeName> {

    ActuatorTypeName actuatorTypeName;

    /**
     * Constructs an ActuatorType object with the specified actuator type name.
     * The actuator name is used as the identity of the actuator type.
     *
     * @param actuatorTypeName the actuator type name
     * @throws IllegalArgumentException if the actuator type name is null
     */
    public ActuatorType(ActuatorTypeName actuatorTypeName) throws IllegalArgumentException {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        this.actuatorTypeName = actuatorTypeName;
    }


    /**
     * Returns the identity of the actuator type, which is its name.
     *
     * @return the name of the actuator type
     */
    @Override
    public ActuatorTypeName getIdentity() {
        return actuatorTypeName;
    }

    /**
     * Compares this ActuatorType object with another object to determine if they are the same.
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
        final ActuatorType that = (ActuatorType) o;
        return actuatorTypeName.equals(that.actuatorTypeName);
    }

    /**
     * Returns the hash code of the actuator type.
     *
     * @return the hash code of the actuator type
     */
    @Override
    public int hashCode() {
        return actuatorTypeName.hashCode();
    }
}


