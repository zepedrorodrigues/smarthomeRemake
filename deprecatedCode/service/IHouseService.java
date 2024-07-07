package smarthome.service;

import smarthome.mapper.HouseDTO;

import java.util.List;

/**
 * IHouseService interface for configuring the location of the House entity.
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
     * @param houseDTO The DTO representing the House entity.
     * @return The DTO representing the House entity with the location configured.
     */
    HouseDTO configHouseLocation(HouseDTO houseDTO);

}


