package smarthome.domain.house;

import org.springframework.stereotype.Component;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;

/**
 * This class is used to create House objects
 */
@Component
public class HouseFactoryImpl implements HouseFactory {

    /**
     * Creates a House object with the specified house name and location.
     *
     * @param houseName the name of the house
     * @param location  the location of the house
     * @return a House object with the specified house name and location
     */
    @Override
    public House createHouse(HouseName houseName, Location location) {
        return new House(houseName, location);
    }
}
