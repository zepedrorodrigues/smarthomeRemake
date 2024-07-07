package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.vo.Address;
import smarthome.domain.house.vo.City;
import smarthome.domain.house.vo.Country;
import smarthome.domain.house.vo.Gps;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Latitude;
import smarthome.domain.house.vo.Location;
import smarthome.domain.house.vo.Longitude;
import smarthome.domain.house.vo.StreetName;
import smarthome.domain.house.vo.StreetNumber;
import smarthome.domain.house.vo.ZipCode;
import smarthome.persistence.datamodel.HouseDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between HouseDataModel and House domain model.
 */
@Component
public class HouseDataModelMapper {

    private final HouseFactory houseFactory;

    /**
     * Constructor of the House Data Model Mapper that receives a house factory
     *
     * @param houseFactory the house factory
     */
    public HouseDataModelMapper(HouseFactory houseFactory) {
        this.houseFactory = houseFactory;
    }

    /**
     * This method converts a HouseDataModel to a House domain model.
     *
     * @param houseDataModel The HouseDataModel to be converted.
     * @return The converted House domain model.
     */
    public House toHouseDomain(HouseDataModel houseDataModel) {
        HouseName houseName = new HouseName(houseDataModel.getHouseName());
        Location location = new Location(
                new Address(
                        new StreetName(houseDataModel.getStreetName()),
                        new StreetNumber(houseDataModel.getStreetNumber()),
                        new ZipCode(houseDataModel.getZipCode()),
                        new City(houseDataModel.getCity()),
                        new Country(houseDataModel.getCountry())),
                new Gps(
                        new Latitude(houseDataModel.getLatitude()),
                        new Longitude(houseDataModel.getLongitude())));
        return houseFactory.createHouse(houseName, location);
    }

    /**
     * This method converts a list of HouseDataModel to a list of House domain model.
     *
     * @param houseDataModels The list of HouseDataModel to be converted.
     * @return The converted list of House domain model.
     */
    public Iterable<House> toHousesDomain(Iterable<HouseDataModel> houseDataModels) {
        List<House> houses = new ArrayList<>();

        for (HouseDataModel houseDataModel : houseDataModels) {
            houses.add(toHouseDomain(houseDataModel));
        }
        return houses;
    }
}
