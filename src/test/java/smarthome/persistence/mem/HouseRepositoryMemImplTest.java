package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.HouseFactoryImpl;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for HouseRepositoryMemImpl class.
 */
class HouseRepositoryMemImplTest {
    HouseFactory houseFactory;
    HouseName houseName;
    String houseNameString;
    Location location;
    House house;

    /**
     * Sets up the test environment by initializing and mocking necessary objects.
     */
    @BeforeEach
    void setUp() {
        houseFactory = mock(HouseFactoryImpl.class);
        // houseName
        houseName = mock(HouseName.class);
        houseNameString = "HouseName";
        when(houseName.getName()).thenReturn(houseNameString);
        // location
        location = mock(Location.class);
        // house
        house = mock(House.class);
        when(house.getLocation()).thenReturn(location);
        when(house.getIdentity()).thenReturn(houseName);
    }

    /**
     * Tests the update method with a valid house. The updated house should be returned.
     */
    @Test
    void testUpdateMethodValidHouseShouldReturnHouse() {
        // Arrange
        Location location1 = mock(Location.class);
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        houseRepositoryMemImpl.save(house);
        when(houseFactory.createHouse(any(HouseName.class), any(Location.class))).thenReturn(house);
        House upDatedHouse = houseFactory.createHouse(houseName, location1);
        // Act
        House house1 = houseRepositoryMemImpl.update(upDatedHouse);
        // Assert
        assertEquals(house, house1);
    }

    /**
     * Tests the update method with a null house. An IllegalArgumentException should be thrown.
     */
    @Test
    void testUpdateMethodNullHouseShouldThrowIllegalArgumentException() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMemImpl.update(null));
    }

    /**
     * Tests the update method with a house not in the repository. An IllegalArgumentException should be thrown.
     */
    @Test
    void testUpdateMethodHouseNotInRepositoryShouldThrowIllegalArgumentException() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        when(houseFactory.createHouse(any(HouseName.class), any(Location.class))).thenReturn(house);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMemImpl.update(house));
    }

    /**
     * Tests the save method with a valid house. The saved house should be returned.
     */
    @Test
    void testSaveMethodValidHouseShouldReturnHouse() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act
        House house1 = houseRepositoryMemImpl.save(house);
        // Assert
        assertEquals(house, house1);
    }

    /**
     * Tests the save method with a null house. An IllegalArgumentException should be thrown.
     */
    @Test
    void testSaveMethodNullHouseShouldThrowIllegalArgumentException() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMemImpl.save(null));
    }

    /**
     * Tests the save method with a house that already exists in the repository.
     * An IllegalArgumentException should be thrown.
     */
    @Test
    void testSaveMethodHouseWithSameIdentityInRepositoryShouldThrowIllegalArgumentException() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        when(houseFactory.createHouse(any(HouseName.class), any(Location.class))).thenReturn(house);
        houseRepositoryMemImpl.save(house);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMemImpl.save(house));
    }

    /**
     * Tests the findAll method. An iterable of houses should be returned.
     */
    @Test
    void testFindAllMethodShouldReturnIterableOfHouses() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        when(houseFactory.createHouse(any(HouseName.class), any(Location.class))).thenReturn(house);
        houseRepositoryMemImpl.save(house);
        // Act
        Iterable<House> houses = houseRepositoryMemImpl.findAll();
        House house1 = houses.iterator().next();
        // Assert
        assertEquals(house, house1);
    }

    /**
     * Tests the findAll method when no houses are in the repository. An empty iterable should be returned.
     */
    @Test
    void testFindAllMethodShouldReturnEmptyIterableIfNoHousesInRepository() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act
        Iterable<House> houses = houseRepositoryMemImpl.findAll();
        // Assert
        assertFalse(houses.iterator().hasNext());
    }

    /**
     * Tests the getByIdentity method when a house exists in the repository. An optional of house should be returned.
     */
    @Test
    void testFindByIdentityMethodHouseInRepositoryShouldReturnOptionalOfHouse() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        when(houseFactory.createHouse(any(HouseName.class), any(Location.class))).thenReturn(house);
        houseRepositoryMemImpl.save(house);
        // Act
        Optional<House> houseOptional = houseRepositoryMemImpl.findByIdentity(houseName);
        House house1 = Objects.requireNonNull(houseOptional.orElse(null));
        // Assert
        assertEquals(house, house1);
        assertNotNull(house1);
    }

    /**
     * Tests the getByIdentity method when no house exists in the repository. An empty optional should be returned.
     */
    @Test
    void testFindByIdentityMethodHouseNotInRepositoryShouldReturnEmptyOptional() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act
        Optional<House> houseOptional = houseRepositoryMemImpl.findByIdentity(houseName);
        // Assert
        assertTrue(houseOptional.isEmpty());
    }

    /**
     * Tests the getByIdentity method with a null house name. An IllegalArgumentException should be thrown.
     */
    @Test
    void testFindByIdentityMethodNullHouseNameShouldThrowIllegalArgumentException() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMemImpl.findByIdentity(null));
    }

    /**
     * Tests the containsIdentity method when a house exists in the repository. Should return true.
     */
    @Test
    void testContainsIdentityMethodHouseInRepositoryShouldReturnTrue() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        when(houseFactory.createHouse(any(HouseName.class), any(Location.class))).thenReturn(house);
        houseRepositoryMemImpl.save(house);
        // Act
        boolean containsIdentity = houseRepositoryMemImpl.containsIdentity(houseName);
        // Assert
        assertTrue(containsIdentity);
    }

    /**
     * Tests the containsIdentity method when no house exists in the repository. Should return false.
     */
    @Test
    void testContainsIdentityMethodHouseNotInRepositoryShouldReturnFalse() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act
        boolean containsIdentity = houseRepositoryMemImpl.containsIdentity(houseName);
        // Assert
        assertFalse(containsIdentity);
    }

    /**
     * Tests the containsIdentity method with a null house name. An IllegalArgumentException should be thrown.
     */
    @Test
    void testContainsIdentityMethodNullHouseNameShouldThrowIllegalArgumentException() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseRepositoryMemImpl.containsIdentity(null));
    }


    /**
     * This test checks that the list of house ids contains the saved house ids when the repository is
     * with 1 house id.
     */
    @Test
    void testFindHouseIdsToARepositoryWith1HouseId() {
        //Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();

        House house = mock(House.class);
        HouseName houseName = mock(HouseName.class);
        when(house.getIdentity()).thenReturn(houseName);
        houseRepositoryMemImpl.save(house);

        //Act
        List<HouseName> result = new ArrayList<>();
        houseRepositoryMemImpl.findHouseIds().forEach(result::add);

        //Assert
        assertTrue(result.contains(house.getIdentity()),
                "The list of house ids should contain the saved house id.");
    }

    /**
     * This test checks that the list of house ids contains the saved house ids when the repository is
     * with 2 house id.
     */
    @Test
    void testFindHouseIdsToARepositoryWith2HouseIds() {
        //Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();

        House house = mock(House.class);
        HouseName houseName = mock(HouseName.class);
        when(house.getIdentity()).thenReturn(houseName);
        House house2 = mock(House.class);
        HouseName houseName2 = mock(HouseName.class);
        when(house2.getIdentity()).thenReturn(houseName2);
        houseRepositoryMemImpl.save(house);
        houseRepositoryMemImpl.save(house2);

        //Act
        List<HouseName> result = new ArrayList<>();
        houseRepositoryMemImpl.findHouseIds().forEach(result::add);

        //Assert
        assertTrue(result.contains(house.getIdentity()) && result.contains(house2.getIdentity()),
                "The list of house ids should contain the saved house ids.");
    }

    /**
     * This test checks that the list of house ids is empty when the repository is empty.
     */
    @Test
    void testFindHouseIdsToAnEmptyRepository() {
        // Arrange
        HouseRepositoryMemImpl houseRepositoryMemImpl = new HouseRepositoryMemImpl();
        List<HouseName> result = new ArrayList<>();

        //Act
        houseRepositoryMemImpl.findHouseIds().forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(),
                "The list of house ids should be empty.");
    }

}
