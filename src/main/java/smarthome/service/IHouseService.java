package smarthome.service;

import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;

import java.util.List;
import java.util.Optional;

/**
 * IHouseService interface for methods related to the House entity.
 */
public interface IHouseService {

    /**
     * Retrieves a list of available countries in the system.
     *
     * @return A list of available countries in the system.
     */
    List<String> getAvailableCountries();

    /**
     * Configures the location of the House entity.
     *
     * @param houseId  The house id to be updated.
     * @param location The new location to be configured.
     * @return The House entity with the location configured.
     */
    House configHouseLocation(HouseName houseId, Location location);

    /**
     * Retrieves a list of houses ids.
     *
     * @return A list of house ids.
     */
    Iterable<HouseName> getHouseIds();

    /**
     * Retrieves the House entity.
     *
     * @param id The id of the House entity to be retrieved.
     * @return The House entity.
     */
    Optional<House> getHouse(HouseName id);


    /**
     * Adds a new house to the system.
     *
     * @param houseId  The house id to be added.
     * @param location The location of the house.
     * @return The House entity with the location configured.
     */
    House addHouse(HouseName houseId, Location location);

}


