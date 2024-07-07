package smarthome.domain.sensortype;

import org.springframework.stereotype.Component;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
/**
 * This class is an implementation of the SensorTypeFactory interface.
 * It provides a method to create a new SensorType object.
 */
@Component
public class SensorTypeFactoryImpl implements SensorTypeFactory {

    /**
     * This method creates a new SensorType object.
     *
     * @param sensorTypeName The name of the sensor type.
     * @param sensorTypeUnit The unit of the sensor type.
     * @return A new SensorType object.
     */

    @Override
    public SensorType createSensorType(SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit) {
        return new SensorType(sensorTypeName, sensorTypeUnit);
    }

    /**
     * This method creates a new SensorType object.
     *
     * @param sensorTypeId   The id of the sensor type.
     * @param sensorTypeName The name of the sensor type.
     * @param sensorTypeUnit The unit of the sensor type.
     * @return A new SensorType object.
     */
    @Override
    public SensorType createSensorType(SensorTypeId sensorTypeId, SensorTypeName sensorTypeName, SensorTypeUnit sensorTypeUnit) {
        return new SensorType(sensorTypeId, sensorTypeName, sensorTypeUnit);
    }
}
