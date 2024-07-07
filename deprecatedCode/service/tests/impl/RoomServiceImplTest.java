package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomName;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is used to test the RoomServiceImpl class.
 * Uses repositories in memory.
 */
class RoomServiceImplTest {
    //House/House Repository Test Data
    private House mockHouse;
    private HouseName mockHouseName;
    private IHouseRepository mockHouseRepository;
    //Room Repository Test Data
    private IRoomRepository mockRoomRepository;
    //Room/Room Factory Test Data
    private Room mockRoom;
    private String roomId;
    private String roomName;
    private String houseName;
    private int floor;
    private double height;
    private double width;
    private double length;
    private RoomName mockRoomName;
    private Floor mockFloor;
    private Dimensions mockDimensions;
    private RoomFactory mockRoomFactory;
    //Room DTO/Room Mapper Test Data
    private RoomMapper mockRoomMapper;
    private RoomDTO mockRoomDTO;
    //Room Service
    private IRoomService roomService;

    /**
     * This method sets up the necessary objects for the tests.
     * It is run before each test.
     */
    @BeforeEach
    void setUp() {
        roomId = "testRoomId";
        roomName = "testRoomName";
        houseName = "testHouseName";
        floor = 1;
        height = 10.0;
        width = 10.0;
        length = 10.0;

        mockRoom = mock(Room.class);
        mockRoomName = mock(RoomName.class);
        mockFloor = mock(Floor.class);
        mockDimensions = mock(Dimensions.class);

        mockRoomDTO = mock(RoomDTO.class);
        when(mockRoomDTO.getRoomId()).thenReturn(roomId);
        when(mockRoomDTO.getRoomName()).thenReturn(roomName);
        when(mockRoomDTO.getHouseName()).thenReturn(houseName);
        when(mockRoomDTO.getFloor()).thenReturn(floor);
        when(mockRoomDTO.getHeight()).thenReturn(height);
        when(mockRoomDTO.getWidth()).thenReturn(width);
        when(mockRoomDTO.getLength()).thenReturn(length);

        mockRoomMapper = mock(RoomMapper.class);
        when(mockRoomMapper.toRoomDTO(mockRoom)).thenReturn(mockRoomDTO);
        when(mockRoomMapper.toRoomName(mockRoomDTO)).thenReturn(mockRoomName);
        when(mockRoomMapper.toFloor(mockRoomDTO)).thenReturn(mockFloor);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenReturn(mockDimensions);

        mockHouse = mock(House.class);
        mockHouseName = mock(HouseName.class);
        when(mockHouse.getIdentity()).thenReturn(mockHouseName);
        mockHouseRepository = mock(IHouseRepository.class);
        when(mockHouseRepository.getEntity()).thenReturn(Optional.of(mockHouse));

        mockRoomRepository = mock(IRoomRepository.class);
        when(mockRoomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.<Room>getArgument(0));

        mockRoomFactory = mock(RoomFactoryImpl.class);
        when(mockRoomFactory.createRoom(mockRoomName, mockHouseName, mockFloor, mockDimensions)).thenReturn(mockRoom);

        roomService = new RoomServiceImpl(mockHouseRepository, mockRoomRepository, mockRoomFactory, mockRoomMapper);
    }

    /**
     * This test checks that the constructor of RoomServiceImpl does not throw an exception when valid parameters are
     * passed.
     */
    @Test
    void testConstructorRoomServiceDoesNotThrowExceptionForValidParameters() {
        //Act + Assert
        assertDoesNotThrow(() -> new RoomServiceImpl(mockHouseRepository, mockRoomRepository, mockRoomFactory,
                        mockRoomMapper),
                "The constructor should not throw an exception when valid parameters are passed");
    }

    /**
     * This test checks that the constructor of RoomServiceImpl throws an IllegalArgumentException when the house
     * repository parameter is null.
     */
    @Test
    void testConstructorRoomServiceThrowsExceptionForNullHouseRepository() {
        //Arrange
        IHouseRepository nullHouseRepository = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomServiceImpl(nullHouseRepository, mockRoomRepository,
                        mockRoomFactory, mockRoomMapper),
                "The constructor should throw an IllegalArgumentException when the house repository parameter is null");
    }

    /**
     * This test checks that the constructor of RoomServiceImpl throws an IllegalArgumentException when the
     * room repository parameter is null.
     */
    @Test
    void testConstructorRoomServiceThrowsExceptionForNullRoomRepository() {
        //Arrange
        IRoomRepository nullRoomRepository = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomServiceImpl(mockHouseRepository, nullRoomRepository,
                        mockRoomFactory, mockRoomMapper),
                "The constructor should throw an IllegalArgumentException when the room repository parameter is null");
    }

    /**
     * This test checks that the constructor of RoomServiceImpl throws an IllegalArgumentException when the
     * room factory parameter is null.
     */
    @Test
    void testConstructorRoomServiceThrowsExceptionForNullRoomFactory() {
        //Arrange
        RoomFactory nullRoomFactory = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomServiceImpl(mockHouseRepository, mockRoomRepository,
                        nullRoomFactory, mockRoomMapper),
                "The constructor should throw an IllegalArgumentException when the room factory parameter is null");
    }

    /**
     * This test checks that the constructor of RoomServiceImpl throws an IllegalArgumentException when the
     * room mapper parameter is null.
     */
    @Test
    void testConstructorRoomServiceThrowsExceptionForNullRoomMapper() {
        //Arrange
        RoomMapper nullRoomMapper = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomServiceImpl(mockHouseRepository, mockRoomRepository,
                        mockRoomFactory, nullRoomMapper),
                "The constructor should throw an IllegalArgumentException when the room mapper parameter is null");
    }

    /**
     * Test if the addRoom method returns null when the house name is not defined
     */
    @Test
    void testAddRoomReturnNullWhenHouseNameIsNotDefined() {
        //Arrange
        when(mockHouseRepository.getEntity()).thenReturn(Optional.empty());

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the house name is not defined");
    }

    /**
     * Test if the addRoom method returns null when the room name is null
     */
    @Test
    void testAddRoomReturnNullWhenRoomNameIsNull() {
        //Arrange
        String invalidRoomName = null;
        when(mockRoomDTO.getRoomName()).thenReturn(invalidRoomName);
        when(mockRoomMapper.toRoomName(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getRoomName() == null) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the room name is null");
    }

    /**
     * Test if the addRoom method returns null when the room name is blank
     */
    @Test
    void testAddRoomReturnNullWhenRoomNameIsBlank() {
        //Arrange
        String invalidRoomName = " ";
        when(mockRoomDTO.getRoomName()).thenReturn(invalidRoomName);
        when(mockRoomMapper.toRoomName(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getRoomName().isBlank()) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the room name is blank");
    }

    /**
     * Test if the addRoom method returns null when the height is zero
     */
    @Test
    void testAddRoomReturnNullWhenHeightIsZero() {
        //Arrange
        double invalidHeight = 0.0;
        when(mockRoomDTO.getHeight()).thenReturn(invalidHeight);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getHeight() == 0) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the height is zero");
    }

    /**
     * Test if the addRoom method returns null when the height is negative
     */
    @Test
    void testAddRoomReturnNullWhenHeightIsNegative() {
        //Arrange
        double invalidHeight = -1.0;
        when(mockRoomDTO.getHeight()).thenReturn(invalidHeight);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getHeight() < 0) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the height is negative");
    }

    /**
     * Test if the addRoom method returns null when the width is zero
     */
    @Test
    void testAddRoomReturnNullWhenWidthIsZero() {
        //Arrange
        double invalidWidth = 0.0;
        when(mockRoomDTO.getWidth()).thenReturn(invalidWidth);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getWidth() == 0) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the width is zero");
    }

    /**
     * Test if the addRoom method returns null when the width is negative
     */
    @Test
    void testAddRoomReturnNullWhenWidthIsNegative() {
        //Arrange
        double invalidWidth = -1.0;
        when(mockRoomDTO.getWidth()).thenReturn(invalidWidth);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getWidth() < 0) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the width is negative");
    }

    /**
     * Test if the addRoom method returns null when the length is zero
     */
    @Test
    void testAddRoomReturnNullWhenLengthIsZero() {
        //Arrange
        double invalidLength = 0.0;
        when(mockRoomDTO.getLength()).thenReturn(invalidLength);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getLength() == 0) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the length is zero");
    }

    /**
     * Test if the addRoom method returns null when the length is negative
     */
    @Test
    void testAddRoomReturnNullWhenLengthIsNegative() {
        //Arrange
        double invalidLength = -1.0;
        when(mockRoomDTO.getLength()).thenReturn(invalidLength);
        when(mockRoomMapper.toDimensions(mockRoomDTO)).thenAnswer(invocation -> {
            if (mockRoomDTO.getLength() < 0) {
                throw new IllegalArgumentException();
            }
            return mockRoomName;
        });

        //Act
        RoomDTO result = roomService.addRoom(mockRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the length is negative");
    }

    /**
     * Test if the addRoom method correctly creates and saves a room to a repository
     */
    @Test
    void testAddRoomCorrectlyCreatesAndSavesRoomToARepository() {
        //Act
        RoomDTO resultDTO = roomService.addRoom(mockRoomDTO);
        boolean result = resultDTO.getRoomId().equals(roomId) &&
                resultDTO.getHouseName().equals(houseName) &&
                resultDTO.getRoomName().equals(roomName) &&
                resultDTO.getFloor() == floor &&
                resultDTO.getHeight() == height &&
                resultDTO.getWidth() == width &&
                resultDTO.getLength() == length;

        //Assert
        assertTrue(result,
                "The addRoom method should correctly create and save a room to a repository");
    }

    /**
     * Tests if the getRooms returns an empty list when there are no rooms in the repository
     */
    @Test
    void testGetRoomsEmptyRepository() {
        //Arrange
        int expectedSize = 0;
        //Act
        List<RoomDTO> result = roomService.getRooms();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRooms method should return an empty list when there are no rooms in the repository");
    }

    /**
     * Tests if the getRooms returns a list with one room when there is one room in the repository
     */
    @Test
    void  testGetRoomsOneRoomInRepository() {
        //Arrange
        int expectedSize = 1;
        Iterable<Room> rooms = List.of(mockRoom);

        when(mockRoomRepository.findAll()).thenReturn(rooms);
        when(mockRoomMapper.toRoomsDTO(any())).thenReturn(List.of(mockRoomDTO));

        //Act
        List<RoomDTO> result = roomService.getRooms();
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
        when(mockRoomMapper.toRoomsDTO(any())).thenReturn(List.of(mockRoomDTO, mockRoomDTO, mockRoomDTO));

        //Act
        List<RoomDTO> result = roomService.getRooms();
        //Assert
        assertEquals(expectedSize, result.size(),
                "The getRooms method should return a list with three rooms when " +
                        "there are three rooms in the repository");
    }
}