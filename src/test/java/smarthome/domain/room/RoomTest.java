package smarthome.domain.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.Height;
import smarthome.domain.room.vo.Length;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.domain.room.vo.Width;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Room class.
 */
class RoomTest {
    /**
     * The RoomName object to be used in the tests.
     */
    private RoomName roomName;

    /**
     * The HouseName object to be used in the tests.
     */
    private HouseName houseName;
    /**
     * The Floor object to be used in the tests.
     */
    private Floor floor;
    /**
     * The Width object to be used in the tests.
     */
    private Width width;
    /**
     * The Length object to be used in the tests.
     */
    private Length length;
    /**
     * The Height object to be used in the tests.
     */
    private Height height;
    /**
     * The Dimensions object to be used in the tests.
     */
    private Dimensions dimensions;
    /**
     * The Room object to be used in the tests.
     */
    private Room room;

    /**
     * Set up the Room object and the other objects needed for the tests.
     */
    @BeforeEach
    void setUp() {
        roomName = new RoomName("Living Room");
        houseName = new HouseName("My House");
        floor = new Floor(1);
        width = new Width(10.0);
        length = new Length(20.0);
        height = new Height(10.0);
        dimensions = new Dimensions(width, height, length);
        room = new Room(roomName, houseName, floor, dimensions);
    }

    /**
     * Test the constructor of the Room class with valid parameters.
     */
    @Test
    void testConstructorWithoutRoomIdValidParametersShouldNotThrowException() {
        //Act and Assert
        assertDoesNotThrow(() -> new Room(roomName, houseName, floor, dimensions), "Constructor should " + "not throw" +
                " an exception for valid parameters.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithoutRoomIdNullRoomNameShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(null, houseName, floor, dimensions), "Room name " +
                "must not be null.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithoutRoomIdNullHouseNameShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomName, null, floor, dimensions), "House name " +
                "must not be null.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithoutRoomIdNullFloorShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomName, houseName, null, dimensions), "Floor " +
                "must not be null.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithoutRoomIdNullDimensionsShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomName, houseName, floor, null), "Dimensions " +
                "must not be null.");
    }

    /**
     * Test the constructor of the Room class with valid parameters.
     */
    @Test
    void testConstructorWithRoomIdValidParametersShouldNotThrowException() {
        //Arrange
        RoomId roomId = new RoomId("1");
        //Act and Assert
        assertDoesNotThrow(() -> new Room(roomId, roomName, houseName, floor, dimensions), "Constructor should " +
                "not throw an exception for valid parameters.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithRoomIdNullRoomIdShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(null, roomName, houseName, floor, dimensions),
                "Room id must not be null.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithRoomIdNullRoomNameShouldThrowException() {
       //Arrange
        RoomId roomId = new RoomId("1");
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomId, null, houseName, floor, dimensions),
                "Room name must not be null.");
    }

    /**
        * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithRoomIdNullHouseNameShouldThrowException() {
        //Arrange
        RoomId roomId = new RoomId("1");
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomId, roomName, null, floor, dimensions),
                "House name must not be null.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithRoomIdNullFloorShouldThrowException() {
        //Arrange
        RoomId roomId = new RoomId("1");
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomId, roomName, houseName, null, dimensions),
                "Floor must not be null.");
    }

    /**
     * Test the constructor of the Room class with null parameters.
     */
    @Test
    void testConstructorWithRoomIdNullDimensionsShouldThrowException() {
        //Arrange
        RoomId roomId = new RoomId("1");
       //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Room(roomId, roomName, houseName, floor, null),
                "Dimensions must not be null.");
    }

    /**
     * Test the getRoomName method.
     */
    @Test
    void testGetRoomName() {
        //Act and Assert
        assertEquals(roomName, room.getRoomName(), "The room name should be the same" + " as the one used to " +
                "construct the object.");
    }

    /**
     * Test the getHouseName method.
     */
    @Test
    void testGetHouseName() {
        //Act and Assert
        assertEquals(houseName, room.getHouseName(), "The house name should be the same" + " as the one used to " +
                "construct the object.");
    }

    /**
     * Test the getFloor method.
     */
    @Test
    void testGetFloor() {
        //Act and Assert
        assertEquals(floor, room.getFloor(), "The floor should be the same" + " as the one used to construct the " +
                "object.");
    }

    /**
     * Test the getDimensions method.
     */
    @Test
    void testGetDimensions() {
        //Act and Assert
        assertEquals(dimensions, room.getDimensions(), "The dimensions should be the same" + " as the one used to " +
                "construct the object.");
    }

    /**
     * Test the getIdentity method
     */
    @Test
    void testGetIdentity() {
        //Arrange
        RoomId roomId = room.getIdentity();
        //Act
        Room room1 = new Room(roomName, houseName, floor, dimensions);
        //Assert
        assertNotNull(roomId);
        assertInstanceOf(RoomId.class, roomId);
        assertNotEquals(roomId, room1.getIdentity(), "The room id should be unique.");
    }

    /**
     * Test the equals method with two objects from different classes.
     */
    @Test
    void testEqualsWithDifferentClass() {
        //Arrange
        Room room = new Room(roomName, houseName, floor, dimensions);
        Object notARoom = new Object();
        //Act and Assert
        assertFalse(room.equals(notARoom), "The equals method should return false" + " when comparing with an object " +
                "of a different class.");
    }

    /**
     * Test the equals method between one object and itself
     */
    @Test
    void testEqualsWithItself() {
        //Arrange
        Room room = new Room(roomName, houseName, floor, dimensions);
        //Act and Assert
        assertTrue(room.equals(room), "An object should be equal to itself.");
    }

    /**
     * Test the equals method with two Room objects with different ids
     */
    @Test
    void testEqualsWithDifferentIds() {
        //Arrange
        Room room1 = new Room(new RoomName("Room 1"), houseName, floor, dimensions);
        Room room2 = new Room(new RoomName("Room 2"), houseName, floor, dimensions);
        //Act and Assert
        assertFalse(room1.equals(room2), "The equals method should return false " + "when comparing two Room objects " +
                "with different ids.");
    }

    /**
     * Test the equals method with two Room objects with the same id
     */
    @Test
    void tesEqualsBetWeenTwoRoomsWithTheSameIdShouldReturnTrue() {
        //Arrange
        RoomId roomId = new RoomId("1");
        Room otherRoom = new Room(roomId, roomName, houseName, floor, dimensions);
        Room anotherRoom = new Room(roomId, roomName, houseName, floor, dimensions);
        //Act
        boolean result = anotherRoom.equals(otherRoom);
        //Assert
        assertTrue(result, "The equals method should return true " + "when comparing two Room objects with the same " +
                "id.");
    }

    /**
     * Test equals with a null object should return false
     */
    @Test
    void testEqualsNullObjectShouldReturnFalse() {
        //Arrange
        Room room = new Room(roomName, houseName, floor, dimensions);
        //Act
        boolean result = room.equals(null);
        //Assert
        assertFalse(result, "The equals method should return false when comparing with a null object.");
    }

    /**
     * Test the hashCode method with two Room objects different ids
     */
    @Test
    void testHashCodeOf2DifferentRoomsShouldBeNotNullAndDifferent() {
        //Arrange
        Room otherRoom = new Room(roomName, houseName, floor, dimensions);
        //Act
        boolean result = room.hashCode() != otherRoom.hashCode();
        //Assert
        assertTrue(result, "The hash code should be different for two Room objects with different ids.");
    }

}
