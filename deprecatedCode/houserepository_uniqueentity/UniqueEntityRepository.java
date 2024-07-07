package smarthome.ddd;

import java.util.Optional;

/**
 * Interface for a repository that manages a single entity
 * <p>
 * This repository is designed to handle the storage and retrieval of a single instance of the House aggregate.
 * It serves as a data access layer for interacting with the House entity within the domain model.
 * <p>
 * The primary purpose of this repository is to save the aggregate House and allow modifications to its
 * Location value object while ensuring the integrity of the aggregate by preserving its identity.
 * The repository enforces the constraint that only one instance of the House aggregate can be stored at a time.
 * <p>
 * The HouseRepositoryMemImpl class provides the following functionalities:
 * Retrieval of the currently stored House aggregate using the getHouse() method.
 * Saving the House aggregate using the save(House) method, ensuring that only one instance
 *     of the House aggregate is stored at a time and that the identity of the aggregate remains unchanged
 * <p>
 * Note: The HouseRepositoryMemImpl does not support the storage of multiple House instances or the modification of
 * the HouseName value object. It is specifically tailored to handle the persistence of the House aggregate
 * and updates to its Location value object.
 * </p>
 * @param <ID> the entity id
 * @param <T>  the entity type
 */
public interface UniqueEntityRepository<ID extends DomainId, T extends AggregateRoot<ID>> {

    /**
     * Save the entity to the repository
     * <p>
     * This method saves the House aggregate to the repository, ensuring that only one instance of the House aggregate
     * can be stored at a time and that the identity of the aggregate remains unchanged.
     * It throws an IllegalArgumentException if an attempt is made to save a different House instance
     * or if a null House instance is provided.
     * <p>
     * @param entity the entity to save to the repository
     * @return the saved entity if it was saved
     */
    T save(T entity);

    /**
     * Retrieve the entity from the repository
     * <p>
     * @return the entity if it exists
     */
    Optional<T> getEntity();

}
