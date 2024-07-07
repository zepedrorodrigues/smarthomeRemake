package smarthome.domain.device.vo;

import smarthome.ddd.DomainId;

/**
 * Represents a unique identifier for devices within the smart home domain.
 * This identifier is used to distinctly recognize devices across the system.
 */
public class DeviceId implements DomainId {

    private final String deviceId;

    /**
     * Constructs a new {@code DeviceId} with the specified identifier.
     *
     * @param deviceId the unique identifier for the device. Cannot be null or blank.
     * @throws IllegalArgumentException if the provided {@code id} is null or blank,
     * indicating that the identifier does not meet the required validity criteria.
     */
    public DeviceId(String deviceId) {
        if (isIdValid(deviceId)) {
            this.deviceId = deviceId;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the unique identifier for the device.
     *
     * @return the identifier for the device.
     */
    public String getIdentity() {
        return deviceId;
    }

    /**
     * Checks if the provided identifier is valid.
     * A valid identifier is not null and not blank.
     *
     * @param id the identifier to check for validity.
     * @return {@code true} if the identifier is valid, {@code false} otherwise.
     */
    private boolean isIdValid(String id) {
        return id != null && !id.isBlank();
    }

    /**
     * Compares this {@code DeviceId} to the specified object.
     * @param object the object to compare this {@code DeviceId} against.
     * @return {@code true} if the object is a {@code DeviceId} with the same identifier, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DeviceId deviceId = (DeviceId) object;
        return this.deviceId.equals(deviceId.deviceId);
    }

    /**
     * Returns a hash code value for the {@code DeviceId}.
     * @return a hash code value for this {@code DeviceId}, based on Objects.hashh method on the primitive value.
     */
    @Override
    public int hashCode() {
        return deviceId.hashCode();
    }
}
