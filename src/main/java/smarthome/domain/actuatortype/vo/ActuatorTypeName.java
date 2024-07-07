package smarthome.domain.actuatortype.vo;

import smarthome.ddd.DomainId;


/**
 * Represents the type name of an actuator, serving as a domain identifier.
 */
public class ActuatorTypeName implements DomainId {

    private final String actuatorTypeName;

    /**
     * Constructs an ActuatorTypeName with the specified actuator type name.
     *
     * @param actTypeName the name of the actuator type
     * @throws IllegalArgumentException if the provided actuator type name is null or empty
     */
    public ActuatorTypeName(String actTypeName) {
        if (actTypeName == null || actTypeName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.actuatorTypeName = actTypeName;
    }

    /**
     * Retrieves the actuator type name.
     *
     * @return the actuator type name
     */
    public String getActuatorTypeName() {
        return actuatorTypeName;
    }

    /**
     * Compares this ActuatorTypeName with the specified object for equality.
     * Two ActuatorTypeName objects are considered equal if they have the same actuator type name.
     *
     * @param o the object to compare
     * @return true if the specified object is equal to this ActuatorTypeName, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActuatorTypeName that = (ActuatorTypeName) o;
        return actuatorTypeName.equals(that.actuatorTypeName);
    }

    /**
     * Generates a hash code for this ActuatorTypeName.
     *
     * @return the hash code generated for this ActuatorTypeName
     */
    @Override
    public int hashCode() {
        return actuatorTypeName.hashCode();
    }
}
