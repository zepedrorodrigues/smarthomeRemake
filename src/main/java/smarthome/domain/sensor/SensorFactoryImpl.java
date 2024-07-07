package smarthome.domain.sensor;

import org.springframework.stereotype.Component;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.lang.reflect.InvocationTargetException;

/**
 * This class implements the SensorFactory interface.
 * It is used in the domain layer of the application to create Sensor objects.
 */
@Component
public class SensorFactoryImpl implements SensorFactory {
    /**
     * Creates a Sensor object with the given parameters.
     *
     * @param sensorModelName the model name of the sensor
     * @param deviceId        the unique identifier of the device the sensor is attached to
     * @return a Sensor object or null if the sensor class name is invalid or an exception is thrown
     */
    @Override
    public Sensor createSensor(SensorModelName sensorModelName, DeviceId deviceId) {
      return createSensor(null,sensorModelName, deviceId);
    }

    /**
     * This method is used to create a Sensor object. It takes a SensorId, SensorModelName, and DeviceId as parameters.
     * If the sensorId is not null, it uses the constructor that takes a SensorId, DeviceId, and SensorModelName.
     * If the sensorId is null, it uses the constructor that takes a DeviceId and SensorModelName.
     *
     * @param sensorId the unique identifier of the sensor. It can be null.
     * @param sensorModelName the model name of the sensor. It cannot be null.
     * @param deviceId the unique identifier of the device the sensor is attached to. It cannot be null.
     * @return a Sensor object if the sensor class name is valid and no exception is thrown during its creation, null otherwise.
     */
    public Sensor createSensor(SensorId sensorId, SensorModelName sensorModelName, DeviceId deviceId) {
        String sensorClassNamepath = "smarthome.domain.sensor." + sensorModelName.getSensorModelName();
            try {
                if(sensorId != null) {
                    return (Sensor) Class.forName(sensorClassNamepath)
                            .getDeclaredConstructor(SensorId.class, DeviceId.class,SensorModelName.class)
                            .newInstance(sensorId, deviceId,sensorModelName);
                }else{
                    return (Sensor) Class.forName(sensorClassNamepath)
                            .getDeclaredConstructor(DeviceId.class,SensorModelName.class)
                            .newInstance(deviceId,sensorModelName);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                     | NoSuchMethodException | ClassNotFoundException e)
            {return null;}
    }
}
