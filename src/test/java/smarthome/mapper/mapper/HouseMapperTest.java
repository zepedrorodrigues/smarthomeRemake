package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.*;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.HouseIdDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for HouseMapper class
 */
class HouseMapperTest {

    HouseMapper houseMapper;
    HouseDTO houseDto;
    House house;
    HouseName houseName;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        houseMapper = new HouseMapper();

        houseDto = mock(HouseDTO.class);
        when(houseDto.getHouseName()).thenReturn("houseName");
        when(houseDto.getStreetName()).thenReturn("streetName");
        when(houseDto.getStreetNumber()).thenReturn("streetNumber");
        when(houseDto.getZipCode()).thenReturn("4000-000");
        when(houseDto.getCity()).thenReturn("city");
        when(houseDto.getCountry()).thenReturn("Portugal");
        when(houseDto.getLatitude()).thenReturn(30.0);
        when(houseDto.getLongitude()).thenReturn(90.0);


        StreetName streetName = mock(StreetName.class);
        when(streetName.getStreetName()).thenReturn("streetName");

        StreetNumber streetNumber = mock(StreetNumber.class);
        when(streetNumber.getStreetNumber()).thenReturn("streetNumber");

        ZipCode zipCode = mock(ZipCode.class);
        when(zipCode.getZipCode()).thenReturn("4000-000");

        City city = mock(City.class);
        when(city.getCity()).thenReturn("city");

        Country country = mock(Country.class);
        when(country.getCountryName()).thenReturn("Portugal");

        Address address = mock(Address.class);
        when(address.getStreetName()).thenReturn(streetName);
        when(address.getStreetNumber()).thenReturn(streetNumber);
        when(address.getZipCode()).thenReturn(zipCode);
        when(address.getCity()).thenReturn(city);
        when(address.getCountry()).thenReturn(country);

        Latitude latitude = mock(Latitude.class);
        when(latitude.getLatitude()).thenReturn(30.0);

        Longitude longitude = mock(Longitude.class);
        when(longitude.getLongitude()).thenReturn(90.0);

        Gps gps = mock(Gps.class);
        when(gps.getLatitude()).thenReturn(latitude);
        when(gps.getLongitude()).thenReturn(longitude);

        Location location = mock(Location.class);

        when(location.getAddress()).thenReturn(address);
        when(location.getGps()).thenReturn(gps);

        house = mock(House.class);

        houseName = mock(HouseName.class);
        when(houseName.getName()).thenReturn("houseName");

        when(house.getIdentity()).thenReturn(houseName);
        when(house.getLocation()).thenReturn(location);


    }

    /**
     * Tests that the toHouseName method returns the correct HouseName.
     */
    @Test
    void testToHouseName() {
        //Arrange
        String expectedHouseName = "houseName";
        //Act
        HouseName result = houseMapper.toHouseName(houseDto);
        //Assert
        assertEquals(expectedHouseName, result.getName(), "The house name should be the expected one");
    }

    /**
     * Tests that the toLocation method returns a Location with the correct street name.
     */
    @Test
    void testToLocationStreetName() {
        //Arrange
        String expectedStreetName = "streetName";
        //Act
        Location result = houseMapper.toLocation(houseDto);
        //Assert
        assertEquals(expectedStreetName, result.getAddress().getStreetName().getStreetName(),
                "The street name should be the expected one");
    }

    /**
     * Tests that the toLocation method returns a Location with the correct street number.
     */
    @Test
    void testToLocationStreetNumber() {
        //Arrange
        String expectedStreetNumber = "streetNumber";
        //Act
        Location result = houseMapper.toLocation(houseDto);
        //Assert
        assertEquals(expectedStreetNumber, result.getAddress().getStreetNumber().getStreetNumber(),
                "The street number should be the expected one");
    }

    /**
     * Tests that the toLocation method returns a Location with the correct zip code.
     */
    @Test
    void testToLocationZipCode() {
        //Arrange
        String expectedZipCode = "4000-000";
        //Act
        Location result = houseMapper.toLocation(houseDto);
        //Assert
        assertEquals(expectedZipCode, result.getAddress().getZipCode().getZipCode(),
                "The zip code should be the expected one");
    }

    /**
     * Tests that the toHouseDTO method returns a houseDTO with the correct house name.
     */
    @Test
    void testToHouseDTOHouseName() {
        //Arrange
        String expectedName = "houseName";
        //Act
        HouseDTO result = houseMapper.toHouseDTO(house);
        //Assert
        assertEquals(expectedName, result.getHouseName(), "The house name should be the expected one");
    }

    /**
     * Tests that the toHouseDTO method returns a houseDTO with the correct country.
     */
    @Test
    void testToHouseDTOHouseCountry() {
        //Arrange
        String expectedCountry = "Portugal";
        //Act
        HouseDTO result = houseMapper.toHouseDTO(house);
        //Assert
        assertEquals(expectedCountry, result.getCountry(), "The country should be the expected one");
    }

    /**
     * Tests that the toHouseIdsDTO method returns a list with the correct size.
     */
    @Test
    void testToHouseIdsDTO() {
        //Arrange
        Iterable<HouseName> houseNames = List.of(houseName);
        int expectedSize = 1;
        //Act
        List<HouseIdDTO> result = houseMapper.toHouseIdsDTO(houseNames);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }
}