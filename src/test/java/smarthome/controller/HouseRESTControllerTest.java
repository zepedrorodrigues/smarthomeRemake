package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
import smarthome.mapper.HouseIdDTO;
import smarthome.mapper.mapper.HouseMapper;
import smarthome.service.IHouseService;
import smarthome.service.impl.HouseServiceImpl;
import smarthome.utils.AvailableCountries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HouseRESTControllerTest {

    HouseRESTController houseRESTController;
    HouseMapper validHouseMapper;
    IHouseService validHouseRESTService;
    IHouseRepository houseRepositoryDouble;
    House house;
    HouseFactory houseFactory;
    HouseName houseId;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    HouseDTO houseDTODifferentValidLocation;
    House updatedHouseDifferentValidLocation;
    String houseIdString = "MyHouse";
    HouseDTO responseDTO;
    HouseDTO houseDTO;

    /**
     * Set up the HouseMapper and HouseRESTService objects before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize the HouseMapper
        validHouseMapper = new HouseMapper();
        // Initialize the HouseRESTService
        houseRepositoryDouble = mock(IHouseRepository.class); // Create a mock of IHouseRepository
        houseFactory = new HouseFactoryImpl();
        validHouseRESTService = new HouseServiceImpl(houseRepositoryDouble, houseFactory);
        // Initialize the House object
        houseId = new HouseName(houseIdString);
        StreetName streetName = new StreetName("Avenida de Gaia");
        StreetNumber streetNumber = new StreetNumber("123");
        ZipCode zipCode = new ZipCode("1245-678");
        City city = new City("Porto");
        Country country = new Country("Portugal");
        Gps gps = new Gps(new Latitude(41.133), new Longitude(-8.639));
        Address address = new Address(streetName, streetNumber, zipCode, city, country);
        Location location = new Location(address, gps);
        house = houseFactory.createHouse(houseId, location);
        houseDTO = validHouseMapper.toHouseDTO(house);
        // Initialize the responseDTO
        responseDTO = validHouseMapper.toHouseDTO(house);
        // Different location DTO
        houseDTODifferentValidLocation = new HouseDTO("Hollywood", "3",
                "1234-123", "Los Angeles", "Portugal", 90, 180);
        updatedHouseDifferentValidLocation = houseFactory.createHouse(houseId,
                new Location(
                        new Address(
                                new StreetName(houseDTODifferentValidLocation.getStreetName()),
                                new StreetNumber(houseDTODifferentValidLocation.getStreetNumber()),
                                new ZipCode(houseDTODifferentValidLocation.getZipCode()),
                                new City(houseDTODifferentValidLocation.getCity()),
                                new Country(houseDTODifferentValidLocation.getCountry())),
                        new Gps(
                                new Latitude(houseDTODifferentValidLocation.getLatitude()),
                                new Longitude(houseDTODifferentValidLocation.getLongitude()))));
        // Initialize the HouseRESTController
        houseRESTController = new HouseRESTController(validHouseRESTService, validHouseMapper);
        // Setup the controller and HTTP request
        mockMvc = MockMvcBuilders.standaloneSetup(houseRESTController).build();
        objectMapper = new ObjectMapper();
    }

    // SCENARIOS FOR PROVIDING A LIST OF AVAILABLE COUNTRIES

    /**
     * Test the getAvailableCountries method when called.
     * Should not return null.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldNotReturnNull() {
        // Arrange + Act
        ResponseEntity<List<String>> availableCountriesResponse = houseRESTController.getAvailableCountries();
        // Assert
        assertNotNull(availableCountriesResponse,
                "The response entity should not be null.");
    }

    /**
     * Test the getAvailableCountries method when called and retrieves countries.
     * Should return a list of available countries.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldReturnListOfAvailableCountries() {
        // Arrange
        List<String> expectedCountries = AvailableCountries.getAvailableCountries();
        // Act
        ResponseEntity<List<String>> availableCountriesResponse = houseRESTController.getAvailableCountries();
        // Assert
        assertEquals(expectedCountries, availableCountriesResponse.getBody(),
                "The list of available countries should be returned.");
    }

    /**
     * Test the getAvailableCountries method when called and retrieves countries.
     * Should return HttpStatus OK.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldReturnHttpStatusOK() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.OK;
        // Act
        ResponseEntity<List<String>> availableCountriesResponse = houseRESTController.getAvailableCountries();
        // Assert
        assertEquals(expectedHttpStatus, availableCountriesResponse.getStatusCode(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test the getAvailableCountries method when called and fails to retrieve countries.
     * Should return HttpStatus BAD_REQUEST.
     */
    @Test
    void testGetAvailableCountriesWhenFailsToRetrieveCountriesShouldReturnBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        // Mock the AvailableCountries enum
        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenThrow(new NullPointerException());
            // Act
            ResponseEntity<List<String>> availableCountriesResponse = houseRESTController.getAvailableCountries();
            // Assert
            assertEquals(expectedHttpStatus, availableCountriesResponse.getStatusCode(),
                    "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
        }
    }

    // TEST HOW THE CONTROLLER HANDLES A GET REQUEST TO PROVIDE A LIST OF AVAILABLE COUNTRIES
    // JSON RESPONSE

    /**
     * Test if the controller correctly handles a GET request when http status is OK.
     */
    @Test
    void testGetAvailableCountriesCorrectlyHandlesAGetRequestWhenOkCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/countries";
        int expectedStatus = 200;
        // Set up valid data
        List<String> expectedCountries = AvailableCountries.getAvailableCountries();
        String expectedJson = objectMapper.writeValueAsString(expectedCountries);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test if the controller correctly handles a GET request when http status is OK
     * check the JSON response.
     */
    @Test
    void testGetAvailableCountriesCorrectlyHandlesAGetRequestWhenOkCheckJsonResponse()
            throws Exception {
        // Arrange
        String uri = "/houses/countries";
        // Set up valid data
        List<String> expectedCountries = AvailableCountries.getAvailableCountries();
        String expectedJson = objectMapper.writeValueAsString(expectedCountries);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expectedJson, responseJson,
                "The response JSON should be the same as the input JSON with the list of available countries.");
    }

    /**
     * Test if the controller correctly handles a GET request when the list of available countries does not exist
     */
    @Test
    void testGetAvailableCountriesHandlesBadRequestResponseCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/countries";
        int expectedStatus = 400;

        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenThrow(new NullPointerException());
            // Act
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int resultStatus = mvcResult.getResponse().getStatus();

            // Assert
            assertEquals(expectedStatus, resultStatus,
                    "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
        }
    }

    // SCENARIOS FOR CONFIGURING THE LOCATION OF THE HOUSE - HAPPY PATH

    /**
     * Test when configures the location of the house with
     * valid parameters, it should return a response entity with not null body.
     */
    @Test
    void testConfigHouseLocationWhenValidParametersConfigShouldReturnResponseEntityWithNotNullBody() {
        // Arrange
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));
        when(houseRepositoryDouble.update(updatedHouseDifferentValidLocation))
                .thenReturn(updatedHouseDifferentValidLocation);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController
                .configHouseLocation(houseIdString, houseDTODifferentValidLocation);

        // Assert
        assertNotNull(houseDTOResponse.getBody(),
                "The response body should not be null, meaning the location is configured.");
    }

    /**
     * Test when configures the location of the house with
     * valid parameters, it should return the configured response body.
     */
    @Test
    void testConfigHouseLocationWhenValidParametersConfigShouldReturnTheConfiguredResponseBody() {
        // Arrange
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));
        when(houseRepositoryDouble.update(updatedHouseDifferentValidLocation))
                .thenReturn(updatedHouseDifferentValidLocation);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController
                .configHouseLocation(houseIdString, houseDTODifferentValidLocation);

        // Assert
        HouseDTO houseDTOUpdated = Objects.requireNonNull(houseDTOResponse.getBody());
        assertTrue(houseDTOUpdated.getStreetName().equals(houseDTODifferentValidLocation.getStreetName()) &&
                        houseDTOUpdated.getStreetNumber().equals(houseDTODifferentValidLocation.getStreetNumber()) &&
                        houseDTOUpdated.getZipCode().equals(houseDTODifferentValidLocation.getZipCode()) &&
                        houseDTOUpdated.getCity().equals(houseDTODifferentValidLocation.getCity()) &&
                        houseDTOUpdated.getCountry().equals(houseDTODifferentValidLocation.getCountry()) &&
                        houseDTOUpdated.getLatitude() == houseDTODifferentValidLocation.getLatitude() &&
                        houseDTOUpdated.getLongitude() == houseDTODifferentValidLocation.getLongitude(),
                "The response body should be the same as the input body with the configured location.");

    }

    /**
     * Test when configures the location of the house with
     * valid parameters, it should return HTTP status OK.
     */
    @Test
    void testConfigHouseLocationWhenValidParametersConfigShouldReturnHTTPStatusOK() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.OK;
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));
        when(houseRepositoryDouble.update(updatedHouseDifferentValidLocation))
                .thenReturn(updatedHouseDifferentValidLocation);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController
                .configHouseLocation(houseIdString, houseDTODifferentValidLocation);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }


    // TEST HOW THE CONTROLLER HANDLES A POST REQUEST TO CONFIGURE THE LOCATION OF THE HOUSE
    // JSON RESPONSE

    /**
     * Test if controller correctly handles a PUT request when http status sent is OK.
     */
    @Test
    void testConfigHouseLocationCorrectlyHandlesAPostRequestWhenOkCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseId.getName() + "/location";
        int expectedStatus = 200;
        String inputJson = objectMapper.writeValueAsString(houseDTODifferentValidLocation);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));
        when(houseRepositoryDouble.update(updatedHouseDifferentValidLocation))
                .thenReturn(updatedHouseDifferentValidLocation);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test  if the json response is correct.
     */
    @Test
    void testConfigHouseLocationCorrectlyHandlesAPostRequestIsOkCheckJsonResponse()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseId.getName() + "/location";
        String inputJson = objectMapper.writeValueAsString(houseDTODifferentValidLocation);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));
        when(houseRepositoryDouble.update(updatedHouseDifferentValidLocation))
                .thenReturn(updatedHouseDifferentValidLocation);
        HouseDTO houseDTOExpected = validHouseMapper.toHouseDTO(updatedHouseDifferentValidLocation)
                .add(Link.of("http://localhost/houses/" + houseId.getName()))
                .add(Link.of("http://localhost/houses/" + houseId.getName() + "/location")
                        .withRel("configure-house-location"));
        String expectedJson = objectMapper.writeValueAsString(houseDTOExpected);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expectedJson, responseJson,
                "The response JSON should be the same as the input JSON with the configured location.");
    }

    /**
     * Test if the controller correctly handles a POST request when the input data is invalid
     * and returns HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationHandlesBadRequestNullHouseDTOResponseCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseId.getName() + "/location";
        int expectedStatus = 400;
        String inputJson = objectMapper.writeValueAsString(null);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));
        when(houseRepositoryDouble.update(updatedHouseDifferentValidLocation))
                .thenReturn(updatedHouseDifferentValidLocation);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        int resultStatus = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, resultStatus,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test if the controller correctly handles a POST request when the input data is invalid
     * and returns HTTP status not found.
     */
    @Test
    void testConfigHouseLocationHandlesNotFoundHouseDoesNotExistResponseCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseId.getName() + "/location";
        int expectedStatus = 404;
        String inputJson = objectMapper.writeValueAsString(houseDTODifferentValidLocation);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        int resultStatus = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, resultStatus,
                "The HTTP status should be NOT_FOUND, indicating that the request was unsuccessful.");
    }

    /**
     * Test if the controller correctly handles a POST request when the location input data is invalid
     * and returns HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationHandlesBadRequestInvalidLocationResponseCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseId.getName() + "/location";
        int expectedStatus = 400;
        HouseDTO invalidHouseDTO = new HouseDTO(" ", " ", " ", " ",
                " ", 0, 0);
        String inputJson = objectMapper.writeValueAsString(invalidHouseDTO);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        int resultStatus = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, resultStatus,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    // CONFIGURING THE LOCATION OF THE HOUSE WITH INVALID PARAMETERS

    /**
     * Test when configures the location of the house with invalid street name (blank),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithBlankStreetNameShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidStreetName = " ";
        HouseDTO houseDTO = new HouseDTO(invalidStreetName, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid street name (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithBlankStreetNameShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidStreetName = " ";
        HouseDTO houseDTO = new HouseDTO(invalidStreetName, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when configures the location of the house with invalid street name (null),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithNullStreetNameShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(null, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid street name (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithBlankStreetNumberShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidStreetNumber = " ";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", invalidStreetNumber,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid street number (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithBlankStreetNumberShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidStreetNumber = " ";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", invalidStreetNumber,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when configures the location of the house with invalid street number (null),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithNullStreetNumberShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", null,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid street number (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithNullStreetNumberShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", null,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid city (blank),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithBlankZipCodeShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidZipCode = " ";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                invalidZipCode, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid city (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithBlankZipCodeShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidZipCode = " ";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                invalidZipCode, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when configures the location of the house with invalid zip code that does not match the
     * pattern (xxxx-xxx) to the country (Portugal),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchPortugalCountryPatternShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String validZipCode = "123456";
        String validCountry = "Portugal";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code that does not match the
     * pattern (xxxx-xxx) to the country (Portugal),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchPortugalCountryPatternShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String validZipCode = "123456";
        String validCountry = "Portugal";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code that does not match the
     * pattern (xxxxx) to the country (United States of America, but could be France or Spain too), it should return
     * response entity with null body.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchUSACountryPatternShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String validZipCode = "1234-356";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code that does not match the
     * pattern (xxxxx) to the country (United States of America, but could be France or Spain too), it should return
     * HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchUSACountryPatternShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String validZipCode = "1234-356";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code that does not match any
     * country patter, it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchAnyCountryPatternShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidZipCode = "123";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                invalidZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code that does not match any
     * country patter, it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWhenZipCodeDoesNotMatchAnyCountryPatternShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidZipCode = "123";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                invalidZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code (null),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithNullZipCodeShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                null, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid zip code (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithNullZipCodeShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                null, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid city (blank),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithBlankCityShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidCity = " ";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12348", invalidCity, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid city (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithBlankCityShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidCity = " ";
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12348", invalidCity, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid city (null),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithNullCityShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12348", null, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid city (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithNullCityShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12348", null, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid latitude (lower than -90),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithLatitudeLowerThanNegative90ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLatitude = -91;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid latitude (lower than -90),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithLatitudeLowerThanNegative90ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLatitude = -91;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid latitude (higher than 90),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithLatitudeHigherThan90ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLatitude = 91;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid latitude (higher than 90),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithLatitudeHigherThan90ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLatitude = 91;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid longitude (lower than -180),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithLongitudeLowerThanNegative180ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLongitude = -181.0;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid longitude (lower than -180),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithLongitudeLowerThanNegative180ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLongitude = -181.0;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with invalid longitude (higher than 180),
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithLongitudeHigherThan180ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLongitude = 181.0;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with invalid longitude (higher than 180),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithLongitudeHigherThan180ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLongitude = 181.0;
        HouseDTO houseDTO = new HouseDTO("Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house with null houseDTO,
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWithNullHouseDTOShouldReturnResponseEntityWithNullBody() {
        // Arrange + Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, null);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house with null houseDTO,
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testConfigHouseLocationWithNullHouseDTOShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.configHouseLocation(houseIdString, null);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when configures the location of the house and the house does not exist,
     * it should return response entity with null body.
     */
    @Test
    void testConfigHouseLocationWhenHouseDoesNotExistShouldReturnResponseEntityWithNullBody() {
        // Arrange
        // Mock comportment of the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController
                .configHouseLocation(houseIdString, houseDTODifferentValidLocation);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when configures the location of the house and the house does not exist,
     * it should return HTTP status UNPROCESSABLE_ENTITY.
     */
    @Test
    void testConfigHouseLocationWhenHouseDoesNotExistShouldReturnHTTPStatusNotFound() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;
        // Mock comportment of the houseRepositoryDouble
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController
                .configHouseLocation(houseIdString, houseDTODifferentValidLocation);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be NOT_FOUND, indicating that the request was unsuccessful.");
    }


    // GET HOUSES

    /**
     * Test if the controller correctly handles a GET request when there is one house
     * and returns the house ids in the response body.
     */
    @Test
    void testGetHousesWhenOneHouseByIdCheckResponseBody() {
        // Arrange
        List<HouseName> houseIds = new ArrayList<>();
        houseIds.add(houseId);
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);
        int expectedSize = 1;

        // Act
        ResponseEntity<List<HouseIdDTO>> houseNameDTOResponse = houseRESTController.getHouses();
        int resultSize = Objects.requireNonNull(houseNameDTOResponse.getBody()).size();

        // Assert
        assertEquals(expectedSize, resultSize,
                "The response body should contain one house id.");
    }

    /**
     * Test if the controller correctly handles a GET request when there is one house
     * and returns HTTP status OK.
     */
    @Test
    void testGetHousesWhenOneHouseByIdCheckHttpStatus() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.OK;
        List<HouseName> houseIds = new ArrayList<>();
        houseIds.add(houseId);
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);

        // Act
        ResponseEntity<List<HouseIdDTO>> houseNameDTOResponse = houseRESTController.getHouses();

        // Assert
        assertEquals(expectedHttpStatus, houseNameDTOResponse.getStatusCode(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test if the controller correctly handles a GET request when there is more than one house
     * and returns the house ids in the response body.
     */
    @Test
    void testGetHousesWhenMoreThanOneHouseByIdCheckResponseBody() {
        // Arrange
        List<HouseName> houseIds = new ArrayList<>();
        houseIds.add(houseId);
        HouseName houseId2 = new HouseName("House2");
        houseIds.add(houseId2);
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);
        int expectedSize = 2;

        // Act
        ResponseEntity<List<HouseIdDTO>> houseNameDTOResponse = houseRESTController.getHouses();
        int resultSize = Objects.requireNonNull(houseNameDTOResponse.getBody()).size();

        // Assert
        assertEquals(expectedSize, resultSize);
    }

    /**
     * Test if the controller correctly handles a GET request when there is more than one house
     * and returns HTTP status OK.
     */
    @Test
    void testGetHousesWhenMoreThanOneHouseByIdCheckHttpStatus() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.OK;
        List<HouseName> houseIds = new ArrayList<>();
        houseIds.add(houseId);
        HouseName houseId2 = new HouseName("House2");
        houseIds.add(houseId2);
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);

        // Act
        ResponseEntity<List<HouseIdDTO>> houseIdDTOResponse = houseRESTController.getHouses();

        // Assert
        assertEquals(expectedHttpStatus, houseIdDTOResponse.getStatusCode(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test if the controller correctly handles a GET request when there are no houses
     * and returns null in the response body.
     */
    @Test
    void testGetHousesWhenNoHouseByIdCheckResponseBody() {
        // Arrange
        List<HouseName> houseIds = new ArrayList<>();
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);

        // Act
        ResponseEntity<List<HouseIdDTO>> houseIdDTOResponse = houseRESTController.getHouses();

        // Assert
        assertNull(houseIdDTOResponse.getBody(),
                "The response body should be null.");
    }

    /**
     * Test if the controller correctly handles a GET request when there are no houses
     * and returns HTTP status NOT_FOUND.
     */
    @Test
    void testGetHousesWhenNoHouseByIdCheckHttpStatus() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;
        List<HouseName> houseIds = new ArrayList<>();
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);

        // Act
        ResponseEntity<List<HouseIdDTO>> houseIdDTOResponse = houseRESTController.getHouses();

        // Assert
        assertEquals(expectedHttpStatus, houseIdDTOResponse.getStatusCode(),
                "The HTTP status should be NOT_FOUND, indicating that the request was unsuccessful.");
    }

    /**
     * Test if the controller correctly handles a GET request when an exception is thrown
     * and returns null in the response body.
     */
    @Test
    void testGetHousesThrowsExceptionCheckResponseBodyById() {
        // Arrange
        when(houseRepositoryDouble.findHouseIds()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<HouseIdDTO>> houseIdDTOResponse = houseRESTController.getHouses();

        // Assert
        assertNull(houseIdDTOResponse.getBody(),
                "The response body should be null.");
    }

    /**
     * Test if the controller correctly handles a GET request when an exception is thrown
     * and returns HTTP status BAD_REQUEST.
     */
    @Test
    void testGetHousesThrowsExceptionCheckHttpStatusById() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        when(houseRepositoryDouble.findHouseIds()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<HouseIdDTO>> houseIdDTOResponse = houseRESTController.getHouses();

        // Assert
        assertEquals(expectedHttpStatus, houseIdDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test if the controller correctly handles a GET request when there is one house
     * and returns the house ids in the response body.
     */
    @Test
    void testGetHousesCorrectlyHandlesAGetRequestWhenOkCheckHttpStatusById()
            throws Exception {
        // Arrange
        String uri = "/houses";
        int expectedStatus = HttpStatus.OK.value();

        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findHouseIds()).thenReturn(List.of(houseId));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test if the controller correctly handles a GET request when there is one house
     * and returns the house ids in the response body.
     */
    @Test
    void testGetHousesCorrectlyHandlesAGetRequestWhenOkCheckResponseBodyById()
            throws Exception {
        // Arrange
        String uri = "/houses";
        HouseIdDTO houseIdDTO = new HouseIdDTO(houseId.getName())
                .add(Link.of("http://localhost/houses/" + houseId.getName()))
                .add(Link.of("http://localhost/houses/" + houseId.getName() + "/location")
                        .withRel("configure-house-location"));
        String expectedJson = objectMapper.writeValueAsString(List.of(houseIdDTO));

        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findHouseIds()).thenReturn(List.of(houseId));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expectedJson, content,
                "The response body should contain the house ids.");
    }

    /**
     * Test if the controller correctly handles a GET request when there are no houses
     * and returns HTTP status NOT_FOUND.
     */
    @Test
    void testGetHousesCorrectlyHandlesAGetRequestWhenNotFoundCheckHttpStatusById()
            throws Exception {
        // Arrange
        String uri = "/houses";
        int expectedStatus = HttpStatus.NOT_FOUND.value();

        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findHouseIds()).thenReturn(new ArrayList<>());

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be NOT_FOUND, the request was successful but there is no content.");
    }

    /**
     * Test if the controller correctly handles a GET request when there is an exception
     * and returns HTTP status BAD_REQUEST.
     */
    @Test
    void testGetHousesCorrectlyHandlesAGetRequestWhenThrowsExceptionCheckHttpStatusById()
            throws Exception {
        // Arrange
        String uri = "/houses";
        int expectedStatus = HttpStatus.BAD_REQUEST.value();

        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.findHouseIds()).thenThrow(new RuntimeException());

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test case for when the house exists. It should return a response body with the correct house id.
     */
    @Test
    void testGetHouseByIdWhenHouseExistsShouldReturnResponseBodyWithCorrectHouseId() {
        // Arrange
        HouseDTO expected = responseDTO;
        when(houseRepositoryDouble.findByIdentity(any(HouseName.class))).thenReturn(Optional.of(house));

        // Act
        ResponseEntity<HouseDTO> result = houseRESTController.getHouseById(houseIdString);
        HouseDTO resultDTO = result.getBody();

        // Assert
        assert resultDTO != null;
        assertEquals(expected.getHouseName(), resultDTO.getHouseName(),
                "The house id in the HouseDTO object in the response body should be the same as the expected id.");
    }

    /**
     * Test case for when the house does not exist. It should return a null response body.
     */
    @Test
    void testGetHouseWhenHouseByIdDoesNotExistShouldReturnNullResponseBody() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<HouseDTO> result = houseRESTController.getHouseById(houseIdString);

        // Assert
        assertNull(result.getBody(),
                "The response body should be null, meaning that the house does not exist.");
    }

    /**
     * Test case for when an exception is thrown. It should return a null response body.
     */
    @Test
    void testGetHouseByIdWhenAnExceptionIsThrownShouldReturnNullResponseBody() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<HouseDTO> result = houseRESTController.getHouseById(houseIdString);

        // Assert
        assertNull(result.getBody(),
                "The response body should be null, meaning that the house does not exist.");
    }

    /**
     * Test case for when the house exists. It should return the correct HTTP status.
     */
    @Test
    void testGetHouseByIdWhenHouseByIdExistsShouldReturnCorrectHTTPStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.OK;
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));

        // Act
        ResponseEntity<HouseDTO> result = houseRESTController.getHouseById(houseIdString);

        // Assert
        assertEquals(expected, result.getStatusCode(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test case for when the house does not exist. It should return the correct HTTP status.
     */
    @Test
    void testGetHouseByIdWhenHouseByIdDoesNotExistShouldReturnCorrectHTTPStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.NOT_FOUND;
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<HouseDTO> result = houseRESTController.getHouseById(houseIdString);

        // Assert
        assertEquals(expected, result.getStatusCode(),
                "The HTTP status should be NOT_FOUND, indicating that the house does not exist.");
    }

    /**
     * Test case for when an exception is thrown. It should return the correct HTTP status.
     */
    @Test
    void testGetHouseByIdWhenExceptionIsThrownShouldReturnCorrectHTTPStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        when(houseRepositoryDouble.findByIdentity(houseId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<HouseDTO> result = houseRESTController.getHouseById(houseIdString);

        // Assert
        assertEquals(expected, result.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that an exception was thrown.");
    }

    /**
     * Test case using Mock MVC for when the house exists. It should return the correct HTTP status.
     */
    @Test
    void testGetHouseByIdCorrectlyHandlesAGetRequestWhenHouseByIdExistsCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseIdString;
        int expectedStatus = HttpStatus.OK.value();
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test case using Mock MVC for when the house does not exist. It should return the correct HTTP status.
     */
    @Test
    void testGetHouseByIdCorrectlyHandlesAGetRequestWhenHouseByIdDoesNotExistCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseIdString;
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be NOT_FOUND, indicating that the request was" +
                        "but there is no content.");
    }

    /**
     * Test case using Mock MVC for when an exception is thrown. It should return the correct HTTP status.
     */
    @Test
    void testGetHouseByIdCorrectlyHandlesAGetRequestWhenAnExceptionIsThrownCheckHttpStatusById()
            throws Exception {
        // Arrange
        String uri = "/houses/" + houseIdString;
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        when(houseRepositoryDouble.findByIdentity(houseId)).thenThrow(RuntimeException.class);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test case using Mock MVC for when the house exists. It should return the correct response body.
     */
    @Test
    void testGetHouseByIdCorrectlyHandlesAGetRequestWhenHouseByIdExistsCheckResponseBody()
            throws Exception {
        // Arrange
        HouseDTO expectedDTO = responseDTO
                .add(Link.of("http://localhost/houses/" + houseId.getName()))
                .add(Link.of("http://localhost/houses/" + houseId.getName() + "/location")
                        .withRel("configure-house-location"));
        String expectedJSON = objectMapper.writeValueAsString(expectedDTO);
        String uri = "/houses/" + houseIdString;
        when(houseRepositoryDouble.findByIdentity(houseId)).thenReturn(Optional.of(house));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        assertEquals(expectedJSON, mvcResult.getResponse().getContentAsString(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }


    // SCENARIOS FOR ADDING A HOUSE - HAPPY PATH

    /**
     * Test when adding a house with valid parameters, it should return response entity with not null body.
     */
    @Test
    void testAddHouseWhenValidParametersShouldReturnResponseEntityWithNotNullBody() {
        // Arrange
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.save(house)).thenReturn(house);
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(false);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);
        HouseDTO result = Objects.requireNonNull(houseDTOResponse.getBody());

        // Assert
        assertTrue(result.getHouseName().equals(houseDTO.getHouseName()) &&
                        result.getStreetName().equals(houseDTO.getStreetName()) &&
                        result.getStreetNumber().equals(houseDTO.getStreetNumber()) &&
                        result.getZipCode().equals(houseDTO.getZipCode()) &&
                        result.getCity().equals(houseDTO.getCity()) &&
                        result.getCountry().equals(houseDTO.getCountry()) &&
                        result.getLatitude() == houseDTO.getLatitude() &&
                        result.getLongitude() == houseDTO.getLongitude(),
                "The response body should be the same as the input body.");
    }

    /**
     * Test when adding a house with valid parameters, it should return http status CREATED.
     */
    @Test
    void testAddHouseWhenValidParametersShouldReturnHTTPStatusCreated() {
        // Arrange
        // Mock the houseRepositoryDouble
        HttpStatus expectedHttpStatus = HttpStatus.CREATED;
        when(houseRepositoryDouble.save(house)).thenReturn(house);
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(false);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);


        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be CREATED, indicating that the request was successful.");
    }

    // TEST HOW THE CONTROLLER HANDLES A POST REQUEST TO ADD A HOUSE

    /**
     * Test when adding a house with valid parameters, it should return http status CREATED.
     */
    @Test
    void testAddHouseWithMockMVCWhenValidParametersShouldReturnHTTPStatusCreated()
            throws Exception {
        // Arrange
        String uri = "/houses";
        HttpStatus expectedHttpStatus = HttpStatus.CREATED;
        String inputJson = objectMapper.writeValueAsString(houseDTO);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.save(house)).thenReturn(house);
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(false);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        HttpStatus result = HttpStatus.valueOf(mvcResult.getResponse().getStatus());

        // Assert
        assertEquals(expectedHttpStatus, result,
                "The HTTP status should be CREATED, indicating that the request was successful.");
    }

    /**
     * Test when adding a house with valid parameters, it should return response body with correct house data.
     */
    @Test
    void testAddHouseWithMockMVCWhenValidParametersShouldReturnCorrectResponseBody()
            throws Exception {
        // Arrange
        String uri = "/houses";
        houseDTO.add(Link.of("http://localhost/houses/" + houseId.getName()))
                .add(Link.of("http://localhost/houses/" + houseId.getName() + "/location")
                        .withRel("configure-house-location"));
        String inputJson = objectMapper.writeValueAsString(houseDTO);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.save(house)).thenReturn(house);
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(false);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(inputJson, result,
                "The response body should be the same as the input body.");
    }

    /**
     * Test when adding a house with invalid parameters, it should return http status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithMockMVCWhenInvalidParametersShouldReturnHTTPStatusBadRequest()
            throws Exception {
        // Arrange
        String uri = "/houses";
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String inputJson = objectMapper.writeValueAsString(null);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        HttpStatus result = HttpStatus.valueOf(mvcResult.getResponse().getStatus());

        // Assert
        assertEquals(expectedHttpStatus, result,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adding a house with invalid parameters, it should return http status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithMockMVCWhenHouseNameExistsShouldReturnHTTPStatusBadRequest()
            throws Exception {
        // Arrange
        String uri = "/houses";
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String inputJson = objectMapper.writeValueAsString(houseDTO);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(true);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        HttpStatus result = HttpStatus.valueOf(mvcResult.getResponse().getStatus());

        // Assert
        assertEquals(expectedHttpStatus, result,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adding a house with invalid parameters, it should return http status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithMockMVCWhenBlankHouseNameShouldReturnHTTPStatusBadRequest()
            throws Exception {
        // Arrange
        String uri = "/houses";
        HouseDTO houseDTO = new HouseDTO(" ", "Street", "123",
                "12348", "New York", "United States of America", 90, 180);
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String inputJson = objectMapper.writeValueAsString(houseDTO);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.save(house)).thenReturn(house);
        when(houseRepositoryDouble.containsIdentity(houseId)).thenThrow(RuntimeException.class);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        HttpStatus result = HttpStatus.valueOf(mvcResult.getResponse().getStatus());

        // Assert
        assertEquals(expectedHttpStatus, result,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    // SCENARIOS FOR ADDING A HOUSE - NULL HOUSE DTO

    /**
     * Test when adding a house with null houseDTO, it should return http status BAD_REQUEST.
     */
    @Test
    void testAddHouseWhenHouseDTOIsNull() {
        // Arrange
        HouseDTO nullHouseDTO = null;
        // Mock the houseRepositoryDouble
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        when(houseRepositoryDouble.save(house)).thenReturn(house);
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(false);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(nullHouseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be CREATED, indicating that the request was successful.");
    }


    // ADDING A HOUSE WITH INVALID PARAMETERS

    /**
     * Test when adding a house with invalid house id (already exists),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWhenHouseIdAlreadyExistsResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Street", "123",
                "12348", "New York", "United States of America", 90, 180);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(true);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the addition was not successful.");
    }

    /**
     * Test when adds a house with invalid id (already exists),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWhenHouseIdAlreadyExistsShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Street", "123",
                "12348", "New York", "United States of America", 90, 180);
        // Mock the houseRepositoryDouble
        when(houseRepositoryDouble.containsIdentity(houseId)).thenReturn(true);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when add a house with invalid house id (blank),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithBlankHouseIdShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidStreetName = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, invalidStreetName, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the addition was not successful.");
    }

    /**
     * Test when adds a house with invalid id (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testHouseWithBlankHouseIdShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidHouseId = " ";
        HouseDTO houseDTO = new HouseDTO(invalidHouseId, "Street", "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when add a house with invalid house id (null),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithNullHouseIdShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidHouseId = null;
        HouseDTO houseDTO = new HouseDTO(invalidHouseId, "Street", "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the addition was not successful.");
    }

    /**
     * Test when adds a house with invalid id (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testHouseWithNullHouseIdShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidHouseId = null;
        HouseDTO houseDTO = new HouseDTO(invalidHouseId, "Street", "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when add a house with invalid street name (blank),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithBlankStreetNameShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidStreetName = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, invalidStreetName, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the addition was not successful.");
    }

    /**
     * Test when adds a house with invalid street name (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void tesHouseWithBlankStreetNameShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidStreetName = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, invalidStreetName, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when adds a house with invalid street name (null),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithNullStreetNameShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseIdString, null, "123",
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid street name (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithBlankStreetNumberShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidStreetNumber = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", invalidStreetNumber,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid street number (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithBlankStreetNumberShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidStreetNumber = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", invalidStreetNumber,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when adds a house with invalid street number (null),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithNullStreetNumberShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", null,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid street number (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithNullStreetNumberShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", null,
                "12348", "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid city (blank),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithBlankZipCodeShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidZipCode = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                invalidZipCode, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid city (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithBlankZipCodeShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidZipCode = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                invalidZipCode, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }


    /**
     * Test when adds a house with invalid zip code that does not match the
     * pattern (xxxx-xxx) to the country (Portugal),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWhenZipCodeDoesNotMatchPortugalCountryPatternShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String validZipCode = "12345";
        String validCountry = "Portugal";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid zip code that does not match the
     * pattern (xxxx-xxx) to the country (Portugal),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWhenZipCodeDoesNotMatchPortugalCountryPatternShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String validZipCode = "12345";
        String validCountry = "Portugal";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid zip code that does not match the
     * pattern (xxxxx) to the country (United States of America, but could be France or Spain too), it should return
     * response entity with null body.
     */
    @Test
    void testAddHouseWhenZipCodeDoesNotMatchUSACountryPatternShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String validZipCode = "1234-356";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid zip code that does not match the
     * pattern (xxxxx) to the country (United States of America, but could be France or Spain too), it should return
     * HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWhenZipCodeDoesNotMatchUSACountryPatternShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String validZipCode = "1234-356";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                validZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid zip code that does not match any
     * country patter, it should return response entity with null body.
     */
    @Test
    void testAddHouseWhenZipCodeDoesNotMatchAnyCountryPatternShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidZipCode = "123";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                invalidZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid zip code that does not match any
     * country patter, it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWhenZipCodeDoesNotMatchAnyCountryPatternShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidZipCode = "123";
        String validCountry = "United States of America";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                invalidZipCode, "New York", validCountry, 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid zip code (null),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithNullZipCodeShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                null, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid zip code (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithNullZipCodeShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                null, "New York", "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }
    //

    /**
     * Test when adds a house with invalid city (blank),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithBlankCityShouldReturnResponseEntityWithNullBody() {
        // Arrange
        String invalidCity = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12348", invalidCity, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid city (blank),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithBlankCityShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String invalidCity = " ";
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12348", invalidCity, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid city (null),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithNullCityShouldReturnResponseEntityWithNullBody() {
        // Arrange
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12348", null, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null, meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid city (null),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithNullCityShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12348", null, "United States of America", 90, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid latitude (lower than -90),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithLatitudeLowerThanNegative90ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLatitude = -91;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid latitude (lower than -90),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithLatitudeLowerThanNegative90ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLatitude = -91;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid latitude (higher than 90),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithLatitudeHigherThan90ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLatitude = 91;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid latitude (higher than 90),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithLatitudeHigherThan90ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLatitude = 91;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12343", "New York", "United States of America", invalidLatitude, 180);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid longitude (lower than -180),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithLongitudeLowerThanNegative180ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLongitude = -181.0;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid longitude (lower than -180),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithLongitudeLowerThanNegative180ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLongitude = -181.0;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test when adds a house with invalid longitude (higher than 180),
     * it should return response entity with null body.
     */
    @Test
    void testAddHouseWithLongitudeHigherThan180ShouldReturnResponseEntityWithNullBody() {
        // Arrange
        double invalidLongitude = 181.0;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertNull(houseDTOResponse.getBody(),
                "The response body should be null meaning that the configuration was not successful.");
    }

    /**
     * Test when adds a house with invalid longitude (higher than 180),
     * it should return HTTP status BAD_REQUEST.
     */
    @Test
    void testAddHouseWithLongitudeHigherThan180ShouldReturnHTTPStatusBadRequest() {
        // Arrange
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        double invalidLongitude = 181.0;
        HouseDTO houseDTO = new HouseDTO(houseIdString, "Riverside Drive", "123",
                "12342", "New York", "United States of America", 90, invalidLongitude);

        // Act
        ResponseEntity<HouseDTO> houseDTOResponse = houseRESTController.addHouse(houseDTO);

        // Assert
        assertEquals(expectedHttpStatus, houseDTOResponse.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }
}
