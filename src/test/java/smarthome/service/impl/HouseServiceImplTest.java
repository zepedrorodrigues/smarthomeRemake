package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;
import smarthome.domain.repository.IHouseRepository;
import smarthome.service.IHouseService;
import smarthome.utils.AvailableCountries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the HouseServiceImpl class.
 */
class HouseServiceImplTest {

    IHouseRepository houseRepositoryDouble;
    HouseFactory houseFactoryDouble;
    IHouseService houseService;
    Location newLocationDouble;
    Location locationDouble;
    House houseDouble;
    HouseName houseIdDouble;

    /**
     * Arrange the objects for each test.
     */
    @BeforeEach
    void setUp() {
        // Mock the repository
        houseRepositoryDouble = mock(IHouseRepository.class);
        // Initialize the service
        houseFactoryDouble = mock(HouseFactory.class);
        houseService = new HouseServiceImpl(houseRepositoryDouble, houseFactoryDouble);
        // Mock objects
        houseIdDouble = mock(HouseName.class);
        houseDouble = mock(House.class);
        newLocationDouble = mock(Location.class);
        locationDouble = mock(Location.class);
    }


    /**
     * Test the constructor of the HouseServiceImpl
     */
    @Test
    void testConstructorWhenValidHouseRepositoryShouldNotBeNull() {
        // Arrange + Act
        HouseServiceImpl houseServiceImpl = new HouseServiceImpl(houseRepositoryDouble, houseFactoryDouble);

        // Assert
        assertNotNull(houseServiceImpl,
                "HouseServiceImpl should not be null.");
    }

    /**
     * Test when getAvailableCountries method is called, it should not return null.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldNotReturnNull() {
        // Arrange
        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenReturn(Arrays.asList("USA", "Portugal", "Spain"));

            // Act
            List<String> countries = houseService.getAvailableCountries();

            // Assert
            assertNotNull(countries, "List of countries should not be null.");
        }
    }

    /**
     * Test when getAvailableCountries method is called, it should return the actual available countries.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldReturnAvailableCountries() {
        // Arrange
        ArrayList<String> expectedCountries = new ArrayList<>(Arrays.asList("USA", "Portugal", "Spain"));

        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenReturn(expectedCountries);

            // Act
            List<String> resultCountries = houseService.getAvailableCountries();

            // Assert
            assertEquals(expectedCountries, resultCountries,
                    "List of resultCountries should be the same as the expected list of resultCountries.");
        }
    }

    /**
     * Test when configures the location with valid parameters,
     * it should return the House object with the location configured, not null.
     */
    @Test
    void testConfigHouseLocationWithValidParametersShouldReturnNotNull() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.of(houseDouble));
        when(houseDouble.configLocation(newLocationDouble)).thenReturn(newLocationDouble);
        when(houseRepositoryDouble.update(houseDouble)).thenReturn(houseDouble);

        // Act
        House houseResult = houseService.configHouseLocation(houseIdDouble, newLocationDouble);

        // Assert
        assertNotNull(houseResult,
                "The House object should not be null, meaning the location is configured.");
    }

    /**
     * Test when configures the location with valid parameters,
     * it should call the configLocation method of the House object.
     */
    @Test
    void testConfigHouseLocationVerifyIfCallsConfigLocation() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.of(houseDouble));
        when(houseDouble.configLocation(newLocationDouble)).thenReturn(newLocationDouble);
        when(houseRepositoryDouble.update(houseDouble)).thenReturn(houseDouble);

        // Act
        houseService.configHouseLocation(houseIdDouble, newLocationDouble);

        // Assert
        verify(houseDouble).configLocation(newLocationDouble);
    }

    /**
     * Test configHouseLocation method when the house is updated successfully,
     * it should call the update method of the repository.
     */
    @Test
    void testConfigHouseLocationVerifyIfCallsRepositoryUpdate() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.of(houseDouble));
        when(houseDouble.configLocation(newLocationDouble)).thenReturn(newLocationDouble);
        when(houseRepositoryDouble.update(houseDouble)).thenReturn(houseDouble);

        // Act
        houseService.configHouseLocation(houseIdDouble, newLocationDouble);

        // Assert
        verify(houseRepositoryDouble).update(houseDouble);
    }

    /**
     * Test when configures the location when the location is null,
     * it should return null.
     */
    @Test
    void testConfigHouseLocationWithNullLocationShouldReturnNull() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.of(houseDouble));
        when(houseDouble.configLocation(null)).thenThrow(IllegalArgumentException.class);

        // Act
        House houseDoubleResult = houseService.configHouseLocation(houseIdDouble, null);

        // Assert
        assertNull(houseDoubleResult,
                "The House object should be null, meaning the location is not configured.");
    }

    /**
     * Test when configures the location when the house does not exist,
     * it should return null.
     */
    @Test
    void testConfigHouseLocationWhenHouseDoesNotExistShouldReturnNull() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.empty());

        // Act
        House houseResult = houseService.configHouseLocation(houseIdDouble, newLocationDouble);

        // Assert
        assertNull(houseResult,
                "The House object should be null, meaning the location is not configured.");
    }

    /**
     * Tests if the getHouseIds returns an empty list when there are no houses in the repository
     */
    @Test
    void testGetHouseIdsEmptyRepository() {
        //Arrange + Act
        when(houseRepositoryDouble.findHouseIds()).thenReturn(new ArrayList<>());
        Iterable<HouseName> result = houseService.getHouseIds();

        //Assert
        assertFalse(result.iterator().hasNext(),
                "The getHouseIds method should return an empty list when there are no houses in the repository");
    }

    /**
     * Tests if the getHouseIds returns a list with one house id when there is one house in the repository
     */
    @Test
    void testGetHouseIdsOneHouseInRepository() {
        //Arrange
        Iterable<HouseName> houseIds = List.of(mock(HouseName.class));
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);

        //Act
        Iterable<HouseName> result = houseService.getHouseIds();

        //Assert
        assertTrue(result.iterator().hasNext(),
                "The getHouseIds method should return a list with one house id " +
                        "when there is one house in the repository");
    }

    /**
     * Tests if the getHouseIds returns a list with two house ids when there is one house in the repository
     */
    @Test
    void testGetHouseIdsTwoHousesInRepository() {
        //Arrange
        Iterable<HouseName> houseIds = List.of(mock(HouseName.class), mock(HouseName.class));
        when(houseRepositoryDouble.findHouseIds()).thenReturn(houseIds);

        //Act
        Iterable<HouseName> result = houseService.getHouseIds();

        //Assert
        assertTrue(result.iterator().hasNext(),
                "The getHouseIds method should return a list with two house ids " +
                        "when there are two houses in the repository");
    }

    /**
     * Test the get house method when the house exists, it should return optional of house.
     */
    @Test
    void testGetHouseWhenHouseExistsShouldReturnOptionalOfHouse() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.of(houseDouble));

        // Act
        Optional<House> result = houseService.getHouse(houseIdDouble);

        // Assert
        assertTrue(result.isPresent() && houseDouble.equals(result.get()),
                "The optional House object should not be empty, meaning the house exists.");
    }

    /**
     * Test the get house method when the house does not exist, it should return optional empty.
     */
    @Test
    void testGetHouseWhenHouseDoesNotExistShouldReturnOptionalEmpty() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenReturn(Optional.empty());

        // Act
        Optional<House> result = houseService.getHouse(houseIdDouble);

        // Assert
        assertTrue(result.isEmpty(),
                "The optional House object should be empty, meaning the house does not exist.");
    }

    /**
     * Test the get house method when an exception is thrown, it should return optional empty.
     */
    @Test
    void testGetHouseWhenAnExceptionIsThrownShouldReturnOptionalEmpty() {
        // Arrange
        when(houseRepositoryDouble.findByIdentity(houseIdDouble)).thenThrow(RuntimeException.class);

        // Act - Assert
        assertThrows(RuntimeException.class, () -> houseService.getHouse(houseIdDouble),
                "Should throw an exception when an error occurs.");
    }

    /**
     * Test the add house method when the house is added successfully, it should return the saved house.
     */
    @Test
    void testAddHouseWithValidParametersShouldReturnSavedHouse() {
        // Arrange
        when(houseFactoryDouble.createHouse(houseIdDouble, locationDouble)).thenReturn(houseDouble);
        when(houseRepositoryDouble.save(houseDouble)).thenReturn(houseDouble);
        when(houseRepositoryDouble.containsIdentity(houseIdDouble)).thenReturn(false);

        // Act
        House houseResult = houseService.addHouse(houseIdDouble, locationDouble);

        // Assert
        assertEquals(houseDouble, houseResult,
                "The House object should be the same as the saved House object.");
    }

    /**
     * Test the add house method when the house is added successfully, it should return the saved house.
     * Verify if the save method of the repository is called.
     */
    @Test
    void testAddHouseVerifyIfCallsSaveMethod() {
        // Arrange
        when(houseFactoryDouble.createHouse(houseIdDouble, locationDouble)).thenReturn(houseDouble);
        when(houseRepositoryDouble.save(houseDouble)).thenReturn(houseDouble);
        when(houseRepositoryDouble.containsIdentity(houseIdDouble)).thenReturn(false);

        // Act
        houseService.addHouse(houseIdDouble, locationDouble);

        // Assert
        verify(houseRepositoryDouble).save(houseDouble);
    }

    /**
     * Test the add house method when the house name already exists, it should return null.
     */
    @Test
    void testAddHouseHouseNameAlreadyExistsShouldReturnNull() {
        // Arrange
        when(houseFactoryDouble.createHouse(houseIdDouble, locationDouble)).thenReturn(houseDouble);
        when(houseRepositoryDouble.containsIdentity(houseIdDouble)).thenReturn(true);

        // Act
        House houseResult = houseService.addHouse(houseIdDouble, locationDouble);

        // Assert
        assertNull(houseResult,
                "The House object should be null, meaning the house name already exists.");
    }

    /**
     * Test the add house method when an exception is thrown, it should return null.
     */
    @Test
    void testAddHouseWhenThrowsExceptionShouldReturnNull() {
        // Arrange
        when(houseFactoryDouble.createHouse(houseIdDouble, locationDouble)).thenReturn(houseDouble);
        when(houseRepositoryDouble.containsIdentity(houseIdDouble)).thenReturn(false);
        when(houseRepositoryDouble.save(houseDouble)).thenThrow(RuntimeException.class);

        // Act
        House houseResult = houseService.addHouse(houseIdDouble, locationDouble);

        // Assert
        assertNull(houseResult,
                "The House object should be null, meaning the house was not added.");
    }
}
