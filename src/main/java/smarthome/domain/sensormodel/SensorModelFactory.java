package smarthome.domain.sensormodel;

import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

/**
 * A factory interface for creating SensorModel objects.
 */
public interface SensorModelFactory {


    /**
     * Creates a new SensorModel object.
     *
     * @param sensorModelName the name of the sensor model
     * @param sensorTypeId    the type id of the sensor model
     * @return a new SensorModel object
     */
    SensorModel createSensorModel(SensorModelName sensorModelName, SensorTypeId sensorTypeId);
}

