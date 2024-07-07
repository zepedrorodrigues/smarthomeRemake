package smarthome.persistence.mem;

import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents a repository for SensorType objects.
 * It implements the ISensorTypeRepository interface.
 * It provides methods to save, find all, get by identity, and check if a SensorType exists in the repository.
 */
public class SensorTypeRepositoryMemImpl implements ISensorTypeRepository {

    private final Map<SensorTypeId, SensorType> DATA = new HashMap<>();

    /**
     * Saves a SensorType object to the repository.
     *
     * @param sensorType the SensorType object to save
     * @return the saved SensorType object
     * @throws IllegalArgumentException if the SensorType object is null or if the repository
     *                                  already contains a SensorType object with the same identity
     */
    @Override
    public SensorType save(SensorType sensorType) {
        if (sensorType == null || containsIdentity(sensorType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(sensorType.getIdentity(), sensorType);
        return sensorType;
    }

    /**
     * Finds all SensorType objects in the repository.
     *
     * @return an Iterable of all SensorType objects in the repository
     */
    @Override
    public Iterable<SensorType> findAll() {
        return DATA.values();
    }

    /**
     * Finds a SensorType object by its identity.
     *
     * @param id the identity of the SensorType object to find
     * @return an Optional containing the found SensorType object, or an empty Optional if no such SensorType exists
     * @throws IllegalArgumentException if the identity is null
     */
    @Override
    public Optional<SensorType> findByIdentity(SensorTypeId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return Optional.ofNullable(DATA.get(id));
    }

    /**
     * Checks if the repository contains a SensorType object with the given identity.
     *
     * @param id the identity to check for
     * @return true if the repository contains a SensorType object with the given identity, false otherwise
     * @throws IllegalArgumentException if the identity is null
     */
    @Override
    public boolean containsIdentity(SensorTypeId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return DATA.containsKey(id);
    }

    /**
     * Retrieves all sensor type IDs from the repository.
     *
     * @return an Iterable of SensorTypeId objects representing all sensor type IDs in the repository.
     */
    @Override
    public Iterable<SensorTypeId> findSensorTypeIds() {
        return DATA.keySet();
    }
}
