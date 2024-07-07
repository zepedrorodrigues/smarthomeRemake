package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.service.IRoomService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for RoomServiceImpl.
 */
class RoomServiceImplTest {

    private IHouseRepository mockHouseRepository;
    private IRoomRepository mockRoomRepository;
    private RoomFactory mockRoomFactory;

    private RoomName mockRoomName;
    private Floor mockFloor;
    private Dimensions mockDimensions;

    private HouseName mockHouseId;

    private Room mockRoom;
    private RoomId mockRoomId;

    private IRoomService roomService;

    private HouseName houseName;

    /**
     * Setup before each test.
     */
    @BeforeEach
    void setUp() {
        mockHouseRepository = mock(IHouseRepository.class);
        mockRoomRepository = mock(IRoomRepository.class);
        mockRoomFactory = mock(RoomFactory.class);
        roomService = new RoomServiceImpl(mockHouseRepository, mockRoomRepository, mockRoomFactory);

        mockRoom = mock(Room.class);
        when(mockRoom.getIdentity()).thenReturn(mockRoomId);
        mockRoomId = mock(RoomId.class);
        mockRoomName = mock(RoomName.class);
        mockHouseId = mock(HouseName.class);
        mockFloor = mock(Floor.class);
        mockDimensions = mock(Dimensions.class);

        when(mockRoomFactory.createRoom(mockRoomName, mockHouseId, mockFloor, mockDimensions)).thenReturn(mockRoom);

        when(mockRoomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.<Room>getArgument(0));

        houseName = mock(HouseName.class);
        when(houseName.getName()).thenReturn("houseName");

        when(mockRoom.getHouseName()).thenReturn(houseName);

    }

    /**
     * Test that a RoomServiceImpl can be correctly constructed.
     */
    @Test
    void testRoomRESTServiceImplCanBeConstructed() {
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl(mockHouseRepository, mockRoomRepository,
                mockRoomFactory);
        assertNotNull(roomServiceImpl,
                "RoomServiceImpl should be able to be constructed");
    }

    /**
     * Test if the addRoom method returns null when the house name is not present in the repository
     */
    @Test
    void testAddRoomReturnNullWhenHouseNameIsNotPresentInTheRepository() {
        //Arrange
        when(mockHouseRepository.containsIdentity(mockHouseId)).thenReturn(false);

        //Act
        Room result = roomService.addRoom(mockHouseId, mockRoomName, mockFloor, mockDimensions);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the house name is not present in the repository");
    }

    /**
     * Test if the addRoom method returns null when the factory throws an exception
     */
    @Test
    void testAddRoomReturnNullWhenInvalidParametersArePassedToTheFactory() {
        //Arrange
        RoomName nullRoomName = null;
        when(mockHouseRepository.containsIdentity(mockHouseId)).thenReturn(true);
        when(mockRoomFactory.createRoom(nullRoomName, mockHouseId, mockFloor, mockDimensions))
                .thenThrow(IllegalArgumentException.class);

        //Act
        Room result = roomService.addRoom(mockHouseId, nullRoomName, mockFloor, mockDimensions);

        //Assert
        assertNull(result,
                "The addRoom method should return null when one or more parameters are invalid");
    }

    /**
     * Test if the addRoom method correctly creates and saves a room to the repository
     */
    @Test
    void testAddRoomCorrectlyCreatesAndSavesARoomToTheRepository() {
        //Arrange
        when(mockHouseRepository.containsIdentity(mockHouseId)).thenReturn(true);

        //Act
        Room result = roomService.addRoom(mockHouseId, mockRoomName, mockFloor, mockDimensions);

        //Assert
        assertEquals(mockRoom.getIdentity(), result.getIdentity(),
                "The addRoom method should correctly create and save a room to the repository");
    }

    /**
     * Tests if the getRooms returns an empty list when there are no rooms in the repository
     */
    @Test
    void testGetRoomsEmptyRepository() {
        //Arrange
        int expectedSize = 0;
        //Act
        List<Room> result = roomService.getRooms();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRooms method should return an empty list when there are no rooms in the repository");
    }

    /**
     * Tests if the getRooms returns a list with one room when there is one room in the repository
     */
    @Test
    void testGetRoomsOneRoomInRepository() {
        //Arrange
        int expectedSize = 1;
        Iterable<Room> rooms = List.of(mockRoom);

        when(mockRoomRepository.findAll()).thenReturn(rooms);

        //Act
        List<Room> result = roomService.getRooms();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRooms method should return a list with one room when there is one room in the repository");
    }

    /**
     * Tests if the getRooms returns a list with all rooms in the repository
     */
    @Test
    void testGetRoomsSeveralRoomsInRepository() {
        //Arrange
        int expectedSize = 3;
        Iterable<Room> rooms = List.of(mockRoom, mockRoom, mockRoom);

        when(mockRoomRepository.findAll()).thenReturn(rooms);

        //Act
        List<Room> result = roomService.getRooms();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRooms method should return a list with three rooms when " +
                        "there are three rooms in the repository");
    }

    /**
     * Tests if the getRoomById returns the room with the given id
     */
    @Test
    void testGetRoomById() {
        //Arrange
        Optional<Room> expected = Optional.of(mockRoom);
        when(mockRoomRepository.findByIdentity(mockRoomId)).thenReturn(Optional.of(mockRoom));
        //Act
        Optional<Room> result = roomService.getRoomById(mockRoomId);
        //Assert
        assertEquals(expected, result,
                "The getRoomById method should return the room with the given id");
    }

    /**
     * Tests if the getRoomById returns an empty optional when the room with the given id does not exist
     */
    @Test
    void testGetRoomByIdEmpty() {
        //Arrange
        Optional<Room> expected = Optional.empty();
        when(mockRoomRepository.findByIdentity(mockRoomId)).thenReturn(Optional.empty());
        //Act
        Optional<Room> result = roomService.getRoomById(mockRoomId);
        //Assert
        assertEquals(expected, result,
                "The getRoomById method should return an empty optional " +
                        "when the room with the given id does not exist");
    }

    /**
     * Tests if the getRoomIds returns an empty list when there are no rooms in the repository
     */
    @Test
    void testGetRoomIdsEmptyRepository() {
        //Arrange
        int expectedSize = 0;
        //Act
        List<RoomId> result = roomService.getRoomIds();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRoomIds method should return an empty list when there are no rooms in the repository");
    }

    /**
     * Tests if the getRoomIds returns a list with one room id when there is one room in the repository
     */
    @Test
    void testGetRoomIdsOneRoomInRepository() {
        //Arrange
        int expectedSize = 1;
        Iterable<RoomId> roomIds = List.of(mockRoomId);

        when(mockRoomRepository.findRoomIds()).thenReturn(roomIds);

        //Act
        List<RoomId> result = roomService.getRoomIds();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRoomIds method should return a list with one room id " +
                        "when there is one room in the repository");
    }

    /**
     * Tests if the getRoomIds returns a list with all room ids in the repository
     */
    @Test
    void testGetRoomIdsSeveralRoomsInRepository() {
        //Arrange
        int expectedSize = 3;
        Iterable<RoomId> roomIds = List.of(mockRoomId, mockRoomId, mockRoomId);

        when(mockRoomRepository.findRoomIds()).thenReturn(roomIds);

        //Act
        List<RoomId> result = roomService.getRoomIds();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRoomIds method should return a list with three room ids when " +
                        "there are three rooms in the repository");
    }

    /**
     * Tests if the getRoomIds returns an empty list when there are no rooms in the repository
     * with the given house name
     */
    @Test
    void testGetRoomIdsByHouseNameEmptyRepository() {
        //Arrange
        int expectedSize = 0;
        //Act
        List<RoomId> result = roomService.getRoomIdsByHouseName(houseName);
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRoomIds method should return an empty list when there are no rooms in the repository " +
                        "with the given house name");
    }

    /**
     * Tests if the getRoomIds returns a list with one room id when there is one room in the repository
     * with the given house name
     */
    @Test
    void testGetRoomIdsByHouseNameOneRoomInRepository() {
        //Arrange
        int expectedSize = 1;
        Iterable<RoomId> roomIds = List.of(mockRoomId);

        when(mockRoomRepository.findRoomIdsByHouseName(houseName)).thenReturn(roomIds);

        //Act
        List<RoomId> result = roomService.getRoomIdsByHouseName(houseName);
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRoomIds method should return a list with one room id " +
                        "when there is one room in the repository with the given house name");
    }

    /**
     * Tests if the getRoomIds returns a list with all room ids in the repository with the given house name
     */
    @Test
    void testGetRoomIdsByHouseNameSeveralRoomsInRepository() {
        //Arrange
        int expectedSize = 3;
        Iterable<RoomId> roomIds = List.of(mockRoomId, mockRoomId, mockRoomId);

        when(mockRoomRepository.findRoomIdsByHouseName(houseName)).thenReturn(roomIds);

        //Act
        List<RoomId> result = roomService.getRoomIdsByHouseName(houseName);
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRoomIds method should return a list with three room ids when " +
                        "there are three rooms in the repository with the given house name");
    }


}