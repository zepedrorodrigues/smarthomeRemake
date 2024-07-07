package smarthome.controller;

import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.mapper.HouseMapper;
import smarthome.service.IHouseService;

import java.util.List;

/**
 * Controller class for configuring the location of the House entity.
 */
public class ConfigHouseLocationController {

    private final IHouseService houseService;
    private final HouseMapper houseMapper;

    /**
     * Initializes a new instance of the ConfigHouseLocationController class.
     *
     * @param houseService The service to be used.
     * @throws IllegalArgumentException If any of the provided arguments are null.
     */
    public ConfigHouseLocationController(IHouseService houseService, HouseMapper houseMapper) {
        if (houseService == null || houseMapper == null) {
            throw new IllegalArgumentException();
        }
        this.houseService = houseService;
        this.houseMapper = houseMapper;
    }


    /**
     * Retrieves a list of available countries in the system.
     *
     * @return A list of available countries in the system.
     */
    public List<String> getAvailableCountries() {
        try {
            // Get the available countries
            return houseService.getAvailableCountries();
        } catch (Exception e) {
            // The countries could not be retrieved
            return null;
        }
    }


    /**
     * Configures the location of the House entity.
     * The method maps the provided HouseDTO object to a Location value object and configures the location of the
     * House entity. The method returns the HouseDTO object with the updated location information.
     *
     * @param houseDTO The HouseDTO object containing the location information to be configured.
     * @return The updated HouseDTO object after the location has been configured.
     */
    public HouseDTO configHouseLocation(HouseDTO houseDTO) {
        if (houseDTO == null) {
            return null;
        }
        try {
            // Convert the DTO to a Location value object
            Location location = houseMapper.toLocation(houseDTO);
            HouseName houseName = new HouseName(houseDTO.getHouseName());
            // Update the house's location via service layer
            House updatedHouse = houseService.configHouseLocation(houseName, location);
            if (updatedHouse != null) {
                return houseMapper.toHouseDTO(updatedHouse);
            } else {
                // The house could not be updated
                return null;
            }
        } catch (IllegalArgumentException e) {
            // The request is malformed or contains missing data
            return null;
        }
    }

}