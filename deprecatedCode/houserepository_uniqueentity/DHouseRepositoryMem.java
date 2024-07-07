package smarthome.persistence.mem;

import smarthome.domain.house.House;
import smarthome.domain.repository.IHouseRepository;

import java.util.Optional;
/**
 * Repository class for managing the persistence and retrieval of a single instance of the House aggregate.
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
 */
public class HouseRepositoryMem implements IHouseRepository {

    /**
     * The single instance of the House aggregate stored in this repository.
     */
    private House house; // There will only be a single instance of House in this repository

    /**
     * Retrieves the currently stored House aggregate from the repository.
     *
     * @return The currently stored House aggregate.
     */
    @Override
    public Optional<House> getEntity() {
        return Optional.ofNullable(house);
    }

    /**
     * Saves the provided House aggregate to the repository.
     * <p>
     * This method saves the House aggregate to the repository, ensuring that only one instance of the House
     * aggregate can be stored at a time and that the identity of the aggregate remains unchanged. It throws an
     * IllegalArgumentException if an attempt is made to save a different House instance or if a null House
     * instance is provided.
     *
     * @param theHouse The House aggregate to be saved.
     * @return The saved House aggregate.
     * @throws IllegalArgumentException If the provided House instance is null or if an attempt is made to save a
     * different House instance.
     */
    @Override
    public House save(House theHouse) {
        if (theHouse == null || (this.house != null && !this.house.getIdentity().equals(theHouse.getIdentity()))) {
            throw new IllegalArgumentException(); // Attempting to save a house with a different identity
        }
        this.house = theHouse;
        return theHouse;
    }

    /**
     * Update the entity
     *
     * @param house the house to update
     * @return the updated house
     */
    @Override
    public House update(House house) {
        if (house == null || getEntity().isEmpty() || !this.house.getIdentity().equals(house.getIdentity())) {
            throw new IllegalArgumentException();
        }
        this.house = house;
        return this.house;
    }

}
