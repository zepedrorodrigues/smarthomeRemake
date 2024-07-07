package smarthome.domain.device.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Provides unit tests for the {@link DeviceId} class, focusing on its constructor, {@code equals},
 * {@code equals}, and {@code hashCode} methods. These tests verify the class's ability to handle
 * valid, null, and blank identifiers correctly, and ensure that identity comparison, equality, and
 * hash code generation are implemented as expected.
 */
class DeviceIdTest {
    String validIdString = "id";
    String blankString = "";

    /**
     * Verifies that the {@link DeviceId} constructor throws an IllegalArgumentException when a null
     * ID is provided, enforcing non-null ID requirement.
     */
    @Test
    void constructorThrowsExceptionWhenIdIsNull() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceId(null), "ID must not be null.");
    }

    /**
     * Ensures that the {@link DeviceId} constructor throws an IllegalArgumentException when a blank
     * ID is provided, upholding the requirement for non-empty IDs.
     */
    @Test
    void constructorThrowsExceptionWhenIdIsBlank() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceId(blankString), "ID must not be blank.");
    }

    /**
     * Confirms that the {@link DeviceId} constructor accepts a valid non-null, non-blank ID without
     * throwing an exception, demonstrating proper ID validation.
     */
    @Test
    void constructorDoesNotThrowExceptionWhenIdIsValid() {
        // Act and Assert
        assertDoesNotThrow(() -> new DeviceId(validIdString), "Constructor should not throw an exception for a valid " +
                "ID.");
    }

    /**
     * Tests the {@code equals} method's ability to identify when two {@link DeviceId} instances
     * refer to the same object, ensuring correct self-comparison behavior.
     */
    @Test
    void equalsReturnsTrueSameObject() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act and Assert
        assertTrue(deviceId.equals(deviceId), "equals should return true for the same object.");
    }

    /**
     * Validates that the {@code equals} method correctly returns false when a {@link DeviceId}
     * instance is compared with an object of a different class, adhering to type-specific identity comparison.
     */
    @Test
    void equalsReturnsFalseDifferentClass() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act and Assert
        assertFalse(deviceId.equals(new Object()), "equals should return false for different classes.");
    }

    /**
     * Ensures that the {@code equals} method returns false when comparing two {@link DeviceId}
     * instances with different ID values, confirming accurate value-based identity comparison.
     */
    @Test
    void equalsReturnsFalseDifferentId() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act
        DeviceId otherDeviceId = new DeviceId("otherId");
        // Assert
        assertFalse(deviceId.equals(otherDeviceId), "equals should return false for different IDs.");
    }

    /**
     * Tests that the {@code equals} method returns true for two {@link DeviceId} instances with
     * identical ID values, verifying correct identity equivalence for equivalent IDs.
     */
    @Test
    void equalsReturnsTrueSameId() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act
        DeviceId otherDeviceId = new DeviceId(validIdString);
        // Assert
        assertTrue(deviceId.equals(otherDeviceId), "equals should return true for the same ID.");
    }

    /**
     * Verifies that the {@code getIdentity} method correctly returns the ID value of a {@link DeviceId}
     * instance, ensuring the integrity of the identity value retrieval.
     */
    @Test
    void getIdentityReturnsId() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act and Assert
        assertEquals(validIdString, deviceId.getIdentity(), "getIdentity should return the ID value.");
    }

    /**
     * Confirms that the {@code hashCode} method produces identical values for two {@link DeviceId}
     * instances with the same ID value, ensuring consistent hash code generation based on ID value.
     */
    @Test
    void hashCodeReturnsSameValueForSameId() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act
        DeviceId otherDeviceId = new DeviceId(validIdString);
        // Assert
        assertEquals(deviceId.hashCode(), otherDeviceId.hashCode(), "hashCode should return the same value for the " +
                "same ID.");
    }

    /**
     * Tests that the {@code hashCode} method generates distinct values for two {@link DeviceId}
     * instances with different ID values, verifying the uniqueness of hash codes for distinct IDs.
     */
    @Test
    void hashCodeReturnsDifferentValueForDifferentId() {
        // Arrange
        DeviceId deviceId = new DeviceId(validIdString);
        // Act
        DeviceId otherDeviceId = new DeviceId("otherId");
        // Assert
        assertNotEquals(deviceId.hashCode(), otherDeviceId.hashCode(), "hashCode should return different values for " +
                "different IDs.");
    }

    /**
     * Ensures that the {@code equals} method accurately identifies two {@link DeviceId} instances
     * with the same ID value as equal, validating equivalence based on ID value.
     */
    @Test
    void equalsReturnsTrueForSameId() {
        DeviceId deviceId = new DeviceId(validIdString);
        DeviceId otherDeviceId = new DeviceId(validIdString);
        assertTrue(deviceId.equals(otherDeviceId), "equals should return true for the same ID.");
    }

    /**
     * Confirms that the {@code equals} method correctly determines two {@link DeviceId} instances
     * with different ID values as not equal, upholding value-based equality.
     */
    @Test
    void equalsReturnsFalseForDifferentId() {
        DeviceId deviceId = new DeviceId(validIdString);
        DeviceId otherDeviceId = new DeviceId("otherId");
        assertFalse(deviceId.equals(otherDeviceId), "equals should return false for different IDs.");
    }

    /**
     * Validates that the {@code equals} method returns false when a {@link DeviceId} instance is
     * compared with an object of a different class, ensuring type-specific equality.
     */
    @Test
    void equalsReturnsFalseForDifferentClass() {
        DeviceId deviceId = new DeviceId(validIdString);
        assertFalse(deviceId.equals(new Object()), "equals should return false for different classes.");
    }

    /**
     * Tests the {@code equals} method's ability to correctly identify a {@link DeviceId} instance
     * as equal to itself, verifying correct self-equality behavior.
     */
    @Test
    void equalsReturnsTrueForSameObject() {
        DeviceId deviceId = new DeviceId(validIdString);
        assertTrue(deviceId.equals(deviceId), "equals should return true for the same object.");
    }

    /**
     * Ensures that the {@code equals} method returns false when comparing a {@link DeviceId}
     * instance with null, adhering to the general contract of equals.
     */
    @Test
    void equalsReturnsFalseForNull() {
        DeviceId deviceId = new DeviceId(validIdString);
        assertFalse(deviceId.equals(null), "equals should return false for null.");
    }

}
