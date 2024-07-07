package smarthome.persistence.mem;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IActuatorRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository for actuators.
 * This implementation stores the data in memory.
 */
public class ActuatorRepositoryMemImpl implements IActuatorRepository {

    private final Map<ActuatorId, Actuator> DATA = new HashMap<>();

    /**
     * Saves an actuator to the repository.
     *
     * @param actuator the actuator to save
     * @return the saved actuator
     * @throws IllegalArgumentException if the actuator is null
     */
    @Override
    public Actuator save(Actuator actuator) {
        if (actuator == null)
            throw new IllegalArgumentException();
        DATA.put(actuator.getIdentity(), actuator);
        return actuator;
    }

    /**
     * Finds all actuators in the repository.
     *
     * @return all actuators in the repository
     * as an iterable
     * @throws IllegalArgumentException if the identity is null
     */
    @Override
    public Iterable<Actuator> findAll() {
        return DATA.values();
    }

    /**
     * Finds a device by its identity.
     *
     * @param id the identity of the actuator
     * @return the actuator with the given identity or an empty optional
     * if no such device exists
     * @throws IllegalArgumentException if the identity is null
     */
    @Override
    public Optional<Actuator> findByIdentity(ActuatorId id) {
        if (id == null)
            throw new IllegalArgumentException();
        return Optional.ofNullable(DATA.get(id));
    }

    /**
     * Checks if the repository contains an actuator with the given identity.
     *
     * @param id the identity to check for
     * @return true if the repository contains an actuator with the given identity, false otherwise
     * @throws IllegalArgumentException if the identity is null
     */
    @Override
    public boolean containsIdentity(ActuatorId id) {
        if (id == null)
            throw new IllegalArgumentException();
        return DATA.containsKey(id);
    }

    /**
     * Finds all actuators ids by device id.
     *
     * @param deviceId the device id
     * @return the actuators ids associated with the device id
     * @throws IllegalArgumentException if the device id is null
     */
    @Override
    public Iterable<ActuatorId> findActuatorIdsByDeviceId(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        return DATA.values().stream()
                .filter(actuator -> actuator.getDeviceId().equals(deviceId))
                .map(Actuator::getIdentity)
                .toList();
    }
}
