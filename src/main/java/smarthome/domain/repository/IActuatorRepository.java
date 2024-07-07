package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.device.vo.DeviceId;

/**
 * The IActuatorRepository interface represents a repository for managing Actuator entities.
 * It extends the generic IRepository interface with ActuatorId as the ID type and Actuator as the entity type.
 * It provides methods to save, retrieve, and manipulate Actuator objects.
 */
public interface IActuatorRepository extends IRepository<ActuatorId, Actuator> {

    /**
     * Finds all actuator ids by device id.
     * <p>
     * @param deviceId the device id
     * @return the actuator ids associated with the device id
     */
    Iterable<ActuatorId> findActuatorIdsByDeviceId(DeviceId deviceId);
}
