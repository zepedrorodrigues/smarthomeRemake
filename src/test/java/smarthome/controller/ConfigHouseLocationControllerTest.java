package smarthome.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.HouseFactoryImpl;
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
import smarthome.domain.repository.IHouseRepository;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.mapper.HouseMapper;
import smarthome.service.IHouseService;
import smarthome.service.impl.HouseServiceImpl;
import smarthome.utils.AvailableCountries;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration tests for the ConfigHouseLocationController class.
 * Along with constructor tests, this class contains tests for the following scenarios:
 * - Providing a list of available countries
 * - Configuring the location of the house for the first time with valid parameters and persistence
 * - Configuring the location of the house after the first time with valid parameters and persistence
 * - Configuring the location of the house with invalid parameters and persistence
 */
class ConfigHouseLocationControllerTest {

    House house;
    IHouseRepository houseRepositoryImpl;
    IHouseService houseService;
    HouseFactory houseFactory;
    HouseName houseName;
    String houseNameString;
    HouseMapper houseMapper;
    ConfigHouseLocationController configHouseLocationController;

    /**
     * Initializes the House object and the ConfigHouseLocationController object before each test.
     * The House object is created with the name "House1" and the location "Wall Street Porto 777, 1234-567 Porto".
     * Is created a mock of the house repository.
     * The ConfigHouseLocationController object is initialized with valid HouseMapper and mock IHouseRepository.
     */
    @BeforeEach
    void setUp() {
        // Initialize Value Objects of the House
        houseNameString = "House1";
        houseName = new HouseName(houseNameString);
        Gps gps = new Gps(new Latitude(90), new Longitude(180));
        Address address = new Address(new StreetName("Wall Street Porto"), new StreetNumber("777"),
                new ZipCode("1234-567"), new City("Porto"), new Country("Portugal"));
        Location location = new Location(address, gps);
        // Create a House object and the mock IHouseRepository
        houseRepositoryImpl = mock(IHouseRepository.class);
        houseFactory = new HouseFactoryImpl();
        house = houseFactory.createHouse(houseName, location);
        // Create a houseService
        houseMapper = new HouseMapper();
        houseService = new HouseServiceImpl(houseRepositoryImpl, houseFactory);
        // Initialize a valid ConfigHouseLocationController object
        configHouseLocationController =
                new ConfigHouseLocationController(houseService, houseMapper);
    }


    /**
     * Test when the ConfigHouseLocationController constructor is called with valid parameters,
     * it should not throw any exceptions.
     */
    @Test
    void testConfigHouseLocationControllerConstructorWithValidParametersShouldNotThrowExceptions() {
        // Arrange + Act + Assert
        assertDoesNotThrow(() -> new ConfigHouseLocationController(houseService, houseMapper),
                "The ConfigHouseLocationController constructor should not throw any exceptions.");
    }

    /**
     * Test when the ConfigHouseLocationController constructor is called with valid parameters,
     * it should not initialize null.
     */
    @Test
    void testConfigHouseLocationControllerConstructorWithValidParametersShouldNotInitializeNull() {
        // Arrange + Act + Assert
        assertNotNull(configHouseLocationController,
                "The ConfigHouseLocationController object should not be null.");
    }

    /**
     * Test when the ConfigHouseLocationController constructor is called with null HouseService,
     * it should throw an IllegalArgumentException.
     */
    @Test
    void testConfigHouseLocationControllerConstructorWithNullHouseMapperShouldThrowIllegalArgumentException() {
        // Arrange + Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                        new ConfigHouseLocationController(null, houseMapper),
                "The ConfigHouseLocationController constructor should throw an IllegalArgumentException.");
    }

    /**
     * Test when the ConfigHouseLocationController constructor is called with null HouseMapper,
     * it should throw an IllegalArgumentException.
     */
    @Test
    void testConfigHouseLocationControllerConstructorWithNullHouseServiceShouldThrowIllegalArgumentException() {
        // Arrange + Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                        new ConfigHouseLocationController(houseService, null),
                "The ConfigHouseLocationController constructor should throw an IllegalArgumentException.");
    }

    // SCENARIOS FOR PROVIDING A LIST OF AVAILABLE COUNTRIES

    /**
     * Test when getAvailableCountries method is called, it should not return null.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldNotReturnNull() {
        // Arrange + Act
        List<String> availableCountries = configHouseLocationController.getAvailableCountries();
        // Assert
        assertNotNull(availableCountries, "The list of available countries should not be null.");
    }

    /**
     * Test when getAvailableCountries method is called, it should return the actual available countries.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldNotReturnEmpty() {
        // Arrange
        boolean isEmptyExpected = false;
        // Act
        List<String> availableCountries = configHouseLocationController.getAvailableCountries();
        boolean isEmptyAfter = availableCountries.isEmpty();

        // Assert
        assertEquals(isEmptyExpected, isEmptyAfter,
                "List of countries should not be empty.");
    }

    /**
     * Test the getAvailableCountries method when called and fails to retrieve countries.
     * Should return null.
     */
    @Test
    void testGetAvailableCountriesWhenFailsToRetrieveCountriesShouldReturnNull() {
        // Arrange
        // Mock the AvailableCountries enum
        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenThrow(new NullPointerException());
            // Act
            List<String> availableCountries = configHouseLocationController.getAvailableCountries();
            // Assert
            assertNull(availableCountries, "The list of available countries should be null.");
        }
    }

    // SCENARIOS FOR CONFIGURING THE LOCATION OF THE HOUSE FOR THE FIRST TIME AND PERSISTENCE WITH THE NEW LOCATION

    /**
     * Test when configures the location of the house with
     * valid parameters, it should return a valid HouseDTO object, not null.
     */
    @Test
    void testConfigHouseLocationShouldReturnNotNull() {
        // Arrange
        String newStreetName = "Hollywood Blvd";
        String newStreetNumber = "123";
        String newZipCode = "13454";
        String newCity = "Los Angeles";
        String newCountry = "United States of America";
        double newLatitude = 90;
        double newLongitude = 180;
        HouseDTO houseDTO = new HouseDTO(houseNameString, newStreetName, newStreetNumber,
                newZipCode, newCity, newCountry, newLatitude, newLongitude);
        Location newLocation = new Location(
                new Address(
                        new StreetName(newStreetName), new StreetNumber(newStreetNumber), new ZipCode(newZipCode),
                        new City(newCity), new Country(newCountry)),
                new Gps(
                        new Latitude(newLatitude), new Longitude(newLongitude)));
        House updatedHouse = houseFactory.createHouse(houseName, newLocation);
        when(houseRepositoryImpl.findByIdentity(houseName)).thenReturn(java.util.Optional.of(house));
        when(houseRepositoryImpl.update(updatedHouse)).thenReturn(updatedHouse);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNotNull(result,
                "The HouseDTO object should not be null after configuration.");
    }

    /**
     * Test when configures the location of the house with valid parameters,
     * the result dto should be with the same location attributes as the requested dto.
     */
    @Test
    void testConfigHouseLocationTheLocationShouldBeChanged() {
        // Arrange
        String newStreetName = "Hollywood Blvd";
        String newStreetNumber = "123";
        String newZipCode = "13458";
        String newCity = "Los Angeles";
        String newCountry = "United States of America";
        double newLatitude = 90;
        double newLongitude = 180;
        HouseDTO requestedHouseDTO = new HouseDTO(houseNameString, newStreetName, newStreetNumber,
                newZipCode, newCity, newCountry, newLatitude, newLongitude);
        Location newLocation = new Location(
                new Address(
                        new StreetName(newStreetName), new StreetNumber(newStreetNumber), new ZipCode(newZipCode),
                        new City(newCity), new Country(newCountry)),
                new Gps(
                        new Latitude(newLatitude), new Longitude(newLongitude)));
        House updatedHouse = houseFactory.createHouse(houseName, newLocation);
        when(houseRepositoryImpl.findByIdentity(houseName)).thenReturn(Optional.ofNullable(house));
        when(houseRepositoryImpl.update(updatedHouse)).thenReturn(updatedHouse);
        // Act
        configHouseLocationController.configHouseLocation(requestedHouseDTO);
        boolean result = (requestedHouseDTO.getStreetName().equals(newStreetName) &&
                requestedHouseDTO.getStreetNumber().equals(newStreetNumber) &&
                requestedHouseDTO.getZipCode().equals(newZipCode) &&
                requestedHouseDTO.getCity().equals(newCity) &&
                requestedHouseDTO.getCountry().equals(newCountry) &&
                requestedHouseDTO.getLatitude() == newLatitude &&
                requestedHouseDTO.getLongitude() == newLongitude);

        // Assert
        assertTrue(result,
                "The requested dto should be the same as the result dto if the location was changed.");

    }

    // SCENARIOS FOR CONFIGURING THE LOCATION OF THE HOUSE WITH INVALID PARAMETERS AND PERSISTENCE WITH THE ATTEMPTS

    /**
     * Test when the administrator configures the location of the house with invalid street name (blank),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithBlankStreetNameShouldReturnNull() {
        // Arrange
        String invalidStreetName = " ";
        HouseDTO houseDTO = new HouseDTO(houseNameString, invalidStreetName, "123",
                "12348", "New York", "United States of America", 90, 180);
        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);
        // Assert
        assertNull(result,
                "The HouseDTO object should be null,  meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid street name (null),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithNullStreetNameShouldReturnNull() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseNameString, null, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null  meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid street number (blank),
     * it should return null.
     */
    @Test
    void testConfigHouseLocationWithBlankStreetNumberShouldReturnNull() {
        // Arrange
        String invalidStreetNumber = " ";
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", invalidStreetNumber,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid street number (null),
     * it should return null.
     */
    @Test
    void testConfigHouseLocationWithNullStreetNumberShouldReturnNull() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", null,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid zip code (blank),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithBlankZipCodeShouldReturnNull() {
        // Arrange
        String invalidZipCode = " ";
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                invalidZipCode, "New York", "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result, "The HouseDTO object should be null.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid zip code that does not match the
     * pattern (xxxx-xxx) to the country (Portugal), it should return null meaning that the configuration
     * was not successful.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchPortugalCountryPatternShouldReturnNull() {
        // Arrange
        String validZipCode = "123456";
        String validCountry = "Portugal";
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid zip code that does not match the
     * pattern (xxxxx) to the country (United States of America, but could be France or Spain too), it should return
     * null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchUSACountryPatternShouldReturnNull() {
        // Arrange
        String validZipCode = "1234-356";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid zip code that does not match any
     * country patter, it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchAnyCountryPatternShouldReturnNull() {
        // Arrange
        String invalidZipCode = "123";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                invalidZipCode, "New York", validCountry, 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid zip code (null),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithNullZipCodeShouldReturnNull() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                null, "New York", "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when the administrator configures the location of the house with invalid city (blank),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithBlankCityShouldReturnNull() {
        // Arrange
        String invalidCity = " ";
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                "12348", invalidCity, "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid city (null),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithNullCityShouldReturnNull() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                "12348", null, "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid latitude (lower than -90),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithLatitudeLowerThanNegative90ShouldReturnNull() {
        // Arrange
        double invalidLatitude = -91;
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid latitude (higher than 90),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithLatitudeHigherThan90ShouldReturnNull() {
        // Arrange
        double invalidLatitude = 91;
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid longitude (lower than -180),
     * it should return null.
     */
    @Test
    void testConfigHouseLocationWithLongitudeLowerThanNegative180ShouldReturnNull() {
        // Arrange
        double invalidLongitude = -181.0;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null  meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with invalid longitude (higher than 180),
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithLongitudeHigherThan180ShouldReturnNull() {
        // Arrange
        double invalidLongitude = 181.0;
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator configures the location of the house with null HouseDTO,
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWithNullHouseDTOShouldReturnNullAndShouldNotChangeTheLocation() {
        // Arrange + Act
        HouseDTO result = configHouseLocationController.configHouseLocation(null);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


    /**
     * Test when the administrator try to configure the location of a house that does not exist in the HouseRepository,
     * it should return null meaning that the configuration was not successful.
     */
    @Test
    void testConfigHouseLocationWhenHouseDoesNotExistShouldReturnNull() {
        // Arrange
        IHouseRepository emptyHouseRepository = mock(IHouseRepository.class);
        when(emptyHouseRepository.findByIdentity(houseName)).thenReturn(Optional.empty());
        HouseDTO houseDTO = new HouseDTO(houseNameString, "Riverside Drive", "123",
                "12347", "New York", "United States of America", 90, 180);

        // Act
        HouseDTO result = configHouseLocationController.configHouseLocation(houseDTO);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null meaning that the configuration was not successful.");
    }


}


