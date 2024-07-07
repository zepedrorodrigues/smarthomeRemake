package smarthome.service.impl;

import smarthome.domain.house.House;
import smarthome.domain.house.vo.Location;
import smarthome.domain.repository.IHouseRepository;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.mapper.HouseMapper;
import smarthome.service.IHouseService;
import smarthome.utils.AvailableCountries;

import java.util.List;
import java.util.Optional;


/**
 * Service class for configuring the location of the House entity.
 */
public class HouseServiceImpl implements IHouseService {

    private final HouseMapper houseMapper;
    private final IHouseRepository houseRepository;


    /**
     * Initializes a new instance of the HouseServiceImpl class.
     *
     * @param houseMapper     The mapper to be used.
     * @param houseRepository The repository to be used.
     * @throws IllegalArgumentException If any of the provided arguments are null.
     */
    public HouseServiceImpl(HouseMapper houseMapper, IHouseRepository houseRepository) {
        if (houseMapper == null || houseRepository == null) {
            throw new IllegalArgumentException();
        }
        this.houseMapper = houseMapper;
        this.houseRepository = houseRepository;
    }


    /**
     * Retrieves a list of available countries in the system.
     *
     * @return A list of available countries in the system.
     */
    @Override
    public List<String> getAvailableCountries() {
        return AvailableCountries.getAvailableCountries();
    }


    /**
     * Configures the location of the House entity.
     *
     * @param houseDTO The DTO representing the House entity.
     * @return The DTO representing the House entity with the location configured or null if the operation fails.
     */
    @Override
    public HouseDTO configHouseLocation(HouseDTO houseDTO) {
        try {
            Optional<House> oHouse = houseRepository.getEntity();
            if (houseDTO != null && oHouse.isPresent()) {
                Location newLocation = houseMapper.toLocation(houseDTO);
                House house = oHouse.get();
                house.configLocation(newLocation);
                House updatedHouse = houseRepository.update(house);
                return houseMapper.toHouseDTO(updatedHouse);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}

