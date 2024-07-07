package smarthome.domain.sensortype;

import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;

/**
 * This interface defines a factory for creating SensorType objects.
 */
public interface SensorTypeFactory {
    /**
     * Creates a new SensorType object with the given name and unit.
     *
     * @param sensorTypeName the name of the sensor type
     * @param sensorTypeUnit the unit of the sensor type
     * @return a new SensorType object
     */
    SensorType createSensorType(SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit);

    /**
     * Creates a new SensorType object with the given id, name and unit.
     *
     * @param sensorTypeId   the id of the sensor type
     * @param sensorTypeName the name of the sensor type
     * @param sensorTypeUnit the unit of the sensor type
     * @return a new SensorType object
     */
    SensorType createSensorType(SensorTypeId sensorTypeId, SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit);
}
