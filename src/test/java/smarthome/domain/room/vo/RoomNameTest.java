package smarthome.domain.room.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is responsible for testing the RoomName class.
 */
class RoomNameTest {

    /**
     * Test that the constructor does not throw an exception when a valid room name is given
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidRoomName() {
        //Arrange
        String validRoomName = "Living Room";
        //Act & Assert
        assertDoesNotThrow(() -> new RoomName(validRoomName), "RoomName object should be successfully created.");
    }

    /**
     * Test that the constructor throws an exception when a null room name is given
     */
    @Test
    void testConstructorThrowsExceptionForNullRoomName() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomName(null), "RoomName object should not be created" +
                " with null room name.");
    }

    /**
     * Test that the constructor throws an exception when a blank room name is given
     */
    @Test
    void testConstructorThrowsExceptionForBlankRoomName() {
        //Arrange
        String invalidRoomName = " ";
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomName(invalidRoomName), "RoomName object should not" +
                " be created with blank room name.");
    }

    /**
     * Test that the getRoomId method returns the correct value
     */
    @Test
    void testGetRoomNameReturnsCorrectValue() {
        //Arrange
        String expected = "Living Room";
        RoomName roomName = new RoomName(expected);
        //Act
        String result = roomName.getRoomName();
        //Assert
        assertEquals(expected, result, "Room name should be the same as the one provided in the constructor.");
    }

    /**
     * Test that the equals method returns true when the object is the same as the room name
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        // Act
        boolean result = roomName.equals(roomName);
        // Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * Test that the equals method returns false when the object is null
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        // Act
        boolean result = roomName.equals(null);
        // Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * Test that the equals method returns false when the object is not an instance of RoomName
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        // Act
        boolean result = roomName.equals(new Object());
        // Assert
        assertFalse(result, "The result should be false for different class object.");
    }

    /**
     * Test that the equals method returns false when the room name is different
     */
    @Test
    void testEqualsForDifferentRoomName() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        RoomName differentRoomName = new RoomName("Bedroom");
        // Act
        boolean result = roomName.equals(differentRoomName);
        // Assert
        assertFalse(result, "The result should be false for different room name.");
    }

    /**
     * Test that the equals method returns true when the room name is the same
     */
    @Test
    void testEqualsForSameRoomName() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        RoomName sameRoomName = new RoomName("Living Room");
        // Act
        boolean result = roomName.equals(sameRoomName);
        // Assert
        assertTrue(result, "The result should be true for the same room name.");
    }

    /**
     * Test that the hashCode method returns the same value for the same room name
     */
    @Test
    void testHashCodeForSameRoomName() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        RoomName sameRoomName = new RoomName("Living Room");
        // Act
        int hashCode = roomName.hashCode();
        int sameHashCode = sameRoomName.hashCode();
        // Assert
        assertEquals(hashCode, sameHashCode, "The hash code should be the same for equal objects.");
    }

    /**
     * Test that the hashCode method returns different values for different room names
     */
    @Test
    void testHashCodeForDifferentRoomName() {
        // Arrange
        RoomName roomName = new RoomName("Living Room");
        RoomName differentRoomName = new RoomName("Bedroom");
        // Act
        int hashCode = roomName.hashCode();
        int differentHashCode = differentRoomName.hashCode();
        // Assert
        assertNotEquals(hashCode, differentHashCode, "The hash code should be different for different objects.");
    }
}