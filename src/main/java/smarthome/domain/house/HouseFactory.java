package smarthome.domain.house;

import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;

/**
 * This interface is used to create House objects
 */
public interface HouseFactory {

    /**
     * Creates a House object with the specified house name and location.
     *
     * @param houseName the name of the house
     * @param location  the location of the house
     * @return a House object with the specified house name and location
     */
    House createHouse(HouseName houseName, Location location);
}
