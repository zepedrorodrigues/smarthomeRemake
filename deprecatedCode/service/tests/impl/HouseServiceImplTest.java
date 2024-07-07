package smarthome.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.Location;
import smarthome.domain.repository.IHouseRepository;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.mapper.HouseMapper;
import smarthome.service.IHouseService;
import smarthome.utils.AvailableCountries;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class for unit testing the HouseServiceImpl class.
 */
class HouseServiceImplTest {


    /**
     * Test the constructor of the HouseServiceImpl class when the HouseMapper is null.
     */
    @Test
    void testConstructorNullHouseMapper() {
        // Arrange
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class, () -> new HouseServiceImpl(null, houseRepositoryDouble),
                "HouseMapper cannot be null.");
    }

    /**
     * Test the constructor of the HouseServiceImpl class when the HouseRepository is null.
     */
    @Test
    void testConstructorWhenNullHouseRepository() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class, () -> new HouseServiceImpl(houseMapperDouble, null),
                "HouseRepository cannot be null.");
    }

    /**
     * Test the constructor of the HouseServiceImpl class when the HouseMapper and HouseRepository are null.
     */
    @Test
    void testConstructorWhenNullHouseMapperNullHouseRepository() {
        // Arrange & Act & Assert
        assertThrows(
                IllegalArgumentException.class, () -> new HouseServiceImpl(null, null),
                "HouseMapper and HouseRepository cannot be null.");
    }

    /**
     * Test the constructor of the HouseServiceImpl class when the HouseMapper and HouseRepository are not null.
     */
    @Test
    void testConstructorWhenNotNullHouseMapperNotNullHouseRepository() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository

        // Act
        HouseServiceImpl houseServiceImpl = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);
        // Assert
        assertNotNull(houseServiceImpl, "HouseServiceImpl should not be null.");
    }

    /**
     * Test the constructor of the HouseServiceImpl class when the HouseMapper and HouseRepository are not null.
     */
    @Test
    void testConstructorWhenNotNullHouseMapperNotNullHouseRepositoryNoThrowException() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository

        // Act & Assert
        assertDoesNotThrow(() -> new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble),
                "Should not throw an exception.");
    }

    /**
     * Test when getAvailableCountries method is called, it should not return null.
     */
    @Test
    void testGetAvailableCountriesWhenCalledShouldNotReturnNull() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository

        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenReturn(Arrays.asList("USA", "Portugal", "Spain"));

            IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

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
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository

        try (MockedStatic<AvailableCountries> mockedStatic = Mockito.mockStatic(AvailableCountries.class)) {
            mockedStatic.when(AvailableCountries::getAvailableCountries)
                    .thenReturn(Arrays.asList("USA", "Portugal", "Spain"));

            IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

            // Act
            List<String> countries = houseService.getAvailableCountries();

            // Assert
            assertEquals(Arrays.asList("USA", "Portugal", "Spain"), countries,
                    "List of countries should be the same as the expected list of countries.");
        }
    }


    /**
     * Test when configures the location with valid parameters,
     * it should return a valid HouseDTO object, not null.
     */
    @Test
    void testConfigHouseLocationWithValidParametersShouldReturnNotNull() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository
        Location newLocationDouble = mock(Location.class); // Mock of Location
        Location locationUpdatedDouble = mock(Location.class); // Mock of Location
        House houseDouble = mock(House.class); // Mock of House
        House houseUpdatedDouble = mock(House.class); // Mock of House representing the updated house
        HouseDTO houseDTODouble = mock(HouseDTO.class); // Mock of HouseDTO
        HouseDTO houseDTOExpected = mock(HouseDTO.class); // Mock of HouseDTO representing the expected result

        when(houseRepositoryDouble.getEntity()).thenReturn(Optional.of(houseDouble));
        when(houseMapperDouble.toLocation(houseDTODouble)).thenReturn(newLocationDouble);
        when(houseDouble.configLocation(newLocationDouble)).thenReturn(locationUpdatedDouble);
        when(houseRepositoryDouble.update(houseDouble)).thenReturn(houseUpdatedDouble);
        when(houseMapperDouble.toHouseDTO(houseUpdatedDouble)).thenReturn(houseDTOExpected);

        IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

        // Act
        HouseDTO result = houseService.configHouseLocation(houseDTODouble);

        // Assert
        assertNotNull(result, "The HouseDTO object should not be null, meaning the location is configured.");
    }

    /**
     * Test when configures the location with valid parameters,
     * it should return a valid HouseDTO object with the configured location.
     */
    @Test
    void testConfigHouseLocationWithValidParametersShouldReturnHouseDTOWithConfiguredLocation() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository
        Location newLocationDouble = mock(Location.class); // Mock of Location
        Location locationUpdatedDouble = mock(Location.class); // Mock of Location
        House houseDouble = mock(House.class); // Mock of House
        House houseUpdatedDouble = mock(House.class); // Mock of House representing the updated house
        HouseDTO houseDTODouble = mock(HouseDTO.class); // Mock of HouseDTO
        HouseDTO houseDTOExpected = mock(HouseDTO.class); // Mock of HouseDTO representing the expected result

        when(houseRepositoryDouble.getEntity()).thenReturn(Optional.of(houseDouble));
        when(houseMapperDouble.toLocation(houseDTODouble)).thenReturn(newLocationDouble);
        when(houseDouble.configLocation(newLocationDouble)).thenReturn(locationUpdatedDouble);
        when(houseRepositoryDouble.update(houseDouble)).thenReturn(houseUpdatedDouble);
        when(houseMapperDouble.toHouseDTO(houseUpdatedDouble)).thenReturn(houseDTOExpected);

        IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

        // Act
        HouseDTO result = houseService.configHouseLocation(houseDTODouble);

        // Assert
        assertEquals(houseDTOExpected, result,
                "The HouseDTO returned should be the same as the expected DTO, meaning the location is configured.");
    }


    /**
     * Test when configures the location with valid parameters,
     * it should return a valid HouseDTO object with the configured location.
     */
    @Test
    void testConfigHouseLocationWithInvalidLocationParameterShouldReturnNull() {
        // Arrange
        House houseDouble = mock(House.class); // Mock of House
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository
        HouseDTO invalidParametersHouseDTODouble = mock(HouseDTO.class); // Mock of HouseDTO with invalid parameters

        when(houseRepositoryDouble.getEntity()).thenReturn(Optional.ofNullable(houseDouble));
        when(houseMapperDouble.toLocation(invalidParametersHouseDTODouble)).thenThrow(IllegalArgumentException.class);

        IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

        // Act
        HouseDTO result = houseService.configHouseLocation(invalidParametersHouseDTODouble);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null, meaning the location is not configured.");
    }

    /**
     * Test when configures the location with valid parameters,
     * it should return a valid HouseDTO object with the configured location.
     */
    @Test
    void testConfigHouseLocationWithNullHouseDTOShouldReturnNull() {
        // Arrange
        House houseDouble = mock(House.class); // Mock of House
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository
        HouseDTO invalidParametersHouseDTODouble = null; // Mock of HouseDTO with invalid parameters

        when(houseRepositoryDouble.getEntity()).thenReturn(Optional.ofNullable(houseDouble));

        IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

        // Act
        HouseDTO result = houseService.configHouseLocation(invalidParametersHouseDTODouble);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null, meaning the location is not configured.");
    }

    /**
     * Test when configures the location with valid parameters,
     * it should return a valid HouseDTO object with the configured location.
     */
    @Test
    void testConfigHouseLocationHouseDoesNotExistShouldReturnNull() {
        // Arrange
        HouseMapper houseMapperDouble = mock(HouseMapper.class); // Mock of HouseMapper
        IHouseRepository houseRepositoryDouble = mock(IHouseRepository.class); // Mock of HouseRepository
        HouseDTO houseDTODouble = mock(HouseDTO.class); // Mock of HouseDTO with valid parameters

        when(houseRepositoryDouble.getEntity()).thenReturn(Optional.empty());

        IHouseService houseService = new HouseServiceImpl(houseMapperDouble, houseRepositoryDouble);

        // Act
        HouseDTO result = houseService.configHouseLocation(houseDTODouble);

        // Assert
        assertNull(result,
                "The HouseDTO object should be null, meaning the location is not configured.");
    }


}