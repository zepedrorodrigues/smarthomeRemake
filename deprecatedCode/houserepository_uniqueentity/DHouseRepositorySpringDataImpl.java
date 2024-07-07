package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.house.House;
import smarthome.domain.repository.IHouseRepository;
import smarthome.persistence.datamodel.HouseDataModel;
import smarthome.persistence.datamodel.mapper.HouseDataModelMapper;
import smarthome.persistence.spring.IHouseRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * House Repository Spring Data implementation
 * This class implements the IHouseRepository interface and provides methods to interact with the
 * House data using Spring Data. It uses the HouseDataModelMapper and IHouseRepositorySpringData
 * objects to map the data between the domain and data layers.
 */
@Repository
public class HouseRepositorySpringDataImpl implements IHouseRepository {

    private final HouseDataModelMapper houseDataModelMapper;
    private final IHouseRepositorySpringData houseRepositorySpringData;

    /**
     * Instantiates a new House repository Spring Data implementation.
     *
     * @param houseDataModelMapper      the house data model mapper
     * @param houseRepositorySpringData the house repository spring data
     */
    public HouseRepositorySpringDataImpl(HouseDataModelMapper houseDataModelMapper,
                                         IHouseRepositorySpringData houseRepositorySpringData) {
        this.houseDataModelMapper = houseDataModelMapper;
        this.houseRepositorySpringData = houseRepositorySpringData;

    }

    /**
     * Update the house
     *
     * @param house the house to update
     * @return the updated house
     */
    @Override
    public House update(House house) {
        if (house == null) {
            throw new IllegalArgumentException();
        }
        Optional<House> houseOptional = getEntity();
        if (houseOptional.isPresent()) {
            HouseDataModel houseDataModel = new HouseDataModel(houseOptional.get());
            houseDataModel.updateFromDomain(house);
            houseRepositorySpringData.save(houseDataModel);
            return house;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Get the house
     *
     * @return the house
     */
    @Override
    public Optional<House> getEntity() {
        List<HouseDataModel> allHouses = houseRepositorySpringData.findAll();
        if (allHouses.isEmpty()) {
            return Optional.empty();
        }
        if (allHouses.size() > 1) {
            throw new IllegalStateException();
        }
        HouseDataModel houseDataModel = allHouses.get(0);
        House house = houseDataModelMapper.toHouseDomain(houseDataModel);
        return Optional.of(house);
    }

    /**
     * Save the entity
     *
     * @param house the entity to save
     * @return the saved entity
     */
    @Override
    public House save(House house) {
        Optional<House> houseOptional = getEntity();
        if (house == null || (houseOptional.isPresent() &&
                !house.getIdentity().equals(houseOptional.get().getIdentity()))) {
            throw new IllegalArgumentException();
        }
        HouseDataModel houseDataModel = new HouseDataModel(house);
        houseRepositorySpringData.save(houseDataModel);
        return house;
    }
}