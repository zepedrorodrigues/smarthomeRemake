package smarthome.domain.actuator.vo;

import smarthome.ddd.DomainId;

/**
 * This class represents a Value Object ActuatorId in the domain of a smart home system.
 * It implements the DomainId interface.
 */
public class ActuatorId implements DomainId {

    private final String actuatorId;

    /**
     * Constructs an ActuatorId with the given id.
     *
     * @param id the unique identifier for the actuator
     * @throws IllegalArgumentException if the id is not valid (null or blank)
     */
    public ActuatorId(String id) {
        if (isIdValid(id)) {
            this.actuatorId = id;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if the given id is valid.
     *
     * @param id the id to check
     * @return true if the id is valid (not null and not black), false otherwise
     */
    private boolean isIdValid(String id) {
        return id != null && !id.isBlank();
    }

    /**
     * Returns the actuator id.
     *
     * @return the actuator id
     */
    public String getActuatorId() {
        return this.actuatorId;
    }

    /**
     * Checks if this ActuatorId is the same as the given object (same class with same actuator id).
     *
     * @param o the object to compare with
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActuatorId that = (ActuatorId) o;
        return actuatorId.equals(that.actuatorId);
    }

    /**
     * Returns the hash code of the actuator id.
     *
     * @return the hash code of the actuator id
     */
    @Override
    public int hashCode() {
        return actuatorId.hashCode();
    }


}
