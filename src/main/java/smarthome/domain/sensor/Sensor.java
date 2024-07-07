package smarthome.domain.sensor;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

/**
 * Represents a Sensor in the domain.
 */
public interface Sensor extends AggregateRoot<SensorId> {

    /**
     * Returns the name of the Sensor.
     *
     * @return the name of the Sensor
     */
    SensorModelName getSensorModelName();

    /**
     * Returns the DeviceId of the Sensor.
     *
     * @return the DeviceId of the Sensor
     */
    DeviceId getDeviceId();

    /**
     * Returns the Value of the Sensor.
     *
     * @return the Value of the Sensor
     */
    Value getValue();
}
