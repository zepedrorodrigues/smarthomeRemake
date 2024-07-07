package smarthome.domain.room.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the class for RoomId.
 */
class RoomIdTest {

    /**
     * Test the constructor of the class RoomId.
     * The constructor should not throw an exception for a valid room identifier.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidRoomId() {
        //Arrange
        String validRoomId = "112345B";
        //Act - Assert
        assertDoesNotThrow(() -> new RoomId(validRoomId));
    }

    /**
     * Test the constructor of the class RoomId.
     * The constructor should throw an exception for a null room identifier.
     */
    @Test
    void testConstructorThrowsExceptionForNullRoomId() {
        //Arrange
        String invalidRoomId = null;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomId(invalidRoomId));
    }

    /**
     * Test the constructor of the class RoomId.
     * The constructor should throw an exception for a blank room identifier.
     */
    @Test
    void testConstructorThrowsExceptionForBlankRoomId() {
        //Arrange
        String invalidRoomId = " ";
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new RoomId(invalidRoomId));
    }

    /**
     * Test the getRoomId method of the class RoomId.
     * The method should return the correct room identifier value.
     */
    @Test
    void testGetRoomIdReturnsCorrectValue() {
        //Arrange
        String expected = "112345B";
        RoomId roomId = new RoomId(expected);
        //Act
        String result = roomId.getRoomId();
        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the equals method of the class RoomId.
     * The method should return true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        RoomId roomId = new RoomId("112345B");

        // Act
        boolean result = roomId.equals(roomId);

        // Assert
        assertTrue(result);
    }

    /**
     * Test the equals method of the class RoomId.
     * The method should return false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        RoomId roomId = new RoomId("112345B");

        // Act
        boolean result = roomId.equals(null);

        // Assert
        assertFalse(result);
    }

    /**
     * Test the equals method of the class RoomId.
     * The method should return false when comparing with a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        RoomId roomId = new RoomId("112345B");

        // Act
        boolean result = roomId.equals(new Object());

        // Assert
        assertFalse(result);
    }

    /**
     * Test the equals method of the class RoomId.
     * The method should return true for the same room identifier value.
     */
    @Test
    void testEqualsForSameRoomId() {
        // Arrange
        String roomId = "112345B";
        RoomId roomId1 = new RoomId(roomId);
        RoomId roomId2 = new RoomId(roomId);

        // Act
        boolean result = roomId1.equals(roomId2);

        // Assert
        assertTrue(result);
    }

    /**
     * Test the equals method of the class RoomId.
     * The method should return false for different room identifier values.
     */
    @Test
    void testEqualsForDifferentRoomId() {
        // Arrange
        String roomIdA = "112345B";
        String roomIdB = "112345C";
        RoomId roomId1 = new RoomId(roomIdA);
        RoomId roomId2 = new RoomId(roomIdB);

        // Act
        boolean result = roomId1.equals(roomId2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test the hashCode method of the class RoomId.
     * The method should return the same hash code for the same room identifier value.
     */
    @Test
    void testHashCodeForSameRoomId() {
        // Arrange
        String roomId = "112345B";
        RoomId roomId1 = new RoomId(roomId);
        RoomId roomId2 = new RoomId(roomId);

        // Act
        int hashCode1 = roomId1.hashCode();
        int hashCode2 = roomId2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    /**
     * Test the hashCode method of the class RoomId.
     * The method should return different hash codes for different room identifier values.
     */
    @Test
    void testHashCodeForDifferentRoomId() {
        // Arrange
        String roomIdA = "112345B";
        String roomIdB = "1345C";
        RoomId roomId1 = new RoomId(roomIdA);
        RoomId roomId2 = new RoomId(roomIdB);

        // Act
        int hashCode1 = roomId1.hashCode();
        int hashCode2 = roomId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }
}