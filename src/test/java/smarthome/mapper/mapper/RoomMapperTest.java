package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.*;
import smarthome.mapper.RoomDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for RoomMapper
 */
class RoomMapperTest {

    RoomDTO roomDTO;
    RoomMapper roomMapper;
    Room room;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        roomMapper = new RoomMapper();

        roomDTO = mock(RoomDTO.class);
        when(roomDTO.getRoomId()).thenReturn("1");
        when(roomDTO.getHouseName()).thenReturn("house");
        when(roomDTO.getRoomName()).thenReturn("room");
        when(roomDTO.getFloor()).thenReturn(1);
        when(roomDTO.getHeight()).thenReturn(10.0);
        when(roomDTO.getLength()).thenReturn(10.0);
        when(roomDTO.getWidth()).thenReturn(10.0);


        room = mock(Room.class);
        RoomId roomId = mock(RoomId.class);
        when(roomId.getRoomId()).thenReturn("1");
        HouseName houseName = mock(HouseName.class);
        RoomName roomName = mock(RoomName.class);
        when(roomName.getRoomName()).thenReturn("room");

        Height height = mock(Height.class);
        Length length = mock(Length.class);
        Width width = mock(Width.class);

        when(height.getHeight()).thenReturn(10.0);
        when(length.getLength()).thenReturn(10.0);
        when(width.getWidth()).thenReturn(10.0);

        Dimensions dimensions = mock(Dimensions.class);
        when(dimensions.getHeight()).thenReturn(height);
        when(dimensions.getLength()).thenReturn(length);
        when(dimensions.getWidth()).thenReturn(width);

        Floor floor = mock(Floor.class);
        when(floor.getFloor()).thenReturn(1);

        when(room.getIdentity()).thenReturn(roomId);
        when(room.getHouseName()).thenReturn(houseName);
        when(room.getRoomName()).thenReturn(roomName);
        when(room.getDimensions()).thenReturn(dimensions);
        when(room.getFloor()).thenReturn(floor);

    }

    /**
     * Tests that the toRoomId method returns the correct RoomId
     */
    @Test
    void testToRoomId() {
        //Arrange
        String expected = "1";
        //Act
        RoomId result = roomMapper.toRoomId(roomDTO);
        //Assert
        assertEquals(expected, result.getRoomId(), "The room id should be the expected one");
    }

    /**
     * Tests that the toHouseName method returns the correct HouseName
     */
    @Test
    void testToHouseName() {
        //Arrange
        String expected = "house";
        //Act
        HouseName result = roomMapper.toHouseName(roomDTO);
        //Assert
        assertEquals(expected, result.getName(), "The house name should be the expected one");
    }

    /**
     * Tests that the toRoomName method returns the correct RoomName
     */
    @Test
    void testToRoomName() {
        //Arrange
        String expected = "room";
        //Act
        RoomName result = roomMapper.toRoomName(roomDTO);
        //Assert
        assertEquals(expected, result.getRoomName(), "The room name should be the expected one");
    }

    /**
     * Tests that the toFloor method returns the correct Floor
     */
    @Test
    void testToFloor() {
        //Arrange
        int expected = 1;
        //Act
        Floor result = roomMapper.toFloor(roomDTO);
        //Assert
        assertEquals(expected, result.getFloor(), "The floor should be the expected one");
    }

    /**
     * Tests that the toDimensions method returns the Dimensions with the correct width
     */
    @Test
    void testToDimensionsWidth() {
        //Arrange
        double expectedWidth = 10.0;
        //Act
        Dimensions result = roomMapper.toDimensions(roomDTO);
        //Assert
        assertEquals(expectedWidth, result.getWidth().getWidth(), "The width should be the expected one");
    }

    /**
     * Tests that the toDimensions method returns the Dimensions with the correct height
     */
    @Test
    void testToDimensionsHeight() {
        //Arrange
        double expectedHeight = 10.0;
        //Act
        Dimensions result = roomMapper.toDimensions(roomDTO);
        //Assert
        assertEquals(expectedHeight, result.getHeight().getHeight(), "The height should be the expected one");
    }

    /**
     * Tests that the toDimensions method returns the Dimensions with the correct length
     */
    @Test
    void testToDimensionsLength() {
        //Arrange
        double expectedLength = 10.0;
        //Act
        Dimensions result = roomMapper.toDimensions(roomDTO);
        //Assert
        assertEquals(expectedLength, result.getLength().getLength(), "The length should be the expected one");
    }

    /**
     * Tests that the toRoomDTO method returns the RoomDTO with the correct roomId
     */
    @Test
    void testToRoomDTORoomId() {
        //Arrange
        RoomDTO expected = roomDTO;
        //Act
        RoomDTO result = roomMapper.toRoomDTO(room);
        //Assert
        assertEquals(expected.getRoomId(), result.getRoomId(), "The room id should be the expected one");
    }

    /**
     * Tests that the toRoomDTO method returns the RoomDTO with the correct roomName
     */
    @Test
    void testToRoomDTORoomName() {
        //Arrange
        RoomDTO expected = roomDTO;
        //Act
        RoomDTO result = roomMapper.toRoomDTO(room);
        //Assert
        assertEquals(expected.getRoomId(), result.getRoomId(), "The room name should be the expected one");
    }

    /**
     * Tests that the toRoomsDTO method returns a list of RoomDTO.
     */
    @Test
    void testToRoomsDTOListOfOneRoom() {
        //Arrange
        Iterable<Room> rooms = List.of(room);
        //Act
        List<RoomDTO> result = roomMapper.toRoomsDTO(rooms);
        //Assert
        assertEquals(1, result.size(), "The list should have one element");

    }

    /**
     * Tests that the toRoomIdsDTO method returns a list of RoomDTO.
     */
    @Test
    void testToRoomIdsDTO() {
        //Arrange
        RoomId roomId = mock(RoomId.class);
        when(roomId.getRoomId()).thenReturn("1");
        Iterable<RoomId> roomIds = List.of(roomId);
        //Act
        List<RoomDTO> result = roomMapper.toRoomIdsDTO(roomIds);
        //Assert
        assertEquals(1, result.size(), "The list should have one element");
    }
}