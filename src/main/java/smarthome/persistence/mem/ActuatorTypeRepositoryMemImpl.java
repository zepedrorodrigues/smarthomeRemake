package smarthome.persistence.mem;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorTypeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The ActuatorTypeRepositoryMemImpl class represents a repository for managing ActuatorType entities.
 * It provides methods to save, retrieve, and manipulate ActuatorType objects.
 * This repository is backed by an in-memory HashMap data structure.
 * ActuatorTypeName represents the identity of ActuatorType entities.
 * ActuatorType represents ActuatorType entities.
 */
public class ActuatorTypeRepositoryMemImpl implements IActuatorTypeRepository {

    // Internal storage for ActuatorType entities using a HashMap
    private final Map<ActuatorTypeName, ActuatorType> DATA = new HashMap<>();

    /**
     * Saves an actuator type to the repository.
     *
     * @param actuatorType the actuator type to save
     * @return the saved actuator type
     */
    @Override
    public ActuatorType save(ActuatorType actuatorType) {
        if (actuatorType == null || containsIdentity(actuatorType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(actuatorType.getIdentity(), actuatorType);
        return actuatorType;
    }

    /**
     * Finds all actuator types in the repository.
     *
     * @return all actuator types in the repository
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves all actuator type identities from the repository.
     *
     * @return all actuator type identities in the repository
     */
    @Override
    public Iterable<ActuatorTypeName> findActuatorTypeNames() {
        return DATA.keySet();
    }

    /**
     * Finds an actuator type by its identity.
     *
     * @param actuatorTypeName the identity of the actuator type
     * @return the actuator type with the given identity or an empty optional if no such actuator type exists
     */
    @Override
    public Optional<ActuatorType> findByIdentity(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        return Optional.ofNullable(DATA.get(actuatorTypeName));
    }

    /**
     * Checks if the repository contains an actuator type with the given identity.
     *
     * @param actuatorTypeName the identity to check for
     * @return true if the repository contains an actuator type with the given identity, false otherwise
     */
    @Override
    public boolean containsIdentity(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        return DATA.containsKey(actuatorTypeName);
    }
}