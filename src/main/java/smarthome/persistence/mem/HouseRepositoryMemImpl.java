package smarthome.persistence.mem;

import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class for managing the persistence and retrieval of House instances.
 * <p>
 *     This repository is backed by an in-memory HashMap data structure.
 *     It is an in-memory implementation of the repository.
 *     The repository is responsible for managing the storage and retrieval of House instances.
 */
public class HouseRepositoryMemImpl implements IHouseRepository {
    /**
     * The in-memory data structure used to store House instances.
     */
    private final Map<HouseName,House> DATA = new HashMap<>();

    /**
     * Deletes a House from the repository. The method first checks if the House parameter is null and throws an IllegalArgumentException if it is.
     * @param theHouse The House to delete.
     * @throws IllegalArgumentException If the provided House is null or if the House is not in the repository.
     */
    @Override
    public House update(House theHouse) {
        if(theHouse == null || !containsIdentity(theHouse.getIdentity())){
            throw new IllegalArgumentException();}
        DATA.put(theHouse.getIdentity(),theHouse);
        return theHouse;}

    /**
     * Retrieves all House identities from the repository.
     * @return An iterable containing all House identities in the repository.
     */
    @Override
    public Iterable<HouseName> findHouseIds() {
        return DATA.keySet();
    }

    /**
     * Saves a House to the repository. The method first checks if the House parameter is null and throws an IllegalArgumentException if it is.
     * @param entity The House to save.
     * @return The saved House.
     * @throws IllegalArgumentException If the provided House is null or if a House with the same identity is already in the repository.
     */
    @Override
    public House save(House entity) {
        if(entity == null || containsIdentity(entity.getIdentity())){
            throw new IllegalArgumentException();}
        DATA.put(entity.getIdentity(),entity);
        return entity;}

    /**
     * Retrieves all Houses from the repository.
     * @return An iterable containing all Houses in the repository.
     */
    @Override
    public Iterable<House> findAll() {
        return DATA.values();}

    /**
     * Retrieves the House with the given identity from the repository.
     * @param id The identity of the House to retrieve.
     * @return An optional containing the House if it exists in the repository, empty otherwise.
     */
    @Override
    public Optional<House> findByIdentity(HouseName id) {
        if(id == null){
            throw new IllegalArgumentException();}
        return Optional.ofNullable(DATA.get(id));}

    /**
     * Checks if the repository contains a House with the given identity.
     * @param id The identity of the House to check for.
     * @return True if the House is in the repository, false otherwise.
     * @throws IllegalArgumentException If the provided identity is null.
     */
    @Override
    public boolean containsIdentity(HouseName id) {
        if(id == null){
            throw new IllegalArgumentException();}
        return DATA.containsKey(id);
    }
}
