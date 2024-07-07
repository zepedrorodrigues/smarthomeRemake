package smarthome.domain.sensortype;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;

import java.util.Objects;

/**
 * Represents a type of sensor in the smart home domain.
 * This class is an aggregate root in the domain-driven design context.
 */

public class SensorType implements AggregateRoot<SensorTypeId> {

    private SensorTypeId sensorTypeId;
    private SensorTypeName sensorTypeName;
    private SensorTypeUnit sensorTypeUnit;

    /**
     * Constructs a new SensorType with the given name and unit.
     * A unique ID is automatically generated for the new SensorType.
     *
     * @param sensorTypeName the name of the sensor type
     * @param sensorTypeUnit the unit of the sensor type
     * @throws IllegalArgumentException if either parameter is null
     */

    public SensorType(SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit) {
        if (!validParameters(sensorTypeName, sensorTypeUnit))
            throw new IllegalArgumentException();

        this.sensorTypeName = sensorTypeName;
        this.sensorTypeUnit = sensorTypeUnit;
        this.sensorTypeId = new SensorTypeId(sensorTypeName.getSensorTypeName() + sensorTypeUnit.getSensorTypeUnit());
    }

    /**
     * Constructs a new SensorType with the given name, unit, and ID.
     *
     * @param sensorTypeId   the unique ID of the sensor type
     * @param sensorTypeName the name of the sensor type
     * @param sensorTypeUnit the unit of the sensor type
     * @throws IllegalArgumentException if any parameter is null
     */
    public SensorType(SensorTypeId sensorTypeId, SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit) {
        if (!validParameters(sensorTypeName, sensorTypeUnit) || sensorTypeId == null)
            throw new IllegalArgumentException();

        this.sensorTypeId = sensorTypeId;
        this.sensorTypeName = sensorTypeName;
        this.sensorTypeUnit = sensorTypeUnit;
    }

    /**
     * Checks if the given parameters are valid.
     * In this context, valid means that they are not null.
     *
     * @param sensorTypeName the name of the sensor type
     * @param sensorTypeUnit the unit of the sensor type
     * @return true if both parameters are not null, false otherwise
     */
    private boolean validParameters(SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit) {
        return sensorTypeName != null && sensorTypeUnit != null;
    }

    /**
     * Returns the unique ID of this sensor type.
     *
     * @return the unique ID of this sensor type
     */
    @Override
    public SensorTypeId getIdentity() {
        return sensorTypeId;
    }

    /**
     * Returns the name of this sensor type.
     *
     * @return the name of this sensor type
     */
    public SensorTypeName getSensorTypeName() {
        return sensorTypeName;
    }

    /**
     * Returns the unit of this sensor type.
     *
     * @return the unit of this sensor type
     */
    public SensorTypeUnit getSensorTypeUnit() {
        return sensorTypeUnit;
    }


    /**
     * Checks if this sensor type is equal to the given object.
     * Two sensor types are considered equal if they have the same name, unit, and ID.
     *
     * @param o the object to compare with
     * @return true if the given object is a sensor type with the same name, unit, and ID, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorType that = (SensorType) o;
        return sensorTypeId.equals(that.sensorTypeId);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sensorTypeId, sensorTypeName, sensorTypeUnit);
    }
}

