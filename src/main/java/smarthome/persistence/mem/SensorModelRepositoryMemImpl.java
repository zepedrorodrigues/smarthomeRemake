package smarthome.persistence.mem;

import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SensorModelRepositoryMemImpl is a class that implements the IRepository interface.
 * It provides methods to interact with the SensorModel data.
 */
public class SensorModelRepositoryMemImpl implements ISensorModelRepository {
    /**
     * The data structure used to store the SensorModel entities.
     */
    private final Map<SensorModelName, SensorModel> DATA = new HashMap<>();

    /**
     * Save a SensorModel entity to the repository.
     *
     * @param entity The SensorModel entity to be saved.
     * @return The saved SensorModel entity.
     * @throws IllegalArgumentException if the entity is null or if an entity with the same identity
     *                                  already exists in the repository.
     */
    @Override
    public SensorModel save(SensorModel entity) {
        if (entity == null || containsIdentity(entity.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(entity.getIdentity(), entity);
        return entity;
    }

    /**
     * Find all SensorModel entities in the repository.
     *
     * @return An Iterable of all SensorModel entities.
     */
    @Override
    public Iterable<SensorModel> findAll() {

        return DATA.values();
    }

    /**
     * Get a SensorModel entity by its identity.
     *
     * @param id The identity of the SensorModel entity.
     * @return An Optional that may contain the SensorModel entity if it exists.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<SensorModel> findByIdentity(SensorModelName id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return Optional.ofNullable(DATA.get(id));
    }

    /**
     * Check if a SensorModel entity with the given identity exists in the repository.
     *
     * @param id The identity of the SensorModel entity.
     * @return true if the SensorModel entity exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(SensorModelName id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return DATA.containsKey(id);
    }

    /**
     * Find all SensorModel entities in the repository by sensor type identity.
     *
     * @param sensorTypeId The identity of the sensor type.
     * @return An Iterable of all SensorModel entities.
     * @throws IllegalArgumentException if the identity is null.
     */
    public Iterable<SensorModel> findSensorModelsBySensorTypeId(SensorTypeId sensorTypeId) {
        if (sensorTypeId == null) {
            throw new IllegalArgumentException();
        }
        return DATA.values().stream().filter(sensorModel -> sensorModel.getSensorTypeId().equals(sensorTypeId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a iterable of SensorModelName objects by sensor type ID.
     *
     * @param sensorTypeId The ID of the sensor type.
     * @return List of SensorModelName objects that match the given sensor type ID.
     * @throws IllegalArgumentException if the sensorTypeId is null.
     */
    @Override
    public Iterable<SensorModelName> findSensorModelNamesBySensorTypeId(SensorTypeId sensorTypeId) {
        if (sensorTypeId == null) {
            throw new IllegalArgumentException();
        }
        List<SensorModelName> sensorModelNames = new ArrayList<>();
        for (SensorModel sensorModel : DATA.values()) {
            if (sensorModel.getSensorTypeId().equals(sensorTypeId)) {
                sensorModelNames.add(sensorModel.getIdentity());
            }
        }
        return sensorModelNames;
    }
}
