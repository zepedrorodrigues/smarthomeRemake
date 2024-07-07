package smarthome.service;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.Value;

import java.util.Optional;

/**
 * Service interface used to define the methods for managing actuators
 */
public interface IActuatorService {

    /**
     * Adds an actuator.
     */
    Actuator addActuator(ActuatorMap actuatorMap, DeviceId deviceId);

    /**
     * Gets an actuator by its id.
     *
     * @param id the id of the actuator to get.
     * @return the actuator with the given id.
     */
    Optional<Actuator> getActuatorById(ActuatorId id);

    /**
     * Gets all the actuators ids by device identity.
     *
     * @return all the actuators ids.
     */
    Iterable<ActuatorId> getActuatorIdsByDeviceIdentity(DeviceId deviceId);

    /**
     * Operate (open/close) the blind roller with the given id and value.
     *
     * @param id    the id of the actuator to operate
     * @param value the value to operate the actuator with
     * @return the value of the actuator
     */
    Value operateBlindRoller(ActuatorId id, Value value);

    /**
     * Gets the last percentage reading of the actuator with the given id.
     *
     * @param id the id of the actuator to get the last percentage reading from.
     * @return the last percentage reading of the actuator with the given id.
     */
    Optional<Value>  getLastPercentageReading(ActuatorId id);
}