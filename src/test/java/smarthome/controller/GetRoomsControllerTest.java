package smarthome.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.Height;
import smarthome.domain.room.vo.Length;
import smarthome.domain.room.vo.RoomName;
import smarthome.domain.room.vo.Width;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;
import smarthome.service.impl.RoomServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is responsible for testing the GetRoomsController class.
 */
class GetRoomsControllerTest {

    IHouseRepository houseRepository;
    IRoomRepository roomRepositoryMock;
    RoomMapper roomMapper;
    IRoomService roomService;
    RoomFactory roomFactory;
    GetRoomsController getRoomsController;

    /**
     * Set up data for testing
     */
    @BeforeEach
    void setUp() {

        roomRepositoryMock = mock(IRoomRepository.class);
        roomMapper = new RoomMapper();
        roomFactory = new RoomFactoryImpl();
        houseRepository = mock(IHouseRepository.class);

        roomService = new RoomServiceImpl(houseRepository, roomRepositoryMock, roomFactory);

        getRoomsController = new GetRoomsController(roomService, roomMapper);
    }

    /**
     * Tests the constructor of the GetRoomsController class does not throw an exception
     * when valid parameters are provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new GetRoomsController(roomService, roomMapper),
                "Should not throw an exception.");
    }

    /**
     * Tests the constructor of the GetRoomsController class throws an exception
     * when a null RoomService is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullRoomRepository() {
        //Arrange
        IRoomService nullRoomService = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new GetRoomsController(nullRoomService, roomMapper),
                "Should throw IllegalArgumentException.");
    }

    /**
     * Tests the constructor of the GetRoomsController class throws an exception
     * when a null RoomMapper is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullRoomMapper() {
        //Arrange
        RoomMapper nullRoomMapper = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new GetRoomsController(roomService, nullRoomMapper),
                "Should throw IllegalArgumentException.");
    }


    /**
     * Test to check if the getRooms method returns an empty list.
     */
    @Test
    void testGetRoomsEmptyList() throws IllegalAccessException {
        //Arrange
        List<Room> emptyList = new ArrayList<>();
        when(roomRepositoryMock.findAll()).thenReturn(emptyList);

        //Act
        List<RoomDTO> result = getRoomsController.getRooms();
        //Assert
        assertTrue(result.isEmpty(), "Should return an empty list.");
    }

    /**
     * Test to check if the getRooms method returns a list of rooms with two rooms.
     */
    @Test
    void testGetRoomsListWithRooms() throws IllegalAccessException {
        //Arrange
        HouseName houseName = new HouseName("House");
        Floor floor = new Floor(1);

        RoomName roomName = new RoomName("Living Room");
        Width width = new Width(7);
        Height height = new Height(10);
        Length length = new Length(9);
        Dimensions dimensions = new Dimensions(width, height, length);
        Room room1 = roomFactory.createRoom(roomName, houseName, floor, dimensions);

        RoomName roomName2 = new RoomName("Bedroom");
        Width width2 = new Width(3);
        Height height2 = new Height(3);
        Length length2 = new Length(7);
        Dimensions dimensions2 = new Dimensions(width2, height2, length2);
        Room room2 = roomFactory.createRoom(roomName2, houseName, floor, dimensions2);

        List<Room> rooms = List.of(room1, room2);
        when(roomRepositoryMock.findAll()).thenReturn(rooms);

        int expectedSize = 2;
        //Act
        List<RoomDTO> result = getRoomsController.getRooms();
        //Assert
        assertEquals(expectedSize, result.size(), "Should return a list with two rooms.");
    }
}