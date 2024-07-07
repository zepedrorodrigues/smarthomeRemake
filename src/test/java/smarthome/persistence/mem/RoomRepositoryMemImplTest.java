package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.RoomId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is used to test the RoomRepositoryMemImpl class.
 */
class RoomRepositoryMemImplTest {

    private Room room1;
    private Room room2;
    private RoomRepositoryMemImpl roomRepositoryMemImpl;
    private HouseName houseName;

    /**
     * This method is executed before each test. It initializes the RoomRepositoryMemImpl instance.
     */
    @BeforeEach
    void setUp() {
        room1 = mock(Room.class);
        when(room1.getIdentity()).thenReturn(mock(RoomId.class));
        room2 = mock(Room.class);
        when(room2.getIdentity()).thenReturn(mock(RoomId.class));
        roomRepositoryMemImpl = new RoomRepositoryMemImpl();
        houseName = mock(HouseName.class);
        when(houseName.getName()).thenReturn("houseName");
        when(room1.getHouseName()).thenReturn(houseName);
        when(room2.getHouseName()).thenReturn(houseName);


    }

    /**
     * This test checks if a room can be saved to a non-empty repository.
     */
    @Test
    void testSaveToEmptyRepository() {
        //Act
        Room result = roomRepositoryMemImpl.save(room1);

        //Assert
        assertEquals(room1, result,
                "The saved room should be the same as the room that was saved.");
    }

    /**
     * This test checks if a room can be saved to a non-empty repository.
     */
    @Test
    void testSaveToANonEmptyRepository() {
        //Arrange
        roomRepositoryMemImpl.save(room1);

        //Act
        Room result = roomRepositoryMemImpl.save(room2);

        //Assert
        assertEquals(room2, result,
                "The saved room should be the same as the room that was saved.");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when trying to save a null room.
     */
    @Test
    void testSaveThrowsIllegalArgumentExceptionWhenRoomIsNull() {
        // Arrange
        Room invalidRoom = null;

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roomRepositoryMemImpl.save(invalidRoom),
                "An IllegalArgumentException should be thrown when trying to save a null room.");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when trying to save a room with
     * an existing identity.
     */
    @Test
    void testSaveThrowsIllegalArgumentExceptionWhenRoomIdExists() {
        //Arrange
        RoomId roomId = mock(RoomId.class);
        Room room = mock(Room.class);
        when(room.getIdentity()).thenReturn(roomId);
        roomRepositoryMemImpl.save(room);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roomRepositoryMemImpl.save(room),
                "An IllegalArgumentException should be thrown when trying to save a room with an existing identity.");
    }

    /**
     * This test checks if all rooms can be retrieved from an empty repository.
     */
    @Test
    void testFindAllToAnEmptyRepository() {
        //Act
        List<Room> result = new ArrayList<>();
        roomRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(),
                "The list of rooms should be empty.");
    }

    /**
     * This test checks if all rooms can be retrieved from a non-empty repository.
     */
    @Test
    void testFindAllToANonEmptyRepository() {
        //Arrange
        roomRepositoryMemImpl.save(room1);
        roomRepositoryMemImpl.save(room2);

        //Act
        List<Room> result = new ArrayList<>();
        roomRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.contains(room1) && result.contains(room2),
                "The list of rooms should contain the saved rooms.");
    }

    /**
     * This test checks that the list of room ids is empty when the repository is empty.
     */
    @Test
    void testFindRoomIdsToAnEmptyRepository() {
        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIds().forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(),
                "The list of room ids should be empty.");
    }

    /**
     * This test checks that the list of room ids contains two room ids when the repository contains two rooms.
     */
    @Test
    void testFindRoomIdsToARepositoryWith2Rooms() {
        //Arrange
        int expectedSize = 2;
        roomRepositoryMemImpl.save(room1);
        roomRepositoryMemImpl.save(room2);

        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIds().forEach(result::add);

        //Assert
        assertEquals(expectedSize, result.size(),
                "The list of room ids should contain two room ids.");
    }

    /**
     * This test checks that the list of room ids contains the saved room ids when the repository is not empty.
     */
    @Test
    void testFindRoomIdsToANonEmptyRepository() {
        //Arrange
        roomRepositoryMemImpl.save(room1);
        roomRepositoryMemImpl.save(room2);

        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIds().forEach(result::add);

        //Assert
        assertTrue(result.contains(room1.getIdentity()) && result.contains(room2.getIdentity()),
                "The list of room ids should contain the saved room ids.");
    }

    /**
     * This test checks if a room can be retrieved by its identity from an empty repository.
     */
    @Test
    void testFindByIdentityToAnEmptyRepository() {
        //Arrange
        RoomId roomId = mock(RoomId.class);

        //Act
        Optional<Room> result = roomRepositoryMemImpl.findByIdentity(roomId);

        //Assert
        assertTrue(result.isEmpty(),
                "The optional should be empty.");
    }

    /**
     * This test checks if a room can be retrieved by its identity from a non-empty repository.
     */
    @Test
    void testFindByIdentityToANonEmptyRepository() {
        //Arrange
        RoomId roomId = mock(RoomId.class);
        Room room = mock(Room.class);
        when(room.getIdentity()).thenReturn(roomId);
        roomRepositoryMemImpl.save(room);

        //Act
        Room result = roomRepositoryMemImpl.findByIdentity(roomId).get();

        //Assert
        assertEquals(room, result,
                "The retrieved room should be the same as the saved room.");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when trying to retrieve a
     * room with a null identity.
     */
    @Test
    void testFindByIdentityThrowsIllegalArgumentExceptionWhenRoomIdIsNull() {
        // Arrange
        RoomId invalidRoomId = null;

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roomRepositoryMemImpl.findByIdentity(invalidRoomId),
                "An IllegalArgumentException should be thrown when trying to retrieve a room with a null identity.");
    }

    /**
     * This test checks if the repository contains a Room with a certain identity.
     */
    @Test
    void testContainsIdentityReturnsTrueWhenRoomIdExists() {
        //Arrange
        RoomId roomId = mock(RoomId.class);
        Room room = mock(Room.class);
        when(room.getIdentity()).thenReturn(roomId);
        roomRepositoryMemImpl.save(room);

        //Act
        boolean result = roomRepositoryMemImpl.containsIdentity(roomId);

        //Assert
        assertTrue(result,
                "The repository should contain a Room with the given identity.");
    }

    /**
     * This test checks if the repository does not contain a Room with a certain identity.
     */
    @Test
    void testContainsIdentityReturnsFalseWhenRoomIdDoesNotExist() {
        //Arrange
        RoomId invalidRoomId = mock(RoomId.class);
        roomRepositoryMemImpl.save(room1);

        //Act
        boolean result = roomRepositoryMemImpl.containsIdentity(invalidRoomId);

        //Assert
        assertFalse(result,
                "The repository should not contain a Room with the given identity.");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when trying to check if the repository
     * contains a Room with a null identity.
     */
    @Test
    void testContainsIdentityThrowsIllegalArgumentExceptionWhenRoomIdIsNull() {
        // Arrange
        RoomId invalidRoomId = null;

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roomRepositoryMemImpl.containsIdentity(invalidRoomId),
                "An IllegalArgumentException should be thrown when trying to check if the repository contains a " +
                        "Room with a null identity.");
    }

    /**
     * This test checks that the list of room ids is empty when the repository is empty.
     */
    @Test
    void testFindRoomIdsByHouseNameToAnEmptyRepository() {
        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIdsByHouseName(houseName).forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(),
                "The list of room ids should be empty.");
    }

    /**
     * This test checks that the list of room ids contains two room ids when the repository contains two rooms.
     */
    @Test
    void testFindRoomIdsByHouseNameToARepositoryWith2Rooms() {
        //Arrange
        int expectedSize = 2;
        roomRepositoryMemImpl.save(room1);
        roomRepositoryMemImpl.save(room2);

        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIdsByHouseName(houseName).forEach(result::add);

        //Assert
        assertEquals(expectedSize, result.size(),
                "The list of room ids should contain two room ids.");
    }

    /**
     * This test checks that the list of room ids is empty when the repository for the house name is empty,
     */
    @Test
    void testFindRoomIdsByHouseNameToARepositoryWith2RoomsAndTheHouseNameDoesNotExist() {
        //Arrange
        HouseName houseName1 = mock(HouseName.class);
        when(houseName1.getName()).thenReturn("differentHouseName");
        int expectedSize = 0;
        roomRepositoryMemImpl.save(room1);
        roomRepositoryMemImpl.save(room2);

        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIdsByHouseName(houseName1).forEach(result::add);

        //Assert
        assertEquals(expectedSize, result.size(),
                "The list of room ids should be empty.");
    }

    /**
     * This test checks that the list of room ids contains the saved room ids when the repository is not empty.
     */
    @Test
    void testFindRoomIdsByHouseNameToANonEmptyRepository() {
        //Arrange
        roomRepositoryMemImpl.save(room1);
        roomRepositoryMemImpl.save(room2);

        //Act
        List<RoomId> result = new ArrayList<>();
        roomRepositoryMemImpl.findRoomIdsByHouseName(houseName).forEach(result::add);

        //Assert
        assertTrue(result.contains(room1.getIdentity()) && result.contains(room2.getIdentity()),
                "The list of room ids should contain the saved room ids.");
    }


}

