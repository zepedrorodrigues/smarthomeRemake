package smarthome.domain.actuator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.DecimalLimit;
import smarthome.domain.actuator.vo.DecimalValue;
import smarthome.domain.actuator.vo.Precision;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.Value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the ActuatorOfDecimalLimiter class.
 */
class ActuatorOfDecimalLimiterTest {

    ActuatorId actuatorId;
    DeviceId deviceId;
    ActuatorModelName actuatorModelName;
    DecimalLimit lowerLimit;
    DecimalLimit upperLimit;
    Precision precision;
    ActuatorOfDecimalLimiter actuatorOfDecimalLimiterPrepare;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        actuatorId = new ActuatorId("actuatorId");
        deviceId = new DeviceId("6h27s");
        actuatorModelName = new ActuatorModelName("XPTO");
        lowerLimit = new DecimalLimit(0.1);
        upperLimit = new DecimalLimit(5.7);
        precision = new Precision(1);

        actuatorOfDecimalLimiterPrepare = new ActuatorOfDecimalLimiter(actuatorId, deviceId, actuatorModelName,
                lowerLimit, upperLimit, precision);

    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class does not throw an exception when valid
     * parameters are provided.
     */
    @Test
    void testConstructorWithoutActuatorIdDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new ActuatorOfDecimalLimiter(deviceId, actuatorModelName, lowerLimit,
                upperLimit, precision), "Should not throw an exception when valid parameters are provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class does not throw an exception when upperLimit
     * and lowerLimit are the same.
     */
    @Test
    void testConstructorWithoutActuatorIdDoesNotThrowExceptionForValidParametersSameLimits() {
        //Arrange
        DecimalLimit lowerLimit1 = new DecimalLimit(5.7);
        DecimalLimit upperLimit1 = new DecimalLimit(5.7);
        //Act + assert
        assertDoesNotThrow(() -> new ActuatorOfDecimalLimiter(deviceId, actuatorModelName, lowerLimit1,
                upperLimit1, precision), "Should not throw an exception when valid parameters are provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null DeviceId
     * is provided.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullDeviceId() {
        //Arrange
        DeviceId nullDeviceId = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(nullDeviceId,
                actuatorModelName, lowerLimit, upperLimit, precision),
                "Should throw an exception when a null DeviceId is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null
     * ActuatorModelName
     * is provided.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullActuatorModelName() {
        //Arrange
        ActuatorModelName nullActuatorModelName = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(deviceId,
                nullActuatorModelName, lowerLimit, upperLimit, precision),
                "Should throw an exception when a null ActuatorModelName is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null
     * LowerLimit is provided.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullLowerLimit() {
        //Arrange
        DecimalLimit nullLowerLimit = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(deviceId, actuatorModelName,
                nullLowerLimit, upperLimit, precision),
                "Should throw an exception when a null LowerLimit is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null
     * UpperLimit is provided.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullUpperLimit() {
        //Arrange
        DecimalLimit nullUpperLimit = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(deviceId, actuatorModelName,
                lowerLimit, nullUpperLimit, precision),
                "Should throw an exception when a null UpperLimit is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null
     * Precision is provided.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullPrecision() {
        //Arrange
        Precision nullPrecision = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(deviceId, actuatorModelName,
                lowerLimit, upperLimit, nullPrecision),
                "Should throw an exception when a null Precision is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when the upperLimit is
     * lower than the lowerLimit is provided.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForInvalidLimits() {
        //Arrange
        DecimalLimit lowerLimitInvalid = new DecimalLimit(5.9);
        DecimalLimit upperLimit1 = new DecimalLimit(4.3);
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(deviceId, actuatorModelName,
                lowerLimitInvalid, upperLimit1, precision),
                "Should throw an exception when invalid limits are provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class does not throw an exception when valid p
     * arameters are provided.
     */
    @Test
    void testConstructorWithActuatorIdDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new ActuatorOfDecimalLimiter(actuatorId, deviceId, actuatorModelName, lowerLimit,
                upperLimit, precision),
                "Should not throw an exception when valid parameters are provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class does not throw an exception when upperLimit
     * and lowerLimit are the same.
     */
    @Test
    void testConstructorWithActuatorIdDoesNotThrowExceptionForValidParametersSameLimits() {
        //Arrange
        DecimalLimit lowerLimit1 = new DecimalLimit(5.7);
        DecimalLimit upperLimit1 = new DecimalLimit(5.7);
        //Act + assert
        assertDoesNotThrow(() -> new ActuatorOfDecimalLimiter(actuatorId, deviceId, actuatorModelName, lowerLimit1,
                upperLimit1, precision), "Should not throw an exception when valid parameters are provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class does not throw an exception when a null
     * actuator id is passed.
     */
    @Test
    void testConstructorWithActuatorIdDoesNotThrowExceptionForNullActuatorId() {
        //Arrange
        ActuatorId nullActuatorId = null;
        //Act + assert
        assertDoesNotThrow(() -> new ActuatorOfDecimalLimiter(nullActuatorId, deviceId, actuatorModelName, lowerLimit,
                upperLimit, precision), "Should not throw an exception when a null ActuatorId is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null DeviceId
     * is provided.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullDeviceId() {
        //Arrange
        DeviceId nullDeviceId = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(actuatorId, nullDeviceId,
                actuatorModelName, lowerLimit, upperLimit, precision),
                "Should throw an exception when a null DeviceId is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null
     * ActuatorModelName is provided.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullActuatorModelName() {
        //Arrange
        ActuatorModelName nullActuatorModelName = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                nullActuatorModelName, lowerLimit, upperLimit, precision),
                "Should throw an exception when a null ActuatorModelName is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null
     * LowerLimit is provided.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullLowerLimit() {
        //Arrange
        DecimalLimit nullLowerLimit = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                actuatorModelName, nullLowerLimit, upperLimit, precision),
                "Should throw an exception when a null LowerLimit is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null UpperLimit
     * is provided.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullUpperLimit() {
        //Arrange
        DecimalLimit nullUpperLimit = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                actuatorModelName, lowerLimit, nullUpperLimit, precision),
                "Should throw an exception when a null UpperLimit is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when a null Precision
     * is provided.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullPrecision() {
        //Arrange
        Precision nullPrecision = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                actuatorModelName, lowerLimit, upperLimit, nullPrecision),
                "Should throw an exception when a null Precision is provided.");
    }

    /**
     * Tests that the constructor of the ActuatorOfDecimalLimiter class throws an exception when the upperLimit is
     * lower than the lowerLimit is provided.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForInvalidLimits() {
        //Arrange
        DecimalLimit lowerLimitInvalid = new DecimalLimit(5.9);
        DecimalLimit upperLimit1 = new DecimalLimit(4.3);
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                actuatorModelName, lowerLimitInvalid, upperLimit1, precision),
                "Should throw an exception when invalid limits are provided.");
    }

    /**
     * Test to verify that the getId method returns the correct id of the actuator of decimal limiter.
     */
    @Test
    void testGetIdentity() {
        //Arrange
        ActuatorId expected = actuatorId;
        //Act
        ActuatorId result = actuatorOfDecimalLimiterPrepare.getIdentity();
        //Assert
        assertEquals(expected, result, "The actuator id should be the same as the one provided in the constructor.");
    }

    /**
     * Test to verify that the getId method does not return null for a random actuator id.
     */
    @Test
    void testGetIdentityForRandomActuatorId() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = new ActuatorOfDecimalLimiter(deviceId, actuatorModelName,
                lowerLimit, upperLimit, precision);
        //Act
        ActuatorId result = actuatorOfDecimalLimiter.getIdentity();
        //Assert
        assertNotNull(result, "The actuator id should not be null.");
    }

    /**
     * Test to verify that the getActuatorModelName method returns the correct actuator model name of the actuator of
     * decimal limiter.
     */
    @Test
    void testGetActuatorModelName() {
        //Arrange
        ActuatorModelName expected = actuatorModelName;
        //Act
        ActuatorModelName result = actuatorOfDecimalLimiterPrepare.getActuatorModelName();
        //Assert
        assertEquals(expected, result,
                "The actuator model name should be the same as the one provided in the constructor.");
    }

    /**
     * Test to verify that the getDeviceId method returns the correct device id of the actuator of decimal limiter.
     */
    @Test
    void testGetDeviceId() {
        //Arrange
        DeviceId expected = deviceId;
        //Act
        DeviceId result = actuatorOfDecimalLimiterPrepare.getDeviceId();
        //Assert
        assertEquals(expected, result, "Should return the same device id as the one provided in the constructor.");
    }

    /**
     * Test to verify that the getLowerLimit method returns the correct lower limit of the actuator of decimal limiter.
     */
    @Test
    void testGetLowerLimit() {
        //Arrange
        DecimalLimit expected = lowerLimit;
        //Act
        DecimalLimit result = actuatorOfDecimalLimiterPrepare.getLowerLimit();
        //Assert
        assertEquals(expected, result, "Should return the same lower limit as the one provided in the constructor.");
    }

    /**
     *
     * Test to verify that the getUpperLimit method returns the correct upper limit of the actuator of decimal limiter.
     */
    @Test
    void testGetUpperLimit() {
        //Arrange
        DecimalLimit expected = upperLimit;
        //Act
        DecimalLimit result = actuatorOfDecimalLimiterPrepare.getUpperLimit();
        //Assert
        assertEquals(expected, result, "Should return the same upper limit as the one provided in the constructor.");
    }

    /**
     * Test to verify that the getPrecision method returns the correct precision of the actuator of decimal limiter.
     */
    @Test
    void testGetPrecision() {
        //Arrange
        Precision expected = precision;
        //Act
        Precision result = actuatorOfDecimalLimiterPrepare.getPrecision();
        //Assert
        assertEquals(expected, result, "Should return the same precision as the one provided in the constructor.");
    }

    /**
     * Test to verify that the operate method does not throw an exception when a valid value is provided.
     */
    @Test
    void testOperateDoesNotThrowExceptionForValidValue() {
        //Arrange
        Value value = new DecimalValue(3.2);
        // Act & Assert
        assertDoesNotThrow(() -> actuatorOfDecimalLimiterPrepare.operate(value),
                "Should not throw an exception when a valid value is provided.");

    }

    /**
     * Test to verify that the operate method throws an exception when a value is below the lower limit
     */
    @Test
    void testOperateThrowsExceptionForValueBelowLowerLimit() {
        //Arrange
        Value value = new DecimalValue(0.05);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorOfDecimalLimiterPrepare.operate(value),
                "Should throw an exception when a value is below the lower limit.");

    }

    /**
     * Test to verify that the operate method throws an exception when a value is above the upper limit.
     */
    @Test
    void testOperateThrowsExceptionForValueAboveUpperLimit() {
        //Arrange
        Value value = new DecimalValue(7.99);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorOfDecimalLimiterPrepare.operate(value),
                "Should throw an exception when a value is above the upper limit.");
    }

    /**
     * Test to verify that the operate method returns the value of the ActuatorOfDecimalLimiter rounded down.
     */
    @Test
    void testOperateUpdateValueRoundDown() {
        // Arrange
        Value value = new DecimalValue(5.003);
        Value expected = new DecimalValue(5.0);
        //Act
        Value result = actuatorOfDecimalLimiterPrepare.operate(value);
        //Assert
        assertEquals(expected, result, "Should return the value rounded down to the nearest integer.");
    }

    /**
     * Test to verify that the operate method returns the value of the ActuatorOfDecimalLimiter rounded up.
     */
    @Test
    void testOperateUpdateValueRoundUp() {
        // Arrange
        Value value = new DecimalValue(5.0777);
        Value expected = new DecimalValue(5.1);
        //Act
        Value result = actuatorOfDecimalLimiterPrepare.operate(value);
        //Assert
        assertEquals(expected, result, "Should return the value rounded up to the nearest integer.");
    }

    /**
     * Test to verify that the operate method does not throw an exception when the value is the same as the
     * lower limit.
     */
    @Test
    void testOperateValueSameLowerLimit() {
        //Arrange
        Value value = new DecimalValue(0.1);
        // Act & Assert
        assertDoesNotThrow(() -> actuatorOfDecimalLimiterPrepare.operate(value),
                "Should not throw an exception when the value is the same as the lower limit.");
    }

    /**
     * Test to verify that the operate method does not throw an exception when the value is the same as the
     * upper limit.
     */
    @Test
    void testOperateValueSameUpperLimit() {
        //Arrange
        Value value = new DecimalValue(5.7);
        // Act & Assert
        assertDoesNotThrow(() -> actuatorOfDecimalLimiterPrepare.operate(value),
                "Should not throw an exception when the value is the same as the upper limit.");
    }

    @Test
    void testEqualsForSameObject() {
        //Act
        boolean result = actuatorOfDecimalLimiterPrepare.equals(actuatorOfDecimalLimiterPrepare);
        //Assert
        assertTrue(result, "Should return true when the same object is compared.");
    }

    /**
     * Test to verify that the equals method returns false when the object is null.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        ActuatorOfDecimalLimiter nullActuatorOfDecimalLimiter = null;
        //Act
        boolean result = actuatorOfDecimalLimiterPrepare.equals(nullActuatorOfDecimalLimiter);
        //Assert
        assertFalse(result, "Should return false when the object is null.");
    }

    /**
     * Test to verify that the equals method returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        //Act
        boolean result = actuatorOfDecimalLimiterPrepare.equals(new Object());
        //Assert
        assertFalse(result, "Should return false when a different class object is passed.");
    }

    /**
     * Test to verify that the equals method returns false when the actuator id is different.
     */
    @Test
    void testEqualsForDifferentActuatorId() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = new ActuatorOfDecimalLimiter(deviceId, actuatorModelName,
                lowerLimit, upperLimit, precision);
        //Act
        boolean result = actuatorOfDecimalLimiterPrepare.equals(actuatorOfDecimalLimiter);
        //Assert
        assertFalse(result, "Should return false when the actuator id is different.");

    }

    /**
     * Test to verify that the equals method returns true when the actuator id is the same.
     */
    @Test
    void testEqualsForSameActuatorId() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                actuatorModelName, lowerLimit, upperLimit, precision);
        //Act
        boolean result = actuatorOfDecimalLimiterPrepare.equals(actuatorOfDecimalLimiter);
        //Assert
        assertTrue(result, "Should return true when the actuator id is the same.");

    }

    /**
     * Test to verify that the hashCode method returns different hash codes for ActuatorOfDecimalLimiter objects with
     * different sensor ids.
     * The test creates a new ActuatorOfDecimalLimiter object with a different actuator id, calculates the hash codes
     * for the new object and the object created in the setUp method, and asserts that the hash codes are not equal.
     */
    @Test
    void testHashCodeForDifferentActuatorId() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = new ActuatorOfDecimalLimiter(deviceId,
                actuatorModelName, lowerLimit, upperLimit, precision);
        //Act
        int hashCode = actuatorOfDecimalLimiter.hashCode();
        int hashCode1 = actuatorOfDecimalLimiterPrepare.hashCode();
        //Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes for different actuator ids.");

    }

    /**
     * Test to verify that the hashCode method returns the same hash code for ActuatorOfDecimalLimiter objects with
     * the same sensor ids.
     * The test creates a new ActuatorOfDecimalLimiter object with the same actuator id as the object created in the
     * setUp method, calculates the hash codes for both objects, and asserts that the hash codes are equal.
     */
    @Test
    void testHashCodeForSameActuatorId() {
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = new ActuatorOfDecimalLimiter(actuatorId, deviceId,
                actuatorModelName, lowerLimit, upperLimit, precision);
        //Act
        int hashCode = actuatorOfDecimalLimiter.hashCode();
        int hashCode1 = actuatorOfDecimalLimiterPrepare.hashCode();
        //Assert
        assertEquals(hashCode, hashCode1, "Should return the same hash code for the same actuator ids.");

    }

}