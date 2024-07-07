package smarthome.domain.sensormodel;


import smarthome.ddd.AggregateRoot;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

/**
 * Represents a SensorModel entity, which encapsulates information about a specific sensor model.
 * This class implements the AggregateRoot interface with SensorModelName as the identity type.
 */
public class SensorModel implements AggregateRoot<SensorModelName> {

    private SensorModelName sensorModelName;
    private SensorTypeId sensorTypeId;

    /**
     * Constructs a SensorModel object with the provided SensorModelName and SensorTypeId.
     *
     * @param aSensorModelName The SensorModelName associated with this SensorModel.
     * @param aSensorTypeId    The SensorTypeId associated with this SensorModel.
     * @throws IllegalArgumentException if either aSensorModelName or aSensorTypeId is null.
     */
    public SensorModel(SensorModelName aSensorModelName, SensorTypeId aSensorTypeId) {
        if (!validParameters(aSensorModelName, aSensorTypeId)) {
            throw new IllegalArgumentException();
        }
        this.sensorModelName = aSensorModelName;
        this.sensorTypeId = aSensorTypeId;
    }

    /**
     * Checks if the provided SensorModelName and SensorTypeId are not null.
     *
     * @param sensorModelName The SensorModelName to check.
     * @param sensorTypeId    The SensorTypeId to check.
     * @return true if both parameters are not null, false otherwise.
     */
    private boolean validParameters(SensorModelName sensorModelName, SensorTypeId sensorTypeId) {
        return sensorModelName != null && sensorTypeId != null;
    }

    /**
     * Retrieves the SensorModelName associated with this SensorModel.
     *
     * @return The SensorModelName of this SensorModel.
     */
    @Override
    public SensorModelName getIdentity() {
        return this.sensorModelName;
    }

    /**
     * Retrieves the SensorTypeId associated with this SensorModel.
     *
     * @return The SensorTypeId of this SensorModel.
     */
    public SensorTypeId getSensorTypeId() {
        return this.sensorTypeId;
    }

    /**
     * Compares this SensorModel with the specified object for equality.
     *
     * @param o The object to compare to this SensorModel.
     * @return true if the specified object is equal to this SensorModel, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorModel that = (SensorModel) o;
        return sensorModelName.equals(that.sensorModelName);
    }

    /**
     * Generates a hash code value for the SensorModel object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return sensorModelName.hashCode();
    }
}
