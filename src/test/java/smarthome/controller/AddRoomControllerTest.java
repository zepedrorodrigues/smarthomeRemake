package smarthome.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;
import smarthome.service.impl.RoomServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration tests for the AddRoomController class
 */
class AddRoomControllerTest {

    //Controller Test Data
    private IHouseRepository mockHouseRepository;
    private IRoomRepository mockRoomRepository;
    private RoomFactory roomFactory;
    private IRoomService roomService;
    private RoomMapper roomMapper;
    private AddRoomController addRoomController;
    //Room DTO Test Data
    private String houseId;
    private String roomName;
    private int floor;
    private double height;
    private double width;
    private double length;

    /**
     * Sets up the test environment
     */
    @BeforeEach
    void setUp() {
        mockHouseRepository = mock(IHouseRepository.class);

        houseId = "testHouseName";
        roomName = "testRoomName";
        floor = 1;
        height = 10.0;
        width = 10.0;
        length = 10.0;

        mockRoomRepository = mock(IRoomRepository.class);
        when(mockRoomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.<Room>getArgument(0));
        roomFactory = new RoomFactoryImpl();
        roomService = new RoomServiceImpl(mockHouseRepository, mockRoomRepository, roomFactory);
        roomMapper = new RoomMapper();
        addRoomController = new AddRoomController(roomService, roomMapper);
    }

    /**
     * Test if the constructor throws an exception when the room service is null
     */
    @Test
    void testConstructorThrowsExceptionWhenRoomServiceIsNull() {
        //Arrange
        IRoomService nullRoomService = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                        new AddRoomController(nullRoomService, roomMapper),
                "The constructor should throw an IllegalArgumentException when the room service is null");
    }

    /**
     * Test if the constructor throws an exception when the room mapper is null
     */
    @Test
    void testConstructorThrowsExceptionWhenRoomMapperIsNull() {
        //Arrange
        RoomMapper nullRoomMapper = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                        new AddRoomController(roomService, nullRoomMapper),
                "The constructor should throw an IllegalArgumentException when the room mapper is null");
    }

    /**
     * Test if the addRoom method returns null when the room DTO is null
     */
    @Test
    void testAddRoomReturnNullWhenRoomDTOIsNull() {
        //Arrange
        RoomDTO invalidRoomDTO = null;

        //Act
        RoomDTO result = addRoomController.addRoom(invalidRoomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the room DTO is null");
    }

    /**
     * Test if the addRoom method returns null when the house id is not present in the repository
     */
    @Test
    void testAddRoomReturnNullWhenHouseNameIsNotPresentInTheRepository() {
        //Arrange
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, width, length);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(false);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the house name is not " +
                        "present in the repository");
    }

    /**
     * Test if the addRoom method returns null when the house id is null
     */
    @Test
    void testAddRoomReturnNullWhenHouseIdIsNull() {
        //Arrange
        String invalidHouseId = null;
        RoomDTO roomDTO = new RoomDTO(roomName, invalidHouseId, floor, height, width, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the house id is null");
    }

    /**
     * Test if the addRoom method returns null when the house id is blank
     */
    @Test
    void testAddRoomReturnNullWhenHouseIdIsBlank() {
        //Arrange
        String invalidHouseId = " ";
        RoomDTO roomDTO = new RoomDTO(roomName, invalidHouseId, floor, height, width, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the room name is blank");
    }

    /**
     * Test if the addRoom method returns null when the room name is null
     */
    @Test
    void testAddRoomReturnNullWhenRoomNameIsNull() {
        //Arrange
        String invalidRoomName = null;
        RoomDTO roomDTO = new RoomDTO(invalidRoomName, houseId, floor, height, width, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(invalidRoomName, houseId, floor, height, width, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, invalidHeight, width, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, invalidHeight, width, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, invalidWidth, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, invalidWidth, length);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, width, invalidLength);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

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
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, width, invalidLength);

        //Act
        RoomDTO result = addRoomController.addRoom(roomDTO);

        //Assert
        assertNull(result,
                "The addRoom method should return null when the length is negative");
    }

    /**
     * Test if the addRoom method correctly creates and saves a room to an empty repository
     */
    @Test
    void testAddRoomCorrectlyCreatesAndSavesRoomToAnEmptyRepository() {
        //Arrange
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, width, length);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);


        //Act
        RoomDTO resultDTO = addRoomController.addRoom(roomDTO);

        //Assert
        assertTrue(resultDTO.getRoomId() != null &&
                        resultDTO.getHouseName().equals(roomDTO.getHouseName()) &&
                        resultDTO.getRoomName().equals(roomDTO.getRoomName()) &&
                        resultDTO.getFloor().equals(roomDTO.getFloor()) &&
                        resultDTO.getHeight().equals(roomDTO.getHeight()) &&
                        resultDTO.getWidth().equals(roomDTO.getWidth()) &&
                        resultDTO.getLength().equals(roomDTO.getLength()),
                "The addRoom method should correctly create and save a room to an empty repository");
    }

    /**
     * Test if the addRoom method correctly creates and saves a room to a non-empty repository
     */
    @Test
    void testAddRoomCorrectlyCreatesAndSavesRoomToNonEmptyRepository() {
        //Arrange
        RoomDTO roomDTO1 = new RoomDTO(roomName, houseId, floor, height, width, length);
        RoomDTO roomDTO2 = new RoomDTO("testRoomName2", "testHouseName2", 1, 20, 20, 20);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);
        when(mockHouseRepository.containsIdentity(new HouseName("testHouseName2"))).thenReturn(true);
        addRoomController.addRoom(roomDTO1);

        //Act
        RoomDTO resultDTO = addRoomController.addRoom(roomDTO2);

        //Assert
        assertTrue(resultDTO.getRoomId() != null &&
                        resultDTO.getHouseName().equals(roomDTO2.getHouseName()) &&
                        resultDTO.getRoomName().equals(roomDTO2.getRoomName()) &&
                        resultDTO.getFloor().equals(roomDTO2.getFloor()) &&
                        resultDTO.getHeight().equals(roomDTO2.getHeight()) &&
                        resultDTO.getWidth().equals(roomDTO2.getWidth()) &&
                        resultDTO.getLength().equals(roomDTO2.getLength()),
                "The addRoom method should correctly create and save a room to a non-empty repository");

    }
}
