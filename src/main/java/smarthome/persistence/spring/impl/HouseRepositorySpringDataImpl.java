package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.persistence.datamodel.HouseDataModel;
import smarthome.persistence.datamodel.mapper.HouseDataModelMapper;
import smarthome.persistence.spring.IHouseRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the IHouseRepository interface using Spring Data.
 */
@Repository
public class HouseRepositorySpringDataImpl implements IHouseRepository {

    private final HouseDataModelMapper houseDataModelMapper;
    private final IHouseRepositorySpringData houseRepositorySpringData;

    /**
     * Constructor for the HouseRepositorySpringDataImpl class.
     *
     * @param houseDataModelMapper      The mapper to convert between House and HouseDataModel objects.
     * @param houseRepositorySpringData The Spring Data repository for HouseDataModel objects.
     */
    public HouseRepositorySpringDataImpl(HouseDataModelMapper houseDataModelMapper,
                                         IHouseRepositorySpringData houseRepositorySpringData) {
        this.houseDataModelMapper = houseDataModelMapper;
        this.houseRepositorySpringData = houseRepositorySpringData;
    }

    /**
     * Saves a new House object to the database.
     *
     * @param house The House object to save.
     * @return The saved House object.
     * @throws IllegalArgumentException If the House object is null or already exists in the database.
     */
    @Override
    public House save(House house) {
        if (house == null || containsIdentity(house.getIdentity())) {
            throw new IllegalArgumentException();
        }
        HouseDataModel houseDataModel = new HouseDataModel(house);
        houseRepositorySpringData.save(houseDataModel);
        return house;
    }

    /**
     * Retrieves all House objects from the database.
     *
     * @return An Iterable of all House objects.
     */
    @Override
    public Iterable<House> findAll() {
        List<HouseDataModel> houseDataModels = houseRepositorySpringData.findAll();
        return houseDataModelMapper.toHousesDomain(houseDataModels);
    }

    /**
     * Retrieves a House object by its identity.
     *
     * @param houseId The identity of the House object to retrieve.
     * @return An Optional containing the House object if found, or an empty Optional if not.
     * @throws IllegalArgumentException If the House identity is null.
     */
    @Override
    public Optional<House> findByIdentity(HouseName houseId) {
        if (houseId == null) {
            throw new IllegalArgumentException();
        }

        Optional<HouseDataModel> houseDataModel = houseRepositorySpringData.findById(houseId.getName());
        return houseDataModel.map(houseDataModelMapper::toHouseDomain);
    }

    /**
     * Checks if a House object with the given identity exists in the database.
     *
     * @param houseId The identity of the House object to check.
     * @return true if the House object exists, false otherwise.
     * @throws IllegalArgumentException If the House identity is null.
     */
    @Override
    public boolean containsIdentity(HouseName houseId) {
        if (houseId == null) {
            throw new IllegalArgumentException();
        }
        return houseRepositorySpringData.existsById(houseId.getName());
    }

    /**
     * Updates an existing House object in the database.
     *
     * @param house The House object to update.
     * @return The updated House object.
     * @throws IllegalArgumentException If the House object is null or does not exist in the database.
     */
    @Override
    public House update(House house) {
        if (house == null || !containsIdentity(house.getIdentity())) {
            throw new IllegalArgumentException();
        }
        HouseDataModel houseDataModel = new HouseDataModel(house);
        houseRepositorySpringData.save(houseDataModel);
        return house;
    }

    /**
     * Finds all House ids.
     *
     * @return A list of House ids.
     */
    @Override
    public List<HouseName> findHouseIds() {
        List<String> houseIdsList = houseRepositorySpringData.findHouseIds();
        return houseIdsList.stream().map(HouseName::new).toList();
    }
}