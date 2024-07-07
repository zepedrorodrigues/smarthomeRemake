package smarthome.persistence.mem;

import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorModelRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The ActuatorModelRepositoryMemImpl class represents a repository for managing ActuatorModel entities.
 * It provides methods to save, retrieve, and manipulate ActuatorModel objects.
 * This repository is backed by an in-memory HashMap data structure.
 * <p>
 * ActuatorModelName representing the identity of Sensor entities.
 * ActuatorModel   representing ActuatorModel entities.
 */
public class ActuatorModelRepositoryMemImpl implements IActuatorModelRepository {

    // Internal storage for ActuatorModel entities using a HashMap
    private final Map<ActuatorModelName, ActuatorModel> DATA = new HashMap<>();

    /**
     * Saves an ActuatorModel entity to the repository.
     *
     * @param entity The ActuatorModel entity to be saved.
     * @return The saved ActuatorModel entity.
     * @throws IllegalArgumentException if the entity is null or if an ActuatorModel with the same identity already
     *                                  exists.
     */
    @Override
    public ActuatorModel save(ActuatorModel entity) {
        if (entity == null || containsIdentity(entity.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(entity.getIdentity(), entity);
        return entity;
    }


    /**
     * Retrieves all ActuatorModel entities stored in the repository.
     *
     * @return An Iterable containing all ActuatorModel entities.
     */
    @Override
    public Iterable<ActuatorModel> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves an ActuatorModel entity from the repository by its identity.
     *
     * @param id The identity of the ActuatorModel entity to retrieve.
     * @return An Optional containing the ActuatorModel entity if found, otherwise empty.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public Optional<ActuatorModel> findByIdentity(ActuatorModelName id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return Optional.ofNullable(DATA.get(id));
    }

    /**
     * Checks if the repository contains an ActuatorModel entity with the specified identity.
     *
     * @param id The identity of the ActuatorModel entity to check for existence.
     * @return True if the repository contains the specified identity, otherwise false.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean containsIdentity(ActuatorModelName id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return DATA.containsKey(id);
    }

    /**
     * This method is used to find ActuatorModels by their ActuatorType identity.
     *
     * @param actuatorTypeName The name of the ActuatorType to find ActuatorModels for.
     * @return An Iterable of ActuatorModels that have the specified ActuatorType identity.
     */
    @Override
    public Iterable<ActuatorModel> findActuatorModelsByActuatorTypeName(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        return DATA.values().stream().filter(actuatorModel -> actuatorModel.getActuatorTypeName().equals(actuatorTypeName)).collect(Collectors.toList());
    }


    /**
     * This method is used to find ActuatorModels by their ActuatorType identity.
     *
     * @param actuatorTypeName The name of the ActuatorType to find ActuatorModels for.
     * @return An Iterable of ActuatorModels that have the specified ActuatorType identity.
     */
    @Override
    public Iterable<ActuatorModelName> findActuatorModelNamesByActuatorTypeName(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        List<ActuatorModelName> actuatorModelNames = new ArrayList<>();
        for (ActuatorModel actuatorModel : DATA.values()) {
            if (actuatorModel.getActuatorTypeName().equals(actuatorTypeName)) {
                actuatorModelNames.add(actuatorModel.getIdentity());
            }
        }
        return actuatorModelNames;

    }
}
