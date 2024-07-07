package smarthome.domain.actuator.vo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the ActuatorMap class.
 */
class ActuatorMapTest {

    ActuatorId actuatorId;
    DeviceId deviceId;
    ActuatorModelName actuatorModelName;
    IntLimit integerUpperLimit;
    IntLimit integerLowerLimit;
    DecimalLimit decimalUpperLimit;
    DecimalLimit decimalLowerLimit;
    Precision precision;
    ActuatorMap actuatorMapPrepare;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        actuatorId = mock(ActuatorId.class);
        deviceId = mock(DeviceId.class);
        actuatorModelName = mock(ActuatorModelName.class);
        integerUpperLimit = mock(IntLimit.class);
        integerLowerLimit = mock(IntLimit.class);
        decimalUpperLimit = mock(DecimalLimit.class);
        decimalLowerLimit = mock(DecimalLimit.class);
        precision = mock(Precision.class);

        actuatorMapPrepare = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);

    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class is not null when valid parameters are provided.
     */
    @Test
    void testConstructorNotNullForValidDecimalLimit() {
        //Act
        ActuatorMap result = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Assert
        assertNotNull(result, "Should not be null");
    }

    /**
     * Tests the functionality of the getActuatorId method in the ActuatorMap class.
     * The method should return the ActuatorId that was set in the setUp method.
     */
    @Test
    void testGetActuatorId() {
        // Act
        ActuatorId result = actuatorMapPrepare.getActuatorId();
        // Assert
        assertEquals(actuatorId, result, "Should return the actuator id that was set in the setUp method");

    }

    /**
     * Tests the functionality of the getDeviceId method in the ActuatorMap class.
     * The method should return the DeviceId that was set in the setUp method.
     */
    @Test
    void testGetDeviceId() {
        // Act
        DeviceId result = actuatorMapPrepare.getDeviceId();
        // Assert
        assertEquals(deviceId, result, "Should return the device id that was set in the setUp method");
    }

    /**
     * Tests the functionality of the getActuatorModelName method in the ActuatorMap class.
     * The method should return the ActuatorModelName that was set in the setUp method.
     */
    @Test
    void testGetActuatorModelName() {
        // Act
        ActuatorModelName result = actuatorMapPrepare.getActuatorModelName();
        // Assert
        assertEquals(actuatorModelName, result, "Should return the actuator model name that was set in the setUp " +
                "method");
    }

    /**
     * Tests the functionality of the getIntegerUpperLimit method in the ActuatorMap class.
     * The method should return the IntegerUpperLimit that was set in the setUp method.
     */
    @Test
    void testGetIntegerUpperLimit() {
        // Act
        IntLimit result = actuatorMapPrepare.getIntegerUpperLimit();
        // Assert
        assertEquals(integerUpperLimit, result, "Should return the integer upper limit that was set in the setUp " +
                "method");
    }

    /**
     * Tests the functionality of the getIntegerLowerLimit method in the ActuatorMap class.
     * The method should return the IntegerLowerLimit that was set in the setUp method.
     */
    @Test
    void testGetIntegerLowerLimit() {
        // Act
        IntLimit result = actuatorMapPrepare.getIntegerLowerLimit();
        // Assert
        assertEquals(integerLowerLimit, result, "Should return the integer lower limit that was set in the setUp " +
                "method");
    }

    /**
     * Tests the functionality of the getDecimalUpperLimit method in the ActuatorMap class.
     * The method should return the DecimalUpperLimit that was set in the setUp method.
     */
    @Test
    void testGetDecimalUpperLimit() {
        // Act
        DecimalLimit result = actuatorMapPrepare.getDecimalUpperLimit();
        // Assert
        assertEquals(decimalUpperLimit, result, "Should return the decimal upper limit that was set in the setUp " +
                "method");
    }

    /**
     * Tests the functionality of the getDecimalLowerLimit method in the ActuatorMap class.
     * The method should return the DecimalLowerLimit that was set in the setUp method.
     */
    @Test
    void testGetDecimalLowerLimit() {
        // Act
        DecimalLimit result = actuatorMapPrepare.getDecimalLowerLimit();
        // Assert
        assertEquals(decimalLowerLimit, result, "Should return the decimal lower limit that was set in the setUp " +
                "method");
    }

    /**
     * Tests the functionality of the getPrecision method in the ActuatorMap class.
     * The method should return the Precision that was set in the setUp method.
     */
    @Test
    void testGetPrecision() {
        // Act
        Precision result = actuatorMapPrepare.getPrecision();
        // Assert
        assertEquals(precision, result, "Should return the precision that was set in the setUp method");
    }

    /**
     * Tests the functionality of the setDeviceId method in the ActuatorMap class.
     * The method should set the DeviceId to the new DeviceId provided.
     */
    @Test
    void testSetDeviceId() {// Arrange
        when(deviceId.getIdentity()).thenReturn("deviceId");

        DeviceId deviceId1 = mock(DeviceId.class);
        when(deviceId1.getIdentity()).thenReturn("deviceId1");

        String expected = "deviceId1";
        // Act
        actuatorMapPrepare.setDeviceId(deviceId1);
        // Assert
        assertEquals(expected, actuatorMapPrepare.getDeviceId().getIdentity(), "Should return the new device id");
    }

    /**
     * Test to verify that the equals method returns true when the object is the same.
     */
    @Test
    void testEqualsForSameObject() {

        // Act
        boolean result = actuatorMapPrepare.equals(actuatorMapPrepare);
        // Assert
        assertTrue(result, "Should return true when the object is the same");
    }

    /**
     * Test to verify that the equals method returns false when the object is null.
     */
    @Test
    void testEqualsForNullObject() {
        // Act
        boolean result = actuatorMapPrepare.equals(null);
        // Assert
        assertFalse(result, "Should return false when the object is null");
    }

    /**
     * Test to verify that the equals method returns false when the object is different.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId1, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);
        // Assert
        assertFalse(result, "Should return false when the object is different");
    }

    /**
     * Test to verify that the equals method returns false when the class object is different.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Act
        boolean result = actuatorMapPrepare.equals(new Object());
        // Assert
        assertFalse(result, "Should return false when the class object is different");
    }

    /**
     * Test to verify that the equals method returns true when the attributes are the same.
     */
    @Test
    void testEqualsForSameAttributesUsingMockedConstructionSho() {
        //Arrange
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertTrue(result, "Should return true when the attributes are the same");
    }

    /**
     * Test to verify that the equals method returns false when the actuatorId is different.
     */
    @Test
    void testEqualsForDifferentActuatorId() {
        //Arrange
        ActuatorId actuatorId1 = mock(ActuatorId.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId1, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the actuator id is different");
    }

    /**
     * Test to verify that the equals method returns false when the deviceId is different.
     */
    @Test
    void testEqualsForDifferentDeviceId() {
        //Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId1, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the device id is different");
    }

    /**
     * Test to verify that the equals method returns false when the actuatorModelName is different.
     */
    @Test
    void testEqualsForDifferentActuatorModelName() {
        //Arrange
        ActuatorModelName actuatorModelName1 = mock(ActuatorModelName.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName1, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the actuator model name is different");
    }

    /**
     * Test to verify that the equals method returns false when the integerUpperLimit is different.
     */
    @Test
    void testEqualsForDifferentIntegerUpperLimit() {
        //Arrange
        IntLimit integerUpperLimit1 = mock(IntLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit1,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the integer upper limit is different");
    }

    /**
     * Test to verify that the equals method returns false when the integerLowerLimit is different.
     */
    @Test
    void testEqualsForDifferentIntegerLowerLimit() {
        //Arrange
        IntLimit integerLowerLimit1 = mock(IntLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit1, decimalUpperLimit, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the integer lower limit is different");
    }

    /**
     * Test to verify that the equals method returns false when the decimalUpperLimit is different.
     */
    @Test
    void testEqualsForDifferentDecimalUpperLimit() {
        //Arrange
        DecimalLimit decimalUpperLimit1 = mock(DecimalLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit1, decimalLowerLimit, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the decimal upper limit is different");
    }

    /**
     * Test to verify that the equals method returns false when the decimalLowerLimit is different.
     */
    @Test
    void testEqualsForDifferentDecimalLowerLimit() {
        //Arrange
        DecimalLimit decimalLowerLimit1 = mock(DecimalLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit1, precision);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the decimal lower limit is different");
    }

    /**
     * Test to verify that the equals method returns false when the precision is different.
     */
    @Test
    void testEqualsForDifferentPrecision() {
        //Arrange
        Precision precision1 = mock(Precision.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision1);

        //Act
        boolean result = actuatorMap.equals(actuatorMapPrepare);

        //Assert
        assertFalse(result, "Should return false when the precision is different");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different actuator id and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentActuatorId() {
        // Arrange
        ActuatorId actuatorId1 = mock(ActuatorId.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId1, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different device id and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentDeviceId() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId1, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different actuator model name and checks if their hashCodes are
     * different.
     */
    @Test
    void testHashCodeForDifferentActuatorModelName() {
        // Arrange
        ActuatorModelName actuatorModelName1 = mock(ActuatorModelName.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName1, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different integer upper limit and checks if their hashCodes are
     * different.
     */
    @Test
    void testHashCodeForDifferentIntegerUpperLimit() {
        // Arrange
        IntLimit integerUpperLimit1 = mock(IntLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit1,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different integer lower limit and checks if their hashCodes are
     * different.
     */
    @Test
    void testHashCodeForDifferentIntegerLowerLimit() {
        // Arrange
        IntLimit integerLowerLimit1 = mock(IntLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit1, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different decimal upper limit and checks if their hashCodes are
     * different.
     */
    @Test
    void testHashCodeForDifferentDecimalUpperLimit() {
        // Arrange
        DecimalLimit decimalUpperLimit1 = mock(DecimalLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit1, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different decimal lower limit and checks if their hashCodes are
     * different.
     */
    @Test
    void testHashCodeForDifferentDecimalLowerLimit() {
        // Arrange
        DecimalLimit decimalLowerLimit1 = mock(DecimalLimit.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit1, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with different precision and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentPrecision() {
        // Arrange
        Precision precision1 = mock(Precision.class);
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision1);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");
    }

    /**
     * This test verifies the hashCode method of the ActuatorMap class.
     * It creates two ActuatorMap objects with the same attributes and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameAttributes() {
        // Arrange
        ActuatorMap actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit,
                integerLowerLimit, decimalUpperLimit, decimalLowerLimit, precision);
        // Act
        int hashCode = actuatorMapPrepare.hashCode();
        int hashCode1 = actuatorMap.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return the same hash code");
    }
}