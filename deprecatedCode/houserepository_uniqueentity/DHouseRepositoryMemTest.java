package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the HouseRepositoryMemImpl class.
 * <p>
 * These tests cover various scenarios related to the behavior of the HouseRepositoryMemImpl class,
 * ensuring that it behaves as expected under different conditions.
 */
class HouseRepositoryMemTest {

    HouseRepositoryMem houseRepositoryMem;
    House houseMock;
    HouseName houseNameMock;
    Location locationMock;

    /**
     * Sets up the test environment before each test method execution.
     * <p>
     * This method initializes the HouseRepositoryMemImpl instance and creates necessary mock objects
     * for testing, including a mocked House, a mocked HouseName, and a mocked Location.
     * Mock objects are configured to provide simulated behavior during test execution.
     * <p>
     * For each test, the following setup steps are performed:
     * houseRepositoryMem: Creates a new instance of HouseRepositoryMemImpl to be tested.
     * houseMock: Creates a mocked House object using Mockito to simulate a house entity.
     * houseNameMock: Creates a mocked HouseName object using Mockito to simulate a house name value object.
     * locationMock: Creates a mocked Location object using Mockito to simulate a location value object.
     * Then configures behavior for the mocked House object to return the mocked HouseName
     * and mocked Location objects when their respective getter methods are called.
     * </p<
     */
    @BeforeEach
    void setUp() {
        houseRepositoryMem = new HouseRepositoryMem();
        houseMock = mock(House.class);
        houseNameMock = mock(HouseName.class);
        locationMock = mock(Location.class);
        when(houseMock.getIdentity()).thenReturn(houseNameMock);
        when(houseMock.getLocation()).thenReturn(locationMock);
    }

    /**
     * Tests the behavior of the getEntity() method when no house is saved.
     * <p>
     * It verifies that calling getEntity() when no house is saved returns an empty Optional.
     * </p>
     */
    @Test
    void testGetHouseNoHouseSaved() {
        // Act
        assertFalse(houseRepositoryMem.getEntity().isPresent());
    }

    /**
     * Tests the behavior of the getHouse() method when one house is saved.
     * <p>
     * It verifies that calling getHouse() after saving a house returns the saved house.
     * </p>
     */
    @Test
    void testGetHouseOneHouseSaved() {
        // Arrange
        houseRepositoryMem.save(houseMock);
        // Act
        House retrievedHouse = houseRepositoryMem.getEntity().get();
        // Assert
        assertEquals(houseMock, retrievedHouse);
    }

    /**
     * Tests the behavior of the saveHouse(House) method when saving a house to an empty repository.
     * <p>
     * It verifies that saving a house using saveHouse(House) to an empty repository
     * returns the saved house, and subsequently, calling getHouse() returns the saved house.
     * </p>
     */
    @Test
    void testSaveHouseToEmptyRepositoryShouldReturnSavedHouse() {
        // Act
        House savedHouse = houseRepositoryMem.save(houseMock);
        // Assert
        assertEquals(houseMock, savedHouse);
        assertEquals(houseMock, houseRepositoryMem.getEntity().get());
    }

    /**
     * Tests the behavior of the saveHouse(House) method when saving a house
     * with a different location but the same name.
     * <p>
     * It verifies that saving a house using saveHouse(House) with a different location but the same name as the previously saved house updates the location of the existing house to the new location.
     * </p>
     */
    @Test
    void testSaveHouseWithDifferentLocationButSameNameShouldReturnSavedHouse() {
        // Arrange
        House houseMock1 = mock(House.class);
        Location oldLocationMock = mock(Location.class);
        when(houseMock1.getIdentity()).thenReturn(houseNameMock);
        when(houseMock1.getLocation()).thenReturn(oldLocationMock);

        houseRepositoryMem.save(houseMock1);

        House houseMock2 = mock(House.class);
        Location newLocationMock = mock(Location.class);
        when(houseMock2.getIdentity()).thenReturn(houseNameMock);
        when(houseMock2.getLocation()).thenReturn(newLocationMock);
        // Act
        House savedHouse = houseRepositoryMem.save(houseMock2);
        // Assert
        assertEquals(houseMock2, savedHouse);
        assertEquals(newLocationMock, savedHouse.getLocation());
    }

    /**
     * Tests the behavior of the saveHouse(House) method when attempting to save a house
     * with a different identity than the previously saved house.
     * <p>
     * It verifies that attempting to save a house using saveHouse(House) with a different name
     * throws an IllegalArgumentException.
     * <p>
     * @throws IllegalArgumentException If the provided House instance is null or if an attempt is made to save a different House instance.
     * </p>
     */
    @Test
    void testSaveHouseDifferentHouseShouldThrowException() {
        // Arrange
        House houseMock1 = mock(House.class);
        HouseName mockHouseName1 = mock(HouseName.class);
        when(houseMock1.getIdentity()).thenReturn(mockHouseName1);
        when(houseMock1.getLocation()).thenReturn(locationMock);

        houseRepositoryMem.save(houseMock1);

        House houseMock2 = mock(House.class);
        HouseName mockHouseName2 = mock(HouseName.class);
        when(houseMock2.getIdentity()).thenReturn(mockHouseName2);
        when(houseMock2.getLocation()).thenReturn(locationMock);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMem.save(houseMock2));
        assertEquals(houseMock1, houseRepositoryMem.getEntity().get());
    }

    /**
     * Tests the behavior of the save(House) method when attempting to save a null house.
     * <p>
     * It verifies that calling save(House) with a null house object throws an IllegalArgumentException.
     * </p>
     */
    @Test
    void testSaveHouse_NullHouse() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMem.save(null));
        assertFalse(houseRepositoryMem.getEntity().isPresent());
    }

    /**
     * Tests the behavior of the update(House) method when updating a house to an empty repository.
     * Should throw an IllegalArgumentException.
     */
    @Test
    void testUpdateHouseToEmptyRepositoryShouldReturnUpdatedHouse() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMem.update(houseMock));
    }

    /**
     * Tests the behavior of the update(House) method when updating a house with a different location but the same name.
     * Should return the updated house with the new location.
     */
    @Test
    void testUpdateHouseWithDifferentLocationButSameNameShouldReturnUpdatedHouse() {
        // Arrange
        House houseMock1 = mock(House.class);
        Location oldLocationMock = mock(Location.class);
        when(houseMock1.getIdentity()).thenReturn(houseNameMock);
        when(houseMock1.getLocation()).thenReturn(oldLocationMock);

        houseRepositoryMem.save(houseMock1);

        House houseMock2 = mock(House.class);
        Location newLocationMock = mock(Location.class);
        when(houseMock2.getIdentity()).thenReturn(houseNameMock);
        when(houseMock2.getLocation()).thenReturn(newLocationMock);
        // Act
        House updatedHouse = houseRepositoryMem.update(houseMock2);
        // Assert
        assertEquals(houseMock2, updatedHouse);
        assertEquals(newLocationMock, updatedHouse.getLocation());
    }

    /**
     * Tests the behavior of the update(House) method when updating a house with a different identity than the previously saved house.
     * Should throw an IllegalArgumentException and not update the house.
     */
    @Test
    void testUpdateHouseWhenDifferentHouseNameShouldThrowException() {
        // Arrange
        House houseMock1 = mock(House.class);
        HouseName mockHouseName1 = mock(HouseName.class);
        when(houseMock1.getIdentity()).thenReturn(mockHouseName1);
        when(houseMock1.getLocation()).thenReturn(locationMock);

        houseRepositoryMem.save(houseMock1);

        House houseMock2 = mock(House.class);
        HouseName mockHouseName2 = mock(HouseName.class);
        when(houseMock2.getIdentity()).thenReturn(mockHouseName2);
        when(houseMock2.getLocation()).thenReturn(locationMock);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMem.update(houseMock2));
        assertEquals(houseMock1, houseRepositoryMem.getEntity().get());
    }

    /**
     * Tests the behavior of the update(House) method when updating a null house.
     * Should throw an IllegalArgumentException.
     */
    @Test
    void testUpdateHouseWhenNullHouseShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMem.update(null));
    }
}