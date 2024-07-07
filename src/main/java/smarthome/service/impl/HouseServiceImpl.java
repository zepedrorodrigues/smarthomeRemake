package smarthome.service.impl;

import org.springframework.stereotype.Service;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;
import smarthome.domain.repository.IHouseRepository;
import smarthome.service.IHouseService;
import smarthome.utils.AvailableCountries;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements IHouseService {

    private final IHouseRepository houseRepository;
    private final HouseFactory houseFactory;

    /**
     * Initializes a new instance of the HouseRESTService class.
     *
     * @param houseRepository The repository to be used.
     */
    public HouseServiceImpl(IHouseRepository houseRepository, HouseFactory houseFactory) {
        this.houseRepository = houseRepository;
        this.houseFactory = houseFactory;
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
     * Configures the location of a house.
     *
     * @param houseId  The id of the house to be configured.
     * @param location The new location to be configured.
     * @return The House entity with the location configured or null if the operation fails
     * or the house does not exist.
     */
    @Override
    public House configHouseLocation(HouseName houseId, Location location) {
        if (houseId == null || location == null) {
            return null;
        }
        Optional<House> oHouse = houseRepository.findByIdentity(houseId);
        if (oHouse.isPresent()) {
            House house = oHouse.get();
            house.configLocation(location);
            return houseRepository.update(house);
        }
        return null;
    }

    /**
     * Retrieves a list of house ids.
     *
     * @return A list of houses ids.
     */
    @Override
    public Iterable<HouseName> getHouseIds() {
        return houseRepository.findHouseIds();
    }

    /**
     * Retrieves the House entity.
     *
     * @param id The id of the House entity to be retrieved.
     * @return The House entity.
     */
    @Override
    public Optional<House> getHouse(HouseName id) {
        return houseRepository.findByIdentity(id);
    }

    /**
     * Adds a new house to the system.
     *
     * @param houseId  The id of the house to be added.
     * @param location The location of the house to be added.
     * @return The House entity added or null if the operation fails.
     */
    @Override
    public House addHouse(HouseName houseId, Location location) {
        try {
            if(houseRepository.containsIdentity(houseId)) {
                return null;
            }
            House house = houseFactory.createHouse(houseId, location);
            return houseRepository.save(house);
        } catch (Exception e) {
            return null;
        }
    }
}
