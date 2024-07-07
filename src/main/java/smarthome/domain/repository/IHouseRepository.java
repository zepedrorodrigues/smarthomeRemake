package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;

/**
 * Repository for the House entity.
 */
public interface IHouseRepository extends IRepository<HouseName, House> {

    /**
     * Update the location of the house.
     *
     * @param house the house to update.
     * @return the updated house.
     */
    House update(House house);

    /**
     * Retrieves all house IDs in the repository.
     *
     * @return an iterable collection of all the house IDs.
     */
    Iterable<HouseName> findHouseIds();
}
