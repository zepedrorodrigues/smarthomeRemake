package smarthome.domain.repository;

import smarthome.ddd.UniqueEntityRepository;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;

/**
 * Interface for House Repository
 */
public interface IHouseRepository extends UniqueEntityRepository<HouseName, House> {

    /**
     * Update the house
     *
     * @param theHouse the house to update
     * @return the updated house
     */
    House update(House theHouse);
}
