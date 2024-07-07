package smarthome.domain.actuatormodel.vo;

import smarthome.ddd.DomainId;

/**
 * Value object for the actuator model name.
 */
public class ActuatorModelName implements DomainId {

    private final String name;

    /**
     * Constructs an ActuatorModelName object with the specified name value.
     *
     * @param name the actuator model name
     * @throws IllegalArgumentException if the name is null or blank
     */
    public ActuatorModelName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * Retrieves the actuator model name.
     *
     * @return the actuator model name
     */
    public String getActuatorModelName() {
        return name;
    }

    /**
     * Compares this ActuatorModelName object with another object to determine if they are equal.
     *
     * @param o the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActuatorModelName that = (ActuatorModelName) o;
        return name.equals(that.name);
    }

    /**
     * Generates a hash code for the ActuatorModelName object.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
