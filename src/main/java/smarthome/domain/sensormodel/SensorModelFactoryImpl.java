package smarthome.domain.sensormodel;

import org.springframework.stereotype.Component;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

/**
 * SensorModelFactoryImpl is a class that implements the SensorModelFactory interface.
 * It provides a method to create a SensorModel.
 */
@Component
public class SensorModelFactoryImpl implements SensorModelFactory {

    /**
     * Create a SensorModel with the given SensorModelName and SensorTypeId.
     *
     * @param sensorModelName The name of the SensorModel.
     * @param sensorTypeId    The type ID of the SensorModel.
     * @return A new SensorModel with the given name and type ID.
     */
    @Override
    public SensorModel createSensorModel(SensorModelName sensorModelName, SensorTypeId sensorTypeId) {
        return new SensorModel(sensorModelName, sensorTypeId);
    }
}
