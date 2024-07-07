package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.house.House;
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
import smarthome.mapper.HouseDTO;
import smarthome.mapper.HouseIdDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for HouseDTO
 */
@Component
public class HouseMapper {

    /**
     * HouseMapper constructor
     */
    public HouseMapper() {
        // Empty constructor
    }

    /**
     * Converts a HouseDTO to a HouseName value object
     *
     * @param houseDTO the HouseDTO to convert
     * @return the HouseName value object
     */
    public HouseName toHouseName(HouseDTO houseDTO) {
        return new HouseName(houseDTO.getHouseName());
    }

    /**
     * Converts a HouseDTO to a Location value object
     *
     * @param houseDTO HouseDTO
     * @return the Location value object
     */
    public Location toLocation(HouseDTO houseDTO) {
        StreetName streetName = new StreetName(houseDTO.getStreetName());
        StreetNumber streetNumber = new StreetNumber(houseDTO.getStreetNumber());
        ZipCode zipCode = new ZipCode(houseDTO.getZipCode());
        City city = new City(houseDTO.getCity());
        Country country = new Country(houseDTO.getCountry());
        Latitude latitude = new Latitude(houseDTO.getLatitude());
        Longitude longitude = new Longitude(houseDTO.getLongitude());
        Address address = new Address(streetName, streetNumber, zipCode, city, country);
        Gps gps = new Gps(latitude, longitude);
        return new Location(address, gps);
    }

    /**
     * Converts a House entity to a HouseDTO
     *
     * @param house House
     * @return the HouseDTO
     */
    public HouseDTO toHouseDTO(House house) {
        String houseName = house.getIdentity().getName();
        String streetName = house.getLocation().getAddress().getStreetName().getStreetName();
        String streetNumber = house.getLocation().getAddress().getStreetNumber().getStreetNumber();
        String zipCode = house.getLocation().getAddress().getZipCode().getZipCode();
        String city = house.getLocation().getAddress().getCity().getCity();
        String country = house.getLocation().getAddress().getCountry().getCountryName();
        double latitude = house.getLocation().getGps().getLatitude().getLatitude();
        double longitude = house.getLocation().getGps().getLongitude().getLongitude();
        return new HouseDTO(houseName, streetName, streetNumber, zipCode, city, country, latitude, longitude);
    }

    /**
     * Converts a list of House Ids to a list of House Ids DTOs
     *
     * @param houseIds List of House ids
     * @return List of HouseIds DTOs
     */
    public List<HouseIdDTO> toHouseIdsDTO(Iterable<HouseName> houseIds) {
        List<HouseIdDTO> houseIdsDTO = new ArrayList<>();
        for (HouseName houseName : houseIds) {
            houseIdsDTO.add(new HouseIdDTO(houseName.getName()));
        }
        return houseIdsDTO;
    }
}
